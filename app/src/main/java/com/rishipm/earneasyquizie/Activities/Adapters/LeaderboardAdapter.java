package com.rishipm.earneasyquizie.Activities.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rishipm.earneasyquizie.Activities.Model.User;
import com.rishipm.earneasyquizie.R;
import com.rishipm.earneasyquizie.databinding.RawLeaderboardBinding;

import java.util.ArrayList;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder> {

    Context context;
    ArrayList<User> users;

    public LeaderboardAdapter() {
    }

    public LeaderboardAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.raw_leaderboard, parent, false);
        return new LeaderboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardViewHolder holder, int position) {

        User user = users.get(position);
        holder.binding.nameTv.setText(user.getName());
        holder.binding.coinsScoreTv.setText(String.valueOf(user.getCoins()));
        holder.binding.index.setText(String.format("#%d", position+1));

        Glide.with(context)
                .load(user.getProfile())
                .into(holder.binding.profileImage);

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class LeaderboardViewHolder extends RecyclerView.ViewHolder {

        RawLeaderboardBinding binding;

        public LeaderboardViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RawLeaderboardBinding.bind(itemView);

        }
    }

    }
