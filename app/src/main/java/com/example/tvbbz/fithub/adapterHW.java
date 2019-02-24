package com.example.tvbbz.fithub;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class adapterHW extends RecyclerView.Adapter<adapterHW.viewHolder> {

    RecyclerView recyclerView;
    Context context;
    ArrayList<String> items = new ArrayList<>();
    ArrayList<String> url = new ArrayList<>();

    public void update(String name, String url){

        items.add(name);
        items.add(url);
        notifyDataSetChanged();

    }

    public adapterHW(RecyclerView recyclerView, Context context, ArrayList<String> items, ArrayList<String> url) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.items = items;
        this.url = url;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.fileitem,viewGroup,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {

        viewHolder.nof.setText(items.get(i));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView nof;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            nof = itemView.findViewById(R.id.nameoffile);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = recyclerView.getChildLayoutPosition(v);
                    Intent intent = new Intent();
                    intent.setType(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url.get(position)));
                    context.startActivity(intent);
                }
            });

        }
    }
}
