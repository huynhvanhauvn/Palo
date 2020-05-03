package com.sbro.palo.Adapter;

import android.content.Context;
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
import com.sbro.palo.Models.Quote;
import com.sbro.palo.R;

import java.util.ArrayList;

public class ReviewItemAdapter extends RecyclerView.Adapter<ReviewItemAdapter.RecyclerViewHolder> {

    private Context context;
    private ArrayList<Quote> quotes;

    public ReviewItemAdapter(Context context, ArrayList<Quote> quotes) {
        this.context = context;
        this.quotes = quotes;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_review_row,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {
        Quote quote = quotes.get(position);
        holder.txtTitle.setText(quote.getTitle());
        Glide.with(context).load(quote.getPoster()).centerCrop().into(holder.img);
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
        return quotes.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView img;
        private TextView txtTitle;
        private ConstraintLayout line;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (RoundedImageView) itemView.findViewById(R.id.ireview_img_poster);
            txtTitle = (TextView) itemView.findViewById(R.id.ireview_txt_title);
            line = (ConstraintLayout) itemView.findViewById(R.id.ireview_line);
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
