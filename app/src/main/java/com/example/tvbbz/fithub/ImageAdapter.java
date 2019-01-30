package com.example.tvbbz.fithub;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.imageviewholder> {

    private Context mcontext;
    private List<Upload> mupload;
    public ImageAdapter (Context context, List<Upload> uploads){
        mcontext = context;
        mupload = uploads;

    }

    @NonNull
    @Override
    public imageviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.image_item,viewGroup, false);
        return new imageviewholder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull imageviewholder imageviewholder, int i) {

        Upload uploadcurrent = mupload.get(i);
        imageviewholder.textViewname.setText(uploadcurrent.getMname());

        Picasso.with(mcontext)
                .load(uploadcurrent.getMimageurl())
                .fit()
                .centerCrop()
                .into(imageviewholder.imageview);


    }

    @Override
    public int getItemCount() {
        return mupload.size();
    }

    public class imageviewholder extends RecyclerView.ViewHolder{

        public TextView textViewname;
        public ImageView imageview;

        public imageviewholder(@NonNull View itemView) {
            super(itemView);

            textViewname = itemView.findViewById(R.id.textnameview);
            imageview = itemView.findViewById(R.id.imageviewupload);

        }
    }

}
