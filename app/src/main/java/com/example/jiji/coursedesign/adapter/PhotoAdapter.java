package com.example.jiji.coursedesign.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jiji.coursedesign.R;
import com.example.jiji.coursedesign.db.Photo;

import java.util.List;

/**
 * Created by jiji on 2017/5/4.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    private Context mcontext;
    private List<Photo> photoList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View photoView;
        CardView cardView;
        ImageView photoImage;
        TextView photoDesc;

        public ViewHolder(View view) {
            super(view);
            photoView = view;
            cardView = (CardView) view;
            photoImage = (ImageView) view.findViewById(R.id.photo_image);
            photoDesc = (TextView) view.findViewById(R.id.photo_desc);
        }
    }

    public PhotoAdapter(List<Photo> photoList) {
        this.photoList = photoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mcontext == null) {
            mcontext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item
                , parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击事件
                int position = holder.getAdapterPosition();
                Photo photo = photoList.get(position);
                Toast.makeText(mcontext, "you click" + photo.getDescribe(), Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Photo photo = photoList.get(position);
        holder.photoDesc.setText(photo.getDescribe());
        Glide.with(mcontext).load(photo.getImageUrl()).into(holder.photoImage);
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

}
