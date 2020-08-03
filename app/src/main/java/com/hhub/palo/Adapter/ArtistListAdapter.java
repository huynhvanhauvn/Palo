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

public class ArtistListAdapter extends RecyclerView.Adapter<ArtistListAdapter.RecylerViewHolder> {

    private Context context;
    private ArrayList<Artist> artists;

    public ArtistListAdapter(Context context, ArrayList<Artist> artists) {
        this.context = context;
        this.artists = artists;
    }

    @NonNull
    @Override
    public RecylerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recent_movie, parent,false);
        return new RecylerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecylerViewHolder holder, final int position) {
        final Artist artist = artists.get(position);
        Glide.with(context).load(artist.getImage()).centerCrop().into(holder.imgAvatar);
        holder.txtName.setText(artist.getName());
        holder.txtNation.setText(artist.getNation());
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

    public class RecylerViewHolder extends RecyclerView.ViewHolder{

        private RoundedImageView imgAvatar;
        private TextView txtName, txtNation;
        private ConstraintLayout line;

        public RecylerViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = (RoundedImageView) itemView.findViewById(R.id.home_img_poster);
            txtName = (TextView) itemView.findViewById(R.id.home_txt_title);
            txtNation = (TextView) itemView.findViewById(R.id.home_txt_director);
            line = (ConstraintLayout) itemView.findViewById(R.id.recent_line);
        }
    }

    public interface OnItemClickListener{
        public void OnItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    //method to set item click in adapter
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
