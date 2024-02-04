package com.e2p.myecf.activites;

import static com.e2p.myecf.helpers.ConstantConfig.AB_TITLE;
import static com.e2p.myecf.helpers.ConstantConfig.FTP_IS_CONECTED;
import static com.e2p.myecf.helpers.Utils.showSnackbar;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e2p.myecf.FTP.DownloadCommand;
import com.e2p.myecf.FTP.FileCommand;
import com.e2p.myecf.FTP.UploadCommand;
import com.e2p.myecf.R;
import com.e2p.myecf.Utils.FtpManager;
import com.e2p.myecf.Utils.ThreadManager;
import com.e2p.myecf.adapters.FtpFilesAdapter;
import com.e2p.myecf.helpers.MySettings;

import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadPoolExecutor;

public class FilesActivity extends AppCompatActivity {

    private static final String TAG = "FILES_ACTIVITTY";

    private Toolbar toolbar;
    private MySettings mySettings;
    private RelativeLayout emptyListView;
    private LinearLayoutCompat progressView;
    private AppCompatButton btnEmptyViewRefresh;
    private RecyclerView rvList;

    FtpManager ftpManager = null;
    ThreadPoolExecutor threadPoolExecutor = null;
    private ActivityResultLauncher<Intent> createFileLauncher;
    private String currentPath = "/";
    private FTPFile selectFile;
    private FileCommand currentCommand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files);

        bindViews();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            rvList.setEnabled(true);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.files_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_upload) {
            uploadFile();
            return true;
        } else if (item.getItemId() == R.id.action_refresh) {
            loadeFiles();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**********************************************************************************************/

    private void bindViews() {
        try {

            toolbar = (Toolbar) findViewById(R.id.toolbar);

            emptyListView = (RelativeLayout) findViewById(R.id.empty_list_view);

            btnEmptyViewRefresh = (AppCompatButton) findViewById(R.id.btn_empty_view_refresh);

            rvList = (RecyclerView) findViewById(R.id.rv_files);
            rvList.setHasFixedSize(true);
            rvList.setLayoutManager(new LinearLayoutManager(this));

            progressView = (LinearLayoutCompat) findViewById(R.id.loading_view);

        } catch (Exception e) {
            Log.e(TAG, e.toString());
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
        }
    }

    private void init() {
        try {
            //settings
            mySettings = new MySettings(getApplicationContext());

            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(AB_TITLE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            createFileLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::handleCreateFileResult);

            ftpManager = FtpManager.getInstance(false);
            threadPoolExecutor = ThreadManager.getInstance();

            btnEmptyViewRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        loadeFiles();
                    } catch (Exception e) {
                        showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
                    } finally {
                    }
                }
            });

            loadeFiles();

        } catch (Exception e) {
            Log.e(TAG, e.toString());
            showSnackbar(findViewById(android.R.id.content), getString(R.string.error_message_something_wrong));
        }
    }

    /**********************************************************************************************/


    private void handleCreateFileResult(ActivityResult result) {
        try {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data != null && currentCommand != null) {
                    Uri uri = data.getData();
                    if (currentCommand instanceof UploadCommand) {
                        currentCommand.execute(uri, currentPath);
                    } else if (currentCommand instanceof DownloadCommand) {
                        String fullPath = currentPath.endsWith("/") ? currentPath + selectFile.getName() : currentPath + "/" + selectFile.getName();
                        currentCommand.execute(uri, fullPath);
                    }
                }
            }
        } catch (Exception e) {
            showSnackbar(findViewById(android.R.id.content), e.getMessage());
        }
    }

    private void loadeFiles() {

        progressView.setVisibility(View.VISIBLE);
        emptyListView.setVisibility(View.GONE);
        rvList.setVisibility(View.GONE);

        threadPoolExecutor.execute(() -> {
            try {
                FTP_IS_CONECTED = ftpManager.connect("41.228.164.76", 21, "myecf", "cfe@FTP", false, "false");

                if (FTP_IS_CONECTED) {
                    currentPath = currentPath.endsWith("/") ? currentPath : currentPath + "/";
                    FTPFile[] files = ftpManager.listFiles(currentPath);
                    runOnUiThread(() -> updateFileListView(files));

                } else {
                    showSnackbar(findViewById(android.R.id.content), "Connection failed");
                    runOnUiThread(() -> updateFileListView(null));
                }
            } catch (IOException e) {
                Log.e(TAG, "Error: " + e.getMessage(), e);
                runOnUiThread(() -> updateFileListView(null));
            } catch (Exception e) {
                Log.e(TAG, "Error: " + e.getMessage(), e);
                runOnUiThread(() -> updateFileListView(null));
            }
        });
    }

    private void updateFileListView(FTPFile[] files) {

        progressView.setVisibility(View.GONE);

        if ((files != null) && (files.length > 0)) {
            emptyListView.setVisibility(View.GONE);
            rvList.setVisibility(View.VISIBLE);

            FtpFilesAdapter _FtpFilesAdapter = new FtpFilesAdapter(FilesActivity.this, new ArrayList<FTPFile>(Arrays.asList(files)));
            rvList.setAdapter(_FtpFilesAdapter);

        } else {
            emptyListView.setVisibility(View.VISIBLE);
            rvList.setVisibility(View.GONE);
        }
    }

    private void uploadFile() {
        currentCommand = new UploadCommand(this, ftpManager, getContentResolver(), threadPoolExecutor);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        createFileLauncher.launch(intent);

    }
}