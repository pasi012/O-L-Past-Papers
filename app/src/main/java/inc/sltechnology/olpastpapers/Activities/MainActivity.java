package inc.sltechnology.olpastpapers.Activities;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.tabs.TabLayout;
import inc.sltechnology.olpastpapers.Adapters.ViewPagerAdapter;
import inc.sltechnology.olpastpapers.Fragments.MarkingFragment;
import inc.sltechnology.olpastpapers.Fragments.PastPapersFragment;
import inc.sltechnology.olpastpapers.R;

public class MainActivity extends AppCompatActivity {

    AdView mAdView;
    AlertDialog dialog;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isConnected()){

            loadNoInternetDialog();

        }else {

            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                }
            });

            mAdView = findViewById(R.id.adView1);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("");

            viewPager = findViewById(R.id.view_pager);
            tabLayout = findViewById(R.id.tab_layout);

            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            adapter.addFragment(new PastPapersFragment(), getResources().getString(R.string.past_papers));
            adapter.addFragment(new MarkingFragment(), getResources().getString(R.string.marking_schemes));
            viewPager.setAdapter(adapter);
            viewPager.setOffscreenPageLimit(1);

            tabLayout.setupWithViewPager(viewPager);

            for (int i = 0; i < tabLayout.getTabCount(); i++) {

                tabLayout.getTabAt(i);

            }


        }

    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == R.id.mEmail) {
            try {
                Intent sendMsg = new Intent(Intent.ACTION_VIEW);
                String url = "https://api.whatsapp.com/send?phone=" + "+94781147864";
                sendMsg.setPackage("com.whatsapp");
                sendMsg.setData(Uri.parse(url));
                startActivity(sendMsg);
            } catch (Exception e) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + MainActivity.this.getString(R.string.email_feedback)));
                intent.setPackage("com.google.android.gm");
                intent.putExtra(Intent.EXTRA_SUBJECT, MainActivity.this.getString(R.string.app_name));
                startActivity(intent);
            }
        } else if (itemId == R.id.mShare) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String shareMessage = getResources().getString(R.string.app_name) + "\n Download Now\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName();
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } else if (itemId == R.id.mRate) {
            MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
        } else if (itemId == R.id.mPrivacy) {
            MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.policy_url))));
        } else if (itemId == R.id.exit) {

            exitConfirm();

        } else {
            return true;
        }

        return true;

    }

    private void exitConfirm() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.exit_dialog, null);

        view.findViewById(R.id.txt_rate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));

            }
        });

        view.findViewById(R.id.txt_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

            }
        });

        view.findViewById(R.id.txt_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                System.exit(0);

            }
        });

        builder.setView(view);
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();

    }

    @Override
    public void onBackPressed() {

        exitConfirm();

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