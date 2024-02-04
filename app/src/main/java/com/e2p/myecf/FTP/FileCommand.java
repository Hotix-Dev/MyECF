package com.e2p.myecf.FTP;

import android.net.Uri;

public interface FileCommand {
    void execute(Uri uri, String path);
}
