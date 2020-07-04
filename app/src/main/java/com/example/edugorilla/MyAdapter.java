package com.example.edugorilla;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<model> listItem ;
    private Context context;

    public MyAdapter(List<model> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.first_activity_design, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        model models = listItem.get(position);

        holder.Id_json.setText(models.getId());
        holder.Name_json.setText(models.getName());
        holder.Email_json.setText(models.getEmail());
        holder.Layout_json.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , GraphActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView Id_json;
        public TextView Name_json;
        public TextView Email_json;
        public RelativeLayout Layout_json;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Id_json = itemView.findViewById(R.id.id_json);
            Name_json = itemView.findViewById(R.id.name_json);
            Email_json = itemView.findViewById(R.id.email_json);
            Layout_json = itemView.findViewById(R.id.linearLayout);


        }
    }
}
