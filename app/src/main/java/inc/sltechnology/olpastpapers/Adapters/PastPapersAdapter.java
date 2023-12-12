package inc.sltechnology.olpastpapers.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import inc.sltechnology.olpastpapers.Activities.PastPapersActivity;
import inc.sltechnology.olpastpapers.Models.PastPapersModel;
import inc.sltechnology.olpastpapers.R;

public class PastPapersAdapter extends RecyclerView.Adapter<PastPapersAdapter.MyViewHolder> {

    Context context;
    private List<PastPapersModel> pastPapersModels;

    public PastPapersAdapter(Context context, List<PastPapersModel> pastPapersModels) {
        this.context = context;
        this.pastPapersModels = pastPapersModels;
    }

    @NonNull
    @Override
    public PastPapersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.past_paper_layout, parent, false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PastPapersAdapter.MyViewHolder holder, int position) {

        Glide.with(holder.img.getContext())
                .load(pastPapersModels.get(holder.getAdapterPosition()).getImg()).into(holder.img);

        holder.title.setText(pastPapersModels.get(holder.getAdapterPosition()).getTitle());

        holder.subTitle.setText(pastPapersModels.get(holder.getAdapterPosition()).getSubTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, PastPapersActivity.class);
                intent.putExtra("subject_title", pastPapersModels.get(holder.getAdapterPosition()).getTitle());
                intent.putExtra("subject_img", pastPapersModels.get(holder.getAdapterPosition()).getImg());
                intent.putExtra("past_papers_list", pastPapersModels.get(holder.getAdapterPosition()).getPast_papers_year());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return pastPapersModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView title, subTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img_main);
            title = itemView.findViewById(R.id.txt_main);
            subTitle = itemView.findViewById(R.id.txt_SubTitle);

        }

    }

}
