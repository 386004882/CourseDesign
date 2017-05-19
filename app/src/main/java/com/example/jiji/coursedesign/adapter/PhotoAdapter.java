package com.example.jiji.coursedesign.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jiji.coursedesign.R;
import com.example.jiji.coursedesign.UI.PhotoDetailActivity;
import com.example.jiji.coursedesign.db.Photo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiji on 2017/5/4.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder>
        implements View.OnClickListener, View.OnLongClickListener {
    private Context mcontext;
    private List<Photo> photoList;
    private boolean isshowbox = false;
    private Map<Integer, Boolean> isCheckedMap = new HashMap<>();
    private RecyclerViewOnItemClickListener OnItemClickListener;


    @Override
    public void onClick(View v) {
        if (OnItemClickListener != null) {
            OnItemClickListener.onItemClickListener(v, (Integer) v.getTag());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        //不管显示隐藏，清空状态
        initIsCheckMap();
        return OnItemClickListener != null
                && OnItemClickListener.onItemLongClickListener(v, (Integer) v.getTag());
    }

    //是否显示checkbox
    public void setShowBox() {
        isshowbox = !isshowbox;
    }

    //点击item选中CheckBox
    public void setSelectItem(int position) {
        //对当前状态取反
        if (isCheckedMap.get(position)) {
            isCheckedMap.put(position, false);
        } else {
            isCheckedMap.put(position, true);
        }
        notifyItemChanged(position);
    }

    //接口回调设置点击事件
    public interface RecyclerViewOnItemClickListener {
        //点击事件
        void onItemClickListener(View view, int position);

        //长按事件
        boolean onItemLongClickListener(View view, int position);
    }

    public void setRecyclerViewOnClickListener(RecyclerViewOnItemClickListener onItemClickListener) {
        this.OnItemClickListener = onItemClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View photoView;
        CardView cardView;
        ImageView photoImage;
        CheckBox checkBox;
        TextView photoDesc;

        public ViewHolder(View view) {
            super(view);
            photoView = view;
            cardView = (CardView) view;
            photoImage = (ImageView) view.findViewById(R.id.photo_image);
            photoDesc = (TextView) view.findViewById(R.id.photo_desc);
            checkBox = (CheckBox) view.findViewById(R.id.photo_isdelete);
        }
    }

    public PhotoAdapter(List<Photo> photoList) {
        this.photoList = photoList;
        initIsCheckMap();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mcontext == null) {
            mcontext = parent.getContext();
        }
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item
                , parent, false);
        final ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        holder.photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                //点击事件
                if (holder.checkBox.getVisibility() == View.INVISIBLE
                        && !holder.checkBox.isChecked()) {
                    //复选框隐藏时打开详情
                    Photo photo = photoList.get(position);
                    Intent intent = new Intent(view.getContext(), PhotoDetailActivity.class);
                    intent.putExtra("imageUrl", photo.getImageUrl());
                    view.getContext().startActivity(intent);
                }
            }
        });
//        //长按删除事件
//        holder.photoView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                int position = holder.getAdapterPosition();
//                Photo photo = photoList.get(position);
//                if (holder.checkBox.getVisibility() == View.VISIBLE) {
//                    //显示时,讲checkbox隐藏
//                    holder.checkBox.setVisibility(View.INVISIBLE);
//                    isCheckedMap.put(photo, false);
//                    holder.checkBox.setChecked(false);
//                } else if (holder.checkBox.getVisibility() == View.INVISIBLE) {
//                    //隐藏时
//                    holder.checkBox.setVisibility(View.VISIBLE);
//                    isCheckedMap.put(photo, true);
//                    holder.checkBox.setChecked(true);
//                }
//                return true;
//            }
//        });
        return holder;
    }


    public Map<Integer, Boolean> getIsCheckedMap() {
        return isCheckedMap;
    }

    public void initIsCheckMap() {
        //初始化复选框
        for (int i = 0; i < photoList.size(); i++) {
            isCheckedMap.put(i, false);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Photo photo = photoList.get(position);
        holder.photoDesc.setText(photo.getDescribe());
        Glide.with(mcontext).load(photo.getImageUrl()).into(holder.photoImage);

        //长按显示/隐藏
        if (isshowbox) {
            AnimationSet set = new AnimationSet(true);
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f
                    , Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            set.addAnimation(alphaAnimation);
            set.addAnimation(scaleAnimation);
            set.setDuration(200);
            holder.checkBox.startAnimation(set);
            holder.checkBox.setVisibility(View.VISIBLE);
        } else {
            AnimationSet set = new AnimationSet(true);
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f
                    , Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            set.addAnimation(alphaAnimation);
            set.addAnimation(scaleAnimation);
            set.setDuration(200);
            holder.checkBox.startAnimation(set);
            holder.checkBox.setVisibility(View.INVISIBLE);
        }
        holder.photoView.setTag(position);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton
                .OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCheckedMap.put(position, isChecked);
            }
        });
        //设置Checkbox的状态
        if (isCheckedMap.get(position) == null) {
            isCheckedMap.put(position, false);
        }
        holder.checkBox.setChecked(isCheckedMap.get(position));

    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

}
