package com.example.jiji.coursedesign.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiji.coursedesign.R;
import com.example.jiji.coursedesign.Utils.Utility;
import com.example.jiji.coursedesign.adapter.DividerItemDecoration;
import com.example.jiji.coursedesign.adapter.TextRecordAdapter;
import com.example.jiji.coursedesign.db.CompletedRecord;
import com.example.jiji.coursedesign.db.TextRecord;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiji on 2017/5/4.
 */

// TODO: 2017/5/26 加入颜色分辨不同类型
public class TextFragment extends Fragment {
    private static final int CHOOSE = 1;
    private static final int TEXT_OK = 10;
    private FloatingActionButton fab;
    private MainActivity main;
    private DrawerLayout drawerLayout;
    private TextView title;
    private RecyclerView textRecycler;
    private List<TextRecord> textRecordList = new ArrayList<>();
    private TextRecordAdapter adapter;

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
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        textRecycler.setLayoutManager(manager);
        adapter = new TextRecordAdapter(textRecordList);
        adapter.setRecyclerViewOnClickListener(new TextRecordAdapter.RecyclerViewOnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                adapter.setSelectItem(position);
            }

            @Override
            public boolean onItemLongClickListener(View view, int position) {
                adapter.initIsCheckedMap();
                adapter.setShowBox();
                adapter.notifyDataSetChanged();
                return true;
            }
        });
        //设置分割线
        textRecycler.addItemDecoration(new DividerItemDecoration(getContext()
                , DividerItemDecoration.VERTICAL_LIST));
        textRecycler.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationSet set = new AnimationSet(true);
                AlphaAnimation alpha = new AlphaAnimation(1.0f, 0.0f);
                ScaleAnimation scale = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f
                        , Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                set.addAnimation(scale);
                set.addAnimation(alpha);
                set.setDuration(100);
                set.setFillAfter(true);
                fab.startAnimation(set);
                set.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent = new Intent(getContext(), ChooseFunctionActivity.class);
                        startActivityForResult(intent, CHOOSE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

            }
        });

    }

    //初始化列表数据
    private void initText() {
        textRecordList.clear();
        List<TextRecord> recordList = DataSupport.order("time").find(TextRecord.class);
        for (TextRecord record : recordList) {
            textRecordList.add(record);
        }
    }

    @Override
    public void onResume() {
        initText();
        adapter.notifyDataSetChanged();
        AnimationSet set = new AnimationSet(true);
        AlphaAnimation alpha = new AlphaAnimation(0.0f, 1.0f);
        ScaleAnimation scale = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f
                , Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        set.setDuration(400);
        set.addAnimation(alpha);
        set.addAnimation(scale);
        fab.startAnimation(set);
        super.onResume();
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
                final HashMap<Integer, Boolean> isCheckedMap = (HashMap) adapter.getIsCheckedMap();
                int i = 0;
                final List<Integer> delList = new ArrayList<>();
                for (Map.Entry<Integer, Boolean> entry : isCheckedMap.entrySet()) {
                    if (entry.getValue()) {
                        //复选框选中
                        delList.add(entry.getKey());
                        i++;
                    }
                }
                if (i > 0) {
                    Utility.showConfirmation(getContext(), "确认删除?", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int j = 0;
                            for (Integer position : delList) {
                                DataSupport.deleteAll(TextRecord.class, "id=?"
                                        , textRecordList.get(position).getId() + "");
                                j++;
                            }
                            initText();
                            adapter.initIsCheckedMap();
                            adapter.setShowBox();
                            adapter.notifyDataSetChanged();
                            Snackbar.make(fab, "成功删除" + j + "个所选项", Snackbar.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(main, "长按选择删除项", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.item_setting://设置
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.item_complete:
                //设置便签事件完成
                final HashMap<Integer, Boolean> isCheckedMap1 = (HashMap) adapter.getIsCheckedMap();
                int k = 0;
                final List<Integer> delList1 = new ArrayList<>();
                for (Map.Entry<Integer, Boolean> entry : isCheckedMap1.entrySet()) {
                    if (entry.getValue()) {
                        //复选框选中
                        delList1.add(entry.getKey());
                        k++;
                    }
                }
                if (k > 0) {
                    Utility.showConfirmation(getContext(), "确认设置为已完成事项?", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int j = 0;
                            for (Integer position : delList1) {
                                //添加完成对象
                                CompletedRecord completedRecord = new CompletedRecord();
                                completedRecord.setId(textRecordList.get(position).getId());
                                completedRecord.setTitle(textRecordList.get(position).getTitle());
                                completedRecord.setContent(textRecordList.get(position).getContent());
                                completedRecord.setTime(textRecordList.get(position).getTime());
                                completedRecord.setAlertTime(textRecordList.get(position).getAlertTime());
                                completedRecord.setType(textRecordList.get(position).getType());
                                completedRecord.save();
                                DataSupport.deleteAll(TextRecord.class, "id=?"
                                        , textRecordList.get(position).getId() + "");
                                j++;
                            }
                            initText();
                            adapter.initIsCheckedMap();
                            adapter.setShowBox();
                            adapter.notifyDataSetChanged();
                            Snackbar.make(fab, j + "个事件已完成，可在设置和个人页面查看", Snackbar.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(main, "长按选择完成项", Toast.LENGTH_SHORT).show();
                }

                break;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHOOSE: //选择功能，在此页面返回为了更新文字标签列表,如果是其他功能，则不需处理
                if (resultCode == TEXT_OK) {
                    initText();
                    adapter.notifyDataSetChanged();
                    break;
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
