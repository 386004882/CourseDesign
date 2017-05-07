package com.example.jiji.coursedesign.UI;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiji.coursedesign.R;
import com.example.jiji.coursedesign.Utils.Utility;
import com.example.jiji.coursedesign.adapter.TextRecordAdapter;
import com.example.jiji.coursedesign.db.TextRecord;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiji on 2017/5/4.
 */

public class TextFragment extends Fragment {
    private FloatingActionButton fab;
    private MainActivity main;
    private DrawerLayout drawerLayout;
    private TextView title;
    private RecyclerView textRecycler;
    private List<TextRecord> textRecordList = new ArrayList<>();
    private TextRecordAdapter adapter;
    private static MainActivity instance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text, container, false);
        fab = (FloatingActionButton) view.findViewById(R.id.text_fab);
        main = (MainActivity) getActivity();
        drawerLayout = (DrawerLayout) main.findViewById(R.id.drawer_layout);
        title = (TextView) main.findViewById(R.id.title_text);
        textRecycler = (RecyclerView) view.findViewById(R.id.text_recycler);

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initText();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        textRecycler.setLayoutManager(manager);
        adapter = new TextRecordAdapter(textRecordList);
        textRecycler.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击新建便签
                Intent intent = new Intent(getActivity(), TextEditActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        instance = (MainActivity) getActivity();

    }

    //初始化列表数据
    private void initText() {
        textRecordList.clear();
        List<TextRecord> recordList = DataSupport.findAll(TextRecord.class);
        for (TextRecord record : recordList) {
            textRecordList.add(record);
        }
    }

    @Override
    public void onResume() {
        initText();
        super.onResume();
    }

    @Override
    public void onPause() {
        initText();
        super.onPause();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Utility.showMenu(menu);
        title.setVisibility(View.INVISIBLE);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home://打开侧滑菜单
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.item_delete:
                //删除按钮逻辑
                final HashMap<TextRecord, Boolean> isCheckedMap = (HashMap) adapter.getIsCheckedMap();
                int i = 0;
                final List<TextRecord> delList = new ArrayList<>();
                for (Map.Entry<TextRecord, Boolean> entry : isCheckedMap.entrySet()) {
                    if (entry.getValue()) {
                        //复选框选中
                        delList.add(entry.getKey());
                        i++;
                    }
                }
                if (i > 0) {
                    AlertDialog isDelete = new AlertDialog.Builder(getContext()).create();
                    isDelete.setTitle("提示");
                    isDelete.setMessage("确认删除？");
                    isDelete.setButton(DialogInterface.BUTTON_POSITIVE, "确认"
                            , new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int i = 0;
                                    for (TextRecord textRecord : delList) {
                                        DataSupport.deleteAll(TextRecord.class, "id=?", textRecord.getId() + "");
                                        i++;
                                    }
                                    initText();
                                    adapter.initIsCheckerMap();
                                    adapter.notifyDataSetChanged();
                                    Snackbar.make(fab, "成功删除" + i + "个所选项", Snackbar.LENGTH_SHORT).show();
                                }
                            });
                    isDelete.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    isDelete.show();
                } else {
                    Toast.makeText(main, "请长按选择删除项", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.item_setting://设置

                break;

        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1://新建便签
                initText();
                adapter.notifyDataSetChanged();
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static MainActivity getInstance() {
        return instance;
    }

    public class TextDataChangeBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            getInstance().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    initText();
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

}
