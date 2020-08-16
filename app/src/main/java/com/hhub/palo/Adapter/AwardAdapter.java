package com.hhub.palo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hhub.palo.Models.Reward;
import com.hhub.palo.R;

import java.util.ArrayList;

public class AwardAdapter extends RecyclerView.Adapter<AwardAdapter.RecyclerViewHolder> {

    private ArrayList<Reward> rewards;
    private Context context;

    public AwardAdapter(ArrayList<Reward> rewards, Context context) {
        this.rewards = rewards;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_oscar,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Reward reward = rewards.get(position);
        holder.txt.setText(reward.getAward()+": "+reward.getTitle()+" - "+reward.getYear());
    }

    @Override
    public int getItemCount() {
        return rewards.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView txt;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txt = (TextView) itemView.findViewById(R.id.oscar_txt_title);
        }
    }
}
