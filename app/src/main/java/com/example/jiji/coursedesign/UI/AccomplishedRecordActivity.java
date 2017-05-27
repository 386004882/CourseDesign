package com.example.jiji.coursedesign.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.jiji.coursedesign.R;
import com.example.jiji.coursedesign.adapter.CompletedRecordAdapter;
import com.example.jiji.coursedesign.adapter.DividerItemDecoration;
import com.example.jiji.coursedesign.db.CompletedRecord;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import static org.litepal.LitePalApplication.getContext;

/**
 * Created by jiji on 2017/5/27.
 */

public class AccomplishedRecordActivity extends BaseActivity {
    private RecyclerView rv;
    private List<CompletedRecord> completedRecords = new ArrayList<>();
    private CompletedRecordAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accomplishedrecord);

        rv = (RecyclerView) findViewById(R.id.accomplished_rv);
        initData();
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        rv.setLayoutManager(manager);
        adapter = new CompletedRecordAdapter(completedRecords);
        adapter.setRecyclerViewOnClickListener(new CompletedRecordAdapter.RecyclerViewOnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                // TODO: 2017/5/27
            }

            @Override
            public boolean onItemLongClickListener(View view, int position) {
                return false;
            }
        });
        //设置分割线
        rv.addItemDecoration(new DividerItemDecoration(getContext()
                , DividerItemDecoration.VERTICAL_LIST));
        rv.setAdapter(adapter);

    }

    private void initData() {
        completedRecords.clear();
        List<CompletedRecord> recordList = DataSupport.order("time").find(CompletedRecord.class);
        for (CompletedRecord record : recordList) {
            completedRecords.add(record);
        }
    }
}
