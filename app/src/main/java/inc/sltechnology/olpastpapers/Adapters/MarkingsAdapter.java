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

import inc.sltechnology.olpastpapers.Activities.MarkingsActivity;
import inc.sltechnology.olpastpapers.Models.MarkingsModel;
import inc.sltechnology.olpastpapers.R;

public class MarkingsAdapter extends RecyclerView.Adapter<MarkingsAdapter.MyViewHolder> {

    Context context;
    private List<MarkingsModel> markingsModels;

    public MarkingsAdapter(Context context, List<MarkingsModel> markingsModels) {
        this.context = context;
        this.markingsModels = markingsModels;
    }

    @NonNull
    @Override
    public MarkingsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.marking_layout, parent, false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MarkingsAdapter.MyViewHolder holder, int position) {

        Glide.with(holder.img.getContext())
                .load(markingsModels.get(holder.getAdapterPosition()).getImg()).into(holder.img);

        holder.title.setText(markingsModels.get(holder.getAdapterPosition()).getTitle());

        holder.subTitle.setText(markingsModels.get(holder.getAdapterPosition()).getSubTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(context, MarkingsActivity.class);
                intent1.putExtra("subject_title", markingsModels.get(holder.getAdapterPosition()).getTitle());
                intent1.putExtra("subject_img", markingsModels.get(holder.getAdapterPosition()).getImg());
                intent1.putExtra("markings_list", markingsModels.get(holder.getAdapterPosition()).getMarkings_year());
                context.startActivity(intent1);

            }
        });

    }

    @Override
    public int getItemCount() {
        return markingsModels.size();
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
