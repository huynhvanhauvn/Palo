package com.sbro.palo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.sbro.palo.Models.Artist;
import com.sbro.palo.R;

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
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Artist artist = artists.get(position);
        Glide.with(context).load(artist.getImage()).centerCrop().into(holder.imgAvatar);
        holder.txtName.setText(artist.getName());
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView imgAvatar;
        private TextView txtName;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = (RoundedImageView) itemView.findViewById(R.id.artist_avatar);
            txtName = (TextView) itemView.findViewById(R.id.artist_txt_name);
        }
    }

    public ArrayList<Artist> getArtists() {
        return artists;
    }
}
