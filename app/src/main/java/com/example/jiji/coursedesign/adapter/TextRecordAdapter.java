package com.example.jiji.coursedesign.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jiji.coursedesign.R;
import com.example.jiji.coursedesign.UI.TextDetailActivity;
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
        TextView content;
        TextView alertTime;
        CheckBox checkBox;
        ImageView icon;

        public ViewHolder(View view) {
            super(view);
            textView = view;
            content = (TextView) view.findViewById(R.id.text_content);
            alertTime = (TextView) view.findViewById(R.id.text_alerttime);
            checkBox = (CheckBox) view.findViewById(R.id.text_isdelete);
            icon = (ImageView) view.findViewById(R.id.alertIcon);
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
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击事件
                if (holder.checkBox.getVisibility() == View.INVISIBLE
                        && !holder.checkBox.isChecked()) {
                    int position = holder.getAdapterPosition();
                    //复选框隐藏时打开详情
                    TextRecord record = textRecordList.get(position);

                    Intent intent = new Intent(view.getContext(), TextDetailActivity.class);
                    intent.putExtra("textId", record.getId());
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
        if (tr.getAlertTime() == null || tr.getAlertTime().equals("")) {
            holder.icon.setVisibility(View.INVISIBLE);
        } else {
            holder.alertTime.setText(tr.getAlertTime());
        }


        //长按显示/隐藏
        if (isshowbox) {
            holder.checkBox.setVisibility(View.VISIBLE);
        } else {
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
