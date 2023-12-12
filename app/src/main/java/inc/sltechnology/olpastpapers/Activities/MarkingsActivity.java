package inc.sltechnology.olpastpapers.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import inc.sltechnology.olpastpapers.Adapters.MarkingsYearListAdapter;
import inc.sltechnology.olpastpapers.Adapters.PastPapersYearListAdapter;
import inc.sltechnology.olpastpapers.Models.MarkingsYearListModel;
import inc.sltechnology.olpastpapers.Models.PastPapersYearListModel;
import inc.sltechnology.olpastpapers.R;

public class MarkingsActivity extends AppCompatActivity {

    AdView mAdView;
    AlertDialog dialog;
    String markings_list;
    List<MarkingsYearListModel> markingsYearListModels;
    MarkingsYearListAdapter markingsYearListAdapter;
    FirebaseDatabase database;
    TextView txtSubject;
    ImageView imgSubject;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markings);

        if (!isConnected()){

            loadNoInternetDialog();

        }else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            builder.setView(inflater.inflate(R.layout.loading_dialog, null));
            builder.setCancelable(false);

            dialog = builder.create();
            dialog.show();

            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                }
            });

            mAdView = findViewById(R.id.adView2);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("");

            txtSubject = findViewById(R.id.txt_suject);
            imgSubject = findViewById(R.id.img_subject);

            recyclerView = findViewById(R.id.recycler);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            database = FirebaseDatabase.getInstance();

            String subject_title = getIntent().getStringExtra("subject_title");
            String subject_img = getIntent().getStringExtra("subject_img");
            markings_list = getIntent().getStringExtra("markings_list");

            txtSubject.setText(subject_title);
            Glide.with(this).load(subject_img).into(imgSubject);

            loadMarkingList();

        }

    }

    private void loadMarkingList() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference markingListRef = database.getReference();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        markingsYearListModels = new ArrayList<>();
        markingsYearListAdapter = new MarkingsYearListAdapter(markingsYearListModels, this);
        recyclerView.setAdapter(markingsYearListAdapter);

        markingListRef.child("markings_years").child(markings_list).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot content:snapshot.getChildren()){

                    MarkingsYearListModel markingsYearListModel = content.getValue(MarkingsYearListModel.class);
                    markingsYearListModels.add(markingsYearListModel);
                    dialog.dismiss();

                }

                markingsYearListAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MarkingsActivity.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        });

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

}