package inc.sltechnology.olpastpapers.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.List;

import inc.sltechnology.olpastpapers.Activities.PDFViewerActivity;
import inc.sltechnology.olpastpapers.Models.MarkingsYearListModel;
import inc.sltechnology.olpastpapers.Models.PastPapersYearListModel;
import inc.sltechnology.olpastpapers.R;

public class MarkingsYearListAdapter extends RecyclerView.Adapter<MarkingsYearListAdapter.MyViewHolder> {

    private InterstitialAd mInterstitialAd;

    private List<MarkingsYearListModel> markingsYearListModels;

    Context context;

    public MarkingsYearListAdapter(List<MarkingsYearListModel> markingsYearListModels, Context context) {
        this.markingsYearListModels = markingsYearListModels;
        this.context = context;

        loadAd();

    }

    @NonNull
    @Override
    public MarkingsYearListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.markings_year_layout, parent, false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MarkingsYearListAdapter.MyViewHolder holder, int position) {

        holder.marking_subTitle.setText("Marking Scheme");

        holder.marking_title.setText(markingsYearListModels.get(holder.getAdapterPosition()).getMarking_title());
        holder.marking_title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        holder.marking_title.setSelected(true);

        Glide.with(holder.itemView).load(markingsYearListModels.get(holder.getAdapterPosition()).getMarking_img()).into(holder.year_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mInterstitialAd != null){

                    mInterstitialAd.show((Activity) context);

                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();

                            Intent intent = new Intent(context, PDFViewerActivity.class);
                            intent.putExtra("title", holder.marking_title.getText().toString());
                            intent.putExtra("subTitle", holder.marking_subTitle.getText().toString());
                            intent.putExtra("url", markingsYearListModels.get(holder.getAdapterPosition()).getMarking_url());
                            context.startActivity(intent);

                            mInterstitialAd = null;

                            loadAd();

                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);

                            mInterstitialAd = null;

                        }
                    });

                }else {

                    Intent intent = new Intent(context, PDFViewerActivity.class);
                    intent.putExtra("title", holder.marking_title.getText().toString());
                    intent.putExtra("subTitle", holder.marking_subTitle.getText().toString());
                    intent.putExtra("url", markingsYearListModels.get(holder.getAdapterPosition()).getMarking_url());
                    context.startActivity(intent);

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return markingsYearListModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView year_image;
        TextView marking_title, marking_subTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            year_image = itemView.findViewById(R.id.img_marking);
            marking_title = itemView.findViewById(R.id.txt_marking);
            marking_subTitle = itemView.findViewById(R.id.txt_SubTitle);

        }

    }

    private void loadAd(){

        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {}
        });

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(context, context.getString(R.string.interstitial_ad2), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i("ads", "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        mInterstitialAd = null;
                        Log.i("ads", "onAdLoadFailed" + loadAdError);
                    }
                });

    }

}
