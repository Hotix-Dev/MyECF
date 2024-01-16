package com.e2p.myecf.activites;

import static com.e2p.myecf.helpers.ConstantConfig.AB_TITLE;
import static com.e2p.myecf.helpers.Utils.showSnackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.e2p.myecf.R;
import com.e2p.myecf.Utils.FtpManager;
import com.e2p.myecf.Utils.ThreadManager;
import com.e2p.myecf.adapters.ClientsAdapter;
import com.e2p.myecf.helpers.MySettings;
import com.e2p.myecf.models.Client;
import com.e2p.myecf.retrofit.RetrofitClient;
import com.e2p.myecf.retrofit.RetrofitInterface;

import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilesActivity extends AppCompatActivity {

    private static final String TAG = "FILES_ACTIVITTY";

    private Toolbar toolbar;
    private MySettings mySettings;
    private RelativeLayout emptyListView;
    private LinearLayoutCompat progressView;
    private AppCompatButton btnEmptyViewRefresh;
    private RecyclerView rvList;

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

    private void loadeFiles() {

        FtpManager ftpManager = FtpManager.getInstance(false);
        ThreadPoolExecutor threadPoolExecutor = ThreadManager.getInstance();
        threadPoolExecutor.execute(() -> {
            boolean isConnected = ftpManager.connect("test.rebex.net", 21, "demo", "password", false, "false");

            if (isConnected) {
                showSnackbar(findViewById(android.R.id.content), "Connected");
            } else {
                showSnackbar(findViewById(android.R.id.content), "Connection failed");
            }
        });


//        // Create an instance of FTPClient
//        FTPClient ftp = new FTPClient();
//
//        try {
//            // Establish a connection with the FTP URL
//            ftp.connect("test.rebex.net");
//            // Enter user details : user name and password
//            boolean isSuccess = ftp.login("demo", "password");
//
//            if (isSuccess) {
//                showSnackbar(findViewById(android.R.id.content), "Success");
//                // Fetch the list of names of the files. In case of no files an
//                // empty array is returned
//                // String[] filesFTP = ftp.listNames();
//                // int count = 1;
//                // Iterate on the returned list to obtain name of each file
//                //for (String file : filesFTP) {
//                //    System.out.println("File " + count + " :" + file);
//                //    count++;
//                //}
//            }
//
//            ftp.logout();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        finally {
//            try {
//                ftp.disconnect();
//            }
//            catch (IOException e) {
//                e.printStackTrace();
//            }
//        }


//        progressView.setVisibility(View.VISIBLE);
//        emptyListView.setVisibility(View.GONE);
//
//        String URL = "Client/GetClient";
//        RetrofitInterface service = RetrofitClient.getClientApi().create(RetrofitInterface.class);
//        Call<ArrayList<Client>> apiCall = service.getAllClientsQuery(URL);
//
//        apiCall.enqueue(new Callback<ArrayList<Client>>() {
//            @Override
//            public void onResponse(Call<ArrayList<Client>> call, Response<ArrayList<Client>> response) {
//                progressView.setVisibility(View.GONE);
//                if (response.raw().code() == 200) {
//
//                    ALL_CLIENTS = response.body();
//
//                    ClientsAdapter _ClientsAdapter = new ClientsAdapter(SelectClientActivity.this, ALL_CLIENTS);
//                    rvList.setAdapter(_ClientsAdapter);
//
//                } else {
//                    emptyListView.setVisibility(View.VISIBLE);
//                    showSnackbar(findViewById(android.R.id.content), response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<Client>> call, Throwable t) {
//                progressView.setVisibility(View.GONE);
//                emptyListView.setVisibility(View.VISIBLE);
//            }
//        });
    }

    private void uploadFile() {


    }
}