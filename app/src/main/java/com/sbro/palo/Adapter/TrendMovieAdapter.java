package com.sbro.palo.Adapter;

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
import com.sbro.palo.Models.Movie;
import com.sbro.palo.Models.TrendMovie;
import com.sbro.palo.R;
import com.sbro.palo.Services.APIService;
import com.sbro.palo.Services.Service;

import java.util.ArrayList;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class TrendMovieAdapter extends RecyclerView.Adapter<TrendMovieAdapter.RecylerViewHolder> {

    private Context context;
    private ArrayList<TrendMovie> movies;
    private Service service = APIService.getService();

    public TrendMovieAdapter(Context context, ArrayList<TrendMovie> movies) {
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
        final TrendMovie movie = movies.get(position);
        Glide.with(context).load(movie.getPoster()).centerCrop().into(holder.imgPoster);
        holder.txtTitle.setText(movie.getTitle());
        holder.txtView.setText(movie.getTrend());
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
        private TextView txtTitle, txtDirector, txtView;
        private ConstraintLayout line;

        public RecylerViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = (RoundedImageView) itemView.findViewById(R.id.home_img_poster);
            txtTitle = (TextView) itemView.findViewById(R.id.home_txt_title);
            txtDirector = (TextView) itemView.findViewById(R.id.home_txt_director);
            txtView = (TextView) itemView.findViewById(R.id.home_txt_view);
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
