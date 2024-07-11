package com.rishipm.earneasyquizie.Activities.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rishipm.earneasyquizie.Activities.Activity.QuizActivity;
import com.rishipm.earneasyquizie.Activities.Model.CategoryModel;
import com.rishipm.earneasyquizie.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context context;
    ArrayList<CategoryModel> categoryModels;

    public CategoryAdapter(Context context, ArrayList<CategoryModel> categoryModels) {
        this.context = context;
        this.categoryModels = categoryModels;
    }

    public CategoryAdapter() {
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, null);
        return new CategoryViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryModel model = categoryModels.get(position);
        holder.tvCatName.setText(model.getCatName());
        Glide.with(context)
                .load(model.getCatImage())
                .into(holder.catImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, QuizActivity.class);
                intent.putExtra("catId", model.getCatId());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryModels.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        ImageView catImage;
        TextView tvCatName;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            catImage = itemView.findViewById(R.id.item_img_cat);
            tvCatName = itemView.findViewById(R.id.tv_cat_name);


        }
    }
}
