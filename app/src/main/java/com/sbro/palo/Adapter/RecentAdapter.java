package com.sbro.palo.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.sbro.palo.Models.Movie;
import com.sbro.palo.R;
import com.sbro.palo.Services.APIService;
import com.sbro.palo.Services.Service;

import java.util.ArrayList;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.RecylerViewHolder> {

    private Context context;
    private ArrayList<Movie> movies;

    public RecentAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public RecylerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recent_movie, parent,false);
        return new RecylerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecylerViewHolder holder, final int position) {
        final Movie movie = movies.get(position);
        Glide.with(context).load(movie.getPoster()).centerCrop().into(holder.imgPoster);
        holder.txtTitle.setText(movie.getTitle());
        if(!movie.getDirector().equals("")){
            String[] strings = movie.getDirector().split("/");
            Service service = APIService.getService();
            final String[] directorNames = {""};
            for(String string : strings) {
                Observable<String> nameObservable = service.artistName(string);
                nameObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                if(directorNames[0].equals("")) {
                                    directorNames[0] = s;
                                } else {
                                    directorNames[0] = directorNames[0] + ", " + s;
                                }
                                holder.txtDirector.setText(directorNames[0]);
                            }
                        });
            }
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
        return movies.size();
    }

    public class RecylerViewHolder extends RecyclerView.ViewHolder{

        private RoundedImageView imgPoster;
        private TextView txtTitle, txtDirector;
        private ConstraintLayout line;

        public RecylerViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = (RoundedImageView) itemView.findViewById(R.id.home_img_poster);
            txtTitle = (TextView) itemView.findViewById(R.id.home_txt_title);
            txtDirector = (TextView) itemView.findViewById(R.id.home_txt_director);
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
