package com.example.jiji.coursedesign.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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

public class TextRecordAdapter extends RecyclerView.Adapter<TextRecordAdapter.ViewHolder> {
    private Context mcontext;
    private List<TextRecord> textRecordList;
    private Map<TextRecord, Boolean> isCheckedMap = new HashMap<>();

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
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击事件
                if (holder.checkBox.getVisibility() == View.VISIBLE) {
                    //复选框出现时
                    if (holder.checkBox.isChecked()) {
                        holder.checkBox.setVisibility(View.INVISIBLE);
                    } else {
                        holder.checkBox.setChecked(true);
                    }
                } else {
                    //复选框隐藏时打开详情
                    int position = holder.getAdapterPosition();
                    TextRecord record = textRecordList.get(position);

                    Intent intent = new Intent(view.getContext(), TextDetailActivity.class);
                    intent.putExtra("textId", record.getId());
                    view.getContext().startActivity(intent);
                }
            }
        });
        //长按删除事件
        holder.textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = holder.getAdapterPosition();
                TextRecord record = textRecordList.get(position);
                if (holder.checkBox.getVisibility() == View.VISIBLE) {
                    //显示时,讲checkbox隐藏
                    holder.checkBox.setVisibility(View.INVISIBLE);
                    isCheckedMap.put(record, false);
                    holder.checkBox.setChecked(false);
                } else if (holder.checkBox.getVisibility() == View.INVISIBLE) {
                    //隐藏时
                    holder.checkBox.setVisibility(View.VISIBLE);
                    isCheckedMap.put(record, true);
                    holder.checkBox.setChecked(true);
                }
                return true;
            }
        });
        return holder;
    }

    public Map<TextRecord, Boolean> getIsCheckedMap() {
        return isCheckedMap;
    }

    public void initIsCheckerMap() {
        for (TextRecord record : textRecordList) {
            isCheckedMap.put(record, false);
        }
    }

    @Override
    public void onBindViewHolder(TextRecordAdapter.ViewHolder holder, int position) {
        TextRecord tr = textRecordList.get(position);
        holder.content.setText(tr.getContent());
        if (tr.getAlertTime() == null || tr.getAlertTime().equals("")) {
            holder.icon.setVisibility(View.INVISIBLE);
        } else {
            holder.alertTime.setText(tr.getAlertTime());
        }
    }

    @Override
    public int getItemCount() {
        return textRecordList.size();
    }
}
