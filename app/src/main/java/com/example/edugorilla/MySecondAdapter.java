package com.example.edugorilla;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MySecondAdapter extends RecyclerView.Adapter<MySecondAdapter.ViewHolder> {

    private List<model_third > listItem;

    public MySecondAdapter(List<model_third> listItem, Context applicationContext) {
        this.listItem = listItem;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trial_design, parent,false);
        return new ViewHolder(v);    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        model_third models = listItem.get(position);

        holder.amount_from_json.setText(models.getAmount());
        holder.date_created_from_json.setText(models.getDate_created());

    }

    @Override
    public int getItemCount() {
        return  listItem.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView date_created_from_json;
        private TextView amount_from_json;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date_created_from_json = itemView.findViewById(R.id.date_json);
            amount_from_json = itemView.findViewById(R.id.amount_json);

        }
    }
}
