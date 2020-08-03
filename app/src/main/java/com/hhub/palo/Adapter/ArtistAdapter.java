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
import com.hhub.palo.Models.Artist;
import com.hhub.palo.R;

import java.util.ArrayList;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.RecyclerViewHolder> {

    private Context context;
    private ArrayList<Artist> artists;

    public ArtistAdapter(Context context, ArrayList<Artist> artists) {
        this.context = context;
        this.artists = artists;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_artist,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {
        Artist artist = artists.get(position);
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
        private TextView txtName;
        private ConstraintLayout line;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = (RoundedImageView) itemView.findViewById(R.id.artist_avatar);
            txtName = (TextView) itemView.findViewById(R.id.artist_txt_name);
            line = (ConstraintLayout) itemView.findViewById(R.id.iartist_line);
        }
    }

    public ArrayList<Artist> getArtists() {
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
