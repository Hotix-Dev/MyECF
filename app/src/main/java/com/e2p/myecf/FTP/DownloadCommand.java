package com.e2p.myecf.FTP;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.e2p.myecf.Utils.FtpManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author kyang
 */
public class DownloadCommand implements FileCommand {
    private final Context context;
    private final FtpManager ftpManager;
    private final ThreadPoolExecutor threadPoolExecutor;

    public DownloadCommand(Context context, FtpManager ftpManager, ThreadPoolExecutor threadPoolExecutor) {
        this.context = context;
        this.ftpManager = ftpManager;
        this.threadPoolExecutor = threadPoolExecutor;
    }

    @Override
    public void execute(Uri uri, String fullPath) {
        threadPoolExecutor.execute(() -> {
            try (InputStream inputStream = ftpManager.retrieveFileStream(fullPath);
                 OutputStream outputStream = context.getContentResolver().openOutputStream(uri)) {
                if (inputStream != null && outputStream != null) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    inputStream.close();
                    if (ftpManager.completePendingCommand()) {
                        ((Activity) context).runOnUiThread(() -> Toast.makeText(context, "File Download Success", Toast.LENGTH_SHORT).show());
                    } else {
                        ((Activity) context).runOnUiThread(() -> Toast.makeText(context, "File Download Failed", Toast.LENGTH_SHORT).show());
                    }
                }
            } catch (IOException e) {
                Log.e("DownloadFile", "Error: " + e.getMessage(), e);
                throw new RuntimeException(e);
            }
        });
    }
}