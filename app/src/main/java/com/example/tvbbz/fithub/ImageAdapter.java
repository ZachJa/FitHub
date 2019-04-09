package com.example.tvbbz.fithub;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
    private OnItemClickListenerimage mListener;


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
        imageviewholder.textdesc.setText(uploadcurrent.getMdesc());

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

    public class imageviewholder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener,MenuItem.OnMenuItemClickListener{

        public TextView textViewname,textdesc;
        public ImageView imageview;

        public imageviewholder(@NonNull View itemView) {
            super(itemView);

            textViewname = itemView.findViewById(R.id.textnameview);
            textdesc = itemView.findViewById(R.id.textdescview);
            imageview = itemView.findViewById(R.id.imageviewupload);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mListener!=null){
                int position = getAdapterPosition();

                if(position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            menu.setHeaderTitle("Select Action");
            MenuItem delete = menu.add(Menu.NONE, 1,1, "Delete");

            delete.setOnMenuItemClickListener(this);

        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener!=null){
                int position = getAdapterPosition();

                if(position != RecyclerView.NO_POSITION){
                    switch (item.getItemId()){
                        case 1:
                        mListener.onDeleteCLick(position);
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public interface OnItemClickListenerimage{
        void onItemClick(int position);
        void onDeleteCLick(int position);
    }

    public void setOnItemCLickListenerimage(OnItemClickListenerimage listener){

        mListener = listener;

    }


}
