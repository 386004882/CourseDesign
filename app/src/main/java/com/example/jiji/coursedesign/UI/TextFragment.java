package com.example.jiji.coursedesign.UI;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.jiji.coursedesign.R;
import com.example.jiji.coursedesign.Utils.Utility;

/**
 * Created by jiji on 2017/5/4.
 */

public class TextFragment extends Fragment {
    private FloatingActionButton fab;
    private MainActivity main;
    private DrawerLayout drawerLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text, container, false);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        main = (MainActivity) getActivity();
        drawerLayout = (DrawerLayout) main.findViewById(R.id.drawer_layout);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Utility.showMenu(menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home://打开侧滑菜单
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.item_backup://上传备份

                break;
            case R.id.item_delete://删除

                Snackbar.make(fab, "成功删除数据", Snackbar.LENGTH_SHORT)
                        .setAction("撤销", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //撤销逻辑

                            }
                        }).show();
                break;
            case R.id.item_setting://设置

                break;

        }
        return true;
    }
}
