package com.hhub.palo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.hhub.palo.Models.ArtistTrend;
import com.hhub.palo.R;

import java.util.ArrayList;

public class ArtistTrendAdapter extends RecyclerView.Adapter<ArtistTrendAdapter.RecyclerViewHolder> {

    private Context context;
    private ArrayList<ArtistTrend> artists;

    public ArtistTrendAdapter(Context context, ArrayList<ArtistTrend> artists) {
        this.context = context;
        this.artists = artists;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.
                item_artist_trend,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {
        ArtistTrend artist = artists.get(position);
        holder.txtNo.setText((position+1)+"");
        holder.txtNo.setVisibility(View.VISIBLE);
        switch (position) {
            case 0:
                holder.txtNo.setTextColor(context.getResources().getColor(R.color.colorGold));
                break;
            case 1:
                holder.txtNo.setTextColor(context.getResources().getColor(R.color.colorSilver));
                break;
            case 2:
                holder.txtNo.setTextColor(context.getResources().getColor(R.color.colorBronze));
                break;
            default:
                break;
        }
        Glide.with(context).load(artist.getImage()).centerCrop().into(holder.imgAvatar);
        holder.txtName.setText(artist.getName());
        holder.line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null){
                    //return clicked view and its index
                    onItemClickListener.OnItemClick(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView imgAvatar;
        private TextView txtName, txtNo;
        private ConstraintLayout line;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = (RoundedImageView) itemView.findViewById(R.id.artist_avatar);
            txtName = (TextView) itemView.findViewById(R.id.artist_txt_name);
            txtNo = (TextView) itemView.findViewById(R.id.artist_txt_no);
            line = (ConstraintLayout) itemView.findViewById(R.id.iartist_line);
        }
    }

    public ArrayList<ArtistTrend> getArtists() {
        return artists;
    }

    public interface OnItemClickListener{
        public void OnItemClick(View view, int position);
    }

    private RecentAdapter.OnItemClickListener onItemClickListener;

    //method to set item click in adapter
    public void setOnItemClickListener(RecentAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
