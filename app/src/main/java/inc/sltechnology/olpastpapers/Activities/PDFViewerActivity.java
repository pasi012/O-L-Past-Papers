package inc.sltechnology.olpastpapers.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import inc.sltechnology.olpastpapers.R;

public class PDFViewerActivity extends AppCompatActivity {

    AlertDialog dialog;

    public static final int PERMISSION_STORAGE_CODE = 1000;

    TextView title;
    PDFView pdfView;

    String toolbarName;

    String subTitle;
    String webLink;

    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);

        if (!isConnected()){

            loadNoInternetDialog();

        }else {

            Toolbar toolbar = findViewById(R.id.web_toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("");

            floatingActionButton = findViewById(R.id.download_btn);

            //Initialization...
            pdfView = findViewById(R.id.pdfView);
            title = findViewById(R.id.txt_paper_title);

            findViewById(R.id.web_back_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    finish();

                }
            });

            Intent intent = getIntent();

            toolbarName = intent.getStringExtra("title");
            subTitle = intent.getStringExtra("subTitle");
            webLink = intent.getStringExtra("url");

            String setTitle = toolbarName + " - " + subTitle;
            title.setText(setTitle);
            title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            title.setSelected(true);

            try {
                new RetrievePDFfromURL().execute(webLink);
            }catch (Exception e){

                onBackPressed();

            }

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    downloadUrl(webLink);

                }
            });

        }

    }

    private void loadNoInternetDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        View view = LayoutInflater.from(this).inflate(R.layout.no_internet_dialog, findViewById(R.id.no_internet_layout));

        view.findViewById(R.id.btn_no_internet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isConnected()){

                    recreate();
                    dialog.dismiss();

                }else {

                    recreate();
                    dialog.dismiss();

                }

            }
        });

        builder.setView(view);
        dialog = builder.create();
        dialog.show();

    }

    private boolean isConnected(){

        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return wifiConn != null && wifiConn.isConnectedOrConnecting() || mobileConn != null && mobileConn.isConnectedOrConnecting();

    }

    private void downloadUrl(String link) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            if (checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){

                String permission = (Manifest.permission.WRITE_EXTERNAL_STORAGE);
                requestPermissions(new String[]{permission},PERMISSION_STORAGE_CODE);

            }else {

                startDownloading(link);

            }

        }else {

            startDownloading(link);

        }

    }

    private void startDownloading(String downloadUrl) {

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle(title.getText());
        request.setDescription("Downloading...");

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, ""+ System.currentTimeMillis());

        DownloadManager manager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case PERMISSION_STORAGE_CODE:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    startDownloading(webLink);

                } else {

                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();

                }

        }
    }

    class RetrievePDFfromURL extends AsyncTask<String, Void, InputStream>{

        @Override
        protected InputStream doInBackground(String... strings) {

            InputStream inputStream = null;

            try {

                URL url = new URL(strings[0]);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                if (urlConnection.getResponseCode() == 200){

                    inputStream = new BufferedInputStream(urlConnection.getInputStream());

                }

            } catch (IOException e) {

                e.printStackTrace();
                return null;

            }

            return inputStream;

        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream)
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    .enableAnnotationRendering(false)
                    .password(null)
                    .scrollHandle(null)
                    .enableAntialiasing(true)
                    .spacing(0)
                    .autoSpacing(false)
                    .pageFitPolicy(FitPolicy.WIDTH)
                    .fitEachPage(false)
                    .pageSnap(false)
                    .pageFling(false)
                    .nightMode(false)
                    .load();

        }

    }

}