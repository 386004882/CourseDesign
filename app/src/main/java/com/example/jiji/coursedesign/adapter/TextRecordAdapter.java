package com.example.jiji.coursedesign.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jiji.coursedesign.R;
import com.example.jiji.coursedesign.UI.TextEditActivity;
import com.example.jiji.coursedesign.db.TextRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiji on 2017/5/6.
 */

public class TextRecordAdapter extends RecyclerView.Adapter<TextRecordAdapter.ViewHolder>
        implements View.OnClickListener, View.OnLongClickListener {
    private Context mcontext;
    private List<TextRecord> textRecordList;
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
        initIsCheckedMap();
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
        View textView;
        TextView text_type;
        TextView content;
        TextView title;
        TextView time;
        TextView alertTime;
        CheckBox checkBox;
        ImageView icon;

        public ViewHolder(View view) {
            super(view);
            textView = view;
            content = (TextView) view.findViewById(R.id.text_content);
            text_type = (TextView) view.findViewById(R.id.text_type);
            alertTime = (TextView) view.findViewById(R.id.text_alerttime);
            checkBox = (CheckBox) view.findViewById(R.id.text_isdelete);
            title = (TextView) view.findViewById(R.id.text_title);
            icon = (ImageView) view.findViewById(R.id.alertIcon);
            time = (TextView) view.findViewById(R.id.text_time);
        }
    }

    public TextRecordAdapter(List<TextRecord> textRecordList) {
        this.textRecordList = textRecordList;
    }

    @Override
    public TextRecordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mcontext == null) {
            mcontext = parent.getContext();
        }
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.textrecord_item
                , parent, false);
        final ViewHolder holder = new ViewHolder(view);
        Animation animation = AnimationUtils.loadAnimation(parent.getContext(), R.anim.anim_function_show);
        animation.setDuration(500);
        view.startAnimation(animation);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                //点击事件
                if (holder.checkBox.getVisibility() == View.INVISIBLE
                        && !holder.checkBox.isChecked()) {
                    //复选框隐藏时打开详情
                    TextRecord record = textRecordList.get(position);
                    Intent intent = new Intent(view.getContext(), TextEditActivity.class);
                    intent.putExtra("record", record);
                    view.getContext().startActivity(intent);
                }
            }
        });
//        //长按删除事件
//        holder.textView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                int position = holder.getAdapterPosition();
//                TextRecord record = textRecordList.get(position);
//                if (holder.checkBox.getVisibility() == View.VISIBLE) {
//                    //显示时,讲checkbox隐藏
//                    holder.checkBox.setVisibility(View.INVISIBLE);
//                    isCheckedMap.put(record, false);
//                    holder.checkBox.setChecked(false);
//                } else if (holder.checkBox.getVisibility() == View.INVISIBLE) {
//                    //隐藏时
//                    holder.checkBox.setVisibility(View.VISIBLE);
//                    isCheckedMap.put(record, true);
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

    public void initIsCheckedMap() {
        for (int i = 0; i < textRecordList.size(); i++) {
            isCheckedMap.put(i, false);
        }
    }

    @Override
    public void onBindViewHolder(TextRecordAdapter.ViewHolder holder, final int position) {
        TextRecord tr = textRecordList.get(position);
        holder.content.setText(tr.getContent());
        holder.time.setText(tr.getTime());
        holder.title.setText(tr.getTitle());
        if (tr.getAlertTime() == null || tr.getAlertTime().equals("")) {
            holder.icon.setVisibility(View.INVISIBLE);
        } else {
            holder.alertTime.setText(tr.getAlertTime());
        }
        switch (tr.getType()) {
            case 0://灰色
                holder.text_type.setBackgroundColor(Color.argb(255, 136, 136, 136));
                break;
            case 1://蓝
                holder.text_type.setBackgroundColor(Color.argb(255, 11, 86, 162));
                break;
            case 2://紫
                holder.text_type.setBackgroundColor(Color.argb(255, 74, 43, 154));
                break;
            case 3://绿
                holder.text_type.setBackgroundColor(Color.argb(255, 30, 95, 2));
                break;
            case 4://橙
                holder.text_type.setBackgroundColor(Color.argb(255, 214, 119, 29));
                break;
        }


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
        holder.textView.setTag(position);
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
        return textRecordList.size();
    }
}
