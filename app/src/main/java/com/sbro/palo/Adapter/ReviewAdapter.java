package com.sbro.palo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sbro.palo.R;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.RecyclerViewHolder> {

    private Context context;
    private ArrayList<String> quotes;

    public ReviewAdapter(Context context, ArrayList<String> quotes) {
        this.context = context;
        this.quotes = quotes;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_review,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return quotes.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
