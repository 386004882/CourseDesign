package com.example.jiji.coursedesign.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
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
import com.example.jiji.coursedesign.adapter.PhotoAdapter;
import com.example.jiji.coursedesign.db.Photo;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


/**
 * Created by jiji on 2017/5/3.
 */
// TODO: 2017/5/7  添加从相册选择图片


public class PhotoFragment extends Fragment {
    private MainActivity main;
    private DrawerLayout drawer;
    private TextView title;
    private List<Photo> photoList = new ArrayList<>();
    private PhotoAdapter adapter;
    private RecyclerView photoRecycler;
    protected FloatingActionButton fab;
    private Uri imageUri;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        main = (MainActivity) getActivity();
        drawer = (DrawerLayout) main.findViewById(R.id.drawer_layout);
        title = (TextView) main.findViewById(R.id.title_text);
        fab = (FloatingActionButton) view.findViewById(R.id.photo_fab);
        photoRecycler = (RecyclerView) view.findViewById(R.id.recycler_photo);
        setHasOptionsMenu(true);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        initPhoto();
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        photoRecycler.setLayoutManager(layoutManager);
        adapter = new PhotoAdapter(photoList);
        adapter.setRecyclerViewOnClickListener(
                new PhotoAdapter.RecyclerViewOnItemClickListener() {
                    @Override
                    public void onItemClickListener(View view, int position) {
                        adapter.setSelectItem(position);
                    }

                    @Override
                    public boolean onItemLongClickListener(View view, int position) {
                        adapter.setShowBox();
                        adapter.setSelectItem(position);
                        adapter.notifyDataSetChanged();
                        return true;
                    }
                });

        photoRecycler.setAdapter(adapter);

        /**
         * 逻辑：
         * 从每当添加完图片后，将图片存储到本地
         * 将图片地址和信息存入数据库中
         * 每当开启碎片或完成添加后，从数据库中取出数据初始化列表
         */
        //添加图片
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File outputImage = new File(getContext().getExternalCacheDir()
                        , System.currentTimeMillis() + ".jpg");
                try {
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(getContext()
                            , "com.example.jiji.coursedesign.fileprovider", outputImage);
                } else {
                    imageUri = Uri.fromFile(outputImage);
                }
                //启动相机
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, 1);
            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    //初始化图片列表
    private void initPhoto() {
        photoList.clear();
        List<Photo> photoes = DataSupport.findAll(Photo.class);
        for (Photo p : photoes) {
            photoList.add(p);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Intent i = new Intent(getActivity(), PhotoEditActivity.class);
                    i.putExtra("imageUri", imageUri.toString());
                    startActivityForResult(i, 2);
                }
                break;
            case 2:
                if (resultCode == 20) {
                    //通知列表更新
                    initPhoto();

                    adapter.notifyDataSetChanged();
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        title.setVisibility(View.INVISIBLE);
        Utility.showMenu(menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
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
                    AlertDialog isDelete = new AlertDialog.Builder(getContext()).create();
                    isDelete.setTitle("提示");
                    isDelete.setMessage("确认删除？");
                    isDelete.setButton(DialogInterface.BUTTON_POSITIVE, "确认"
                            , new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int i = 0;
                                    for (Integer position : delList) {
                                        DataSupport.deleteAll(Photo.class, "imageUrl=?"
                                                , photoList.get(position).getImageUrl());
                                        i++;
                                    }
                                    initPhoto();
                                    adapter.initIsCheckMap();
                                    adapter.setShowBox();
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
        }
        return true;
    }
}
