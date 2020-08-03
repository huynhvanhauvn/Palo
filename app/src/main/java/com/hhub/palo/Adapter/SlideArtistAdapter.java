package com.hhub.palo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.hhub.palo.Models.Artist;
import com.hhub.palo.R;

import java.util.ArrayList;

public class SlideArtistAdapter extends RecyclerView.Adapter<SlideArtistAdapter.RecycleViewHolder> {

    private Context context;
    private ArrayList<Artist> artists;
    private ViewPager2 viewPager2;
    LayoutInflater layoutInflater;

    public SlideArtistAdapter(Context context, ArrayList<Artist> artists, ViewPager2 viewPager2) {
        this.context = context;
        this.artists = artists;
        this.viewPager2 = viewPager2;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_slide_artist,parent,false);
        return new RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder holder, final int position) {
        Artist artist = artists.get(position);
        Glide.with(context).load(artist.getImage()).into(holder.imageView);
        holder.txtName.setText(artist.getName());
        holder.txtBirthday.setText(artist.getBirthday());
        holder.txtNation.setText(artist.getNation());
        if(position == artists.size()-1) {
            viewPager2.post(slideRunnable);
        }
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

    public class RecycleViewHolder extends RecyclerView.ViewHolder {

        private RoundedImageView imageView;
        private TextView txtName, txtBirthday, txtNation;
        private ConstraintLayout line;

        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (RoundedImageView) itemView.findViewById(R.id.islide_img_avatar);
            txtName = (TextView) itemView.findViewById(R.id.islide_txt_name);
            txtBirthday = (TextView) itemView.findViewById(R.id.islide_txt_birthday);
            txtNation = (TextView) itemView.findViewById(R.id.islide_txt_nation);
            line = (ConstraintLayout) itemView.findViewById(R.id.islide_line);
        }
    }

    private Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            artists.addAll(artists);
            notifyDataSetChanged();
        }
    };

    public interface OnItemClickListener{
        public void OnItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    //method to set item click in adapter
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
