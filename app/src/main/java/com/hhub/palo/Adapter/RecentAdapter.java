package com.hhub.palo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.hhub.palo.Models.Movie;
import com.hhub.palo.R;
import com.hhub.palo.Services.APIService;
import com.hhub.palo.Services.Service;

import java.util.ArrayList;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.RecylerViewHolder> {

    private Context context;
    private ArrayList<Movie> movies;
    private Service service = APIService.getService();
    private int lastPosition = -1;

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
        Observable<ArrayList<String>> nameObservable = service.artistName(movie.getId(), 1);
        nameObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<String> s) {
                        if(s!=null && s.size()>0) {
                            String names = s.get(0);
                            if(s.size()>1) {
                                for (int i=1; i<s.size(); i++) {
                                    names = names + ", " + s.get(i);
                                }
                            }
                            holder.txtDirector.setText(names);
                        }
                    }
                });
        Observable<String> rewardObservable = service.checkMovieWinReward(movie.getId());
        rewardObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        if(s != null && s.equals("true")) {
                            holder.imgOscar.setVisibility(View.VISIBLE);
                        } else {
                            holder.imgOscar.setVisibility(View.GONE);
                        }
                    }
                });
        holder.line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null){
                    //return clicked view and its index
                    onItemClickListener.OnItemClick(v, position);
                }
            }
        });
        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastPosition) ? R.anim.anim_load_list
                        : R.anim.anim_load_list_out);
        holder.line.startAnimation(animation);
        lastPosition = position;
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class RecylerViewHolder extends RecyclerView.ViewHolder{

        private RoundedImageView imgPoster;
        private TextView txtTitle, txtDirector;
        private ConstraintLayout line;
        private ImageView imgOscar;

        public RecylerViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = (RoundedImageView) itemView.findViewById(R.id.home_img_poster);
            txtTitle = (TextView) itemView.findViewById(R.id.home_txt_title);
            txtDirector = (TextView) itemView.findViewById(R.id.home_txt_director);
            line = (ConstraintLayout) itemView.findViewById(R.id.recent_line);
            imgOscar = (ImageView) itemView.findViewById(R.id.home_img_oscar);
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
