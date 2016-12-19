package com.yunspeak.travel.ui.me.myalbum.editalbum.albumselector.pictureselector;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.baseui.BaseToolBarActivity;
import com.yunspeak.travel.ui.me.myalbum.editalbum.albumselector.UpPhotoEvent;
import com.yunspeak.travel.ui.me.myalbum.editalbum.albumselector.ImageFolder;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.me.myalbum.editalbum.albumselector.AlbumSelectorActivity;
import com.yunspeak.travel.ui.me.myalbum.editalbum.albumselector.pictureselector.previewpicture.PreviewPicturesActivity;
import com.yunspeak.travel.utils.ToastUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/23.
 * 相册中图片选择
 */
public class PictureSelectorActivity extends BaseToolBarActivity implements View.OnClickListener {
    @BindView(R.id.gv_photo) GridView mGvPhoto;
    @BindView(R.id.tv_send) TextView mTvSend;
    @BindView(R.id.tv_watch) TextView mTvWatch;
    private List<String> mImages;
    private PictureSelectorAdapter adapter;
    private List<String> mSelectImages=new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerEventBus(this);
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_picture_selector;
    }

    @Override
    protected void initOptions() {
       initViewData();
        initEvent();
    }

    @Override
    protected String initTitle() {
        return "图片选择";
    }




    protected void initEvent() {
        mTvSend.setText("发送");
        if (GlobalValue.mSelectImages != null) {
            mTvSend.setText("发送(" + GlobalValue.mSelectImages.size() + ")");
        }
        mTvSend.setOnClickListener(this);
        mTvWatch.setOnClickListener(this);
    }

    @Override
    protected String initRightText() {
        return "取消";
    }

    @Override
    protected void otherOptionsItemSelected(MenuItem item) {
        cancelOrSelect();
    }



    private void cancelOrSelect() {
        if (GlobalValue.mSelectImages == null || GlobalValue.mSelectImages.size() == 0) {
            ToastUtils.showToast("你尚未选中任何图片");
        } else {
            GlobalValue.mSelectImages = null;
            ToastUtils.showToast("所选照片已清除");
            mTvSend.setText("发送(0)");
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }


    }


    /**
     * 打开预览界面
     */
    private void startWatch() {
        if (GlobalValue.mSelectImages == null || GlobalValue.mSelectImages.size() == 0) {
            ToastUtils.showToast("对不起，你尚未选中任何图片");
        } else {
            startActivityForResult(new Intent(PictureSelectorActivity.this, PreviewPicturesActivity.class), AlbumSelectorActivity.GET_PICTURE);
        }
    }

    protected void initViewData() {
        //获取图片
        ImageFolder mFolder = (ImageFolder) getIntent().getSerializableExtra(IVariable.IMAGE_FOLDER);
        if (mFolder != null) {
            String dir = mFolder.getDir();
            File file = new File(dir);
            mImages = Arrays.asList(file.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    return filename.endsWith(".jpg") || filename.endsWith(".png")
                            || filename.endsWith(".jpeg");
                }
            }));
        }
        if (mImages == null || mImages.size() == 0) {
            ToastUtils.showToast("没有图片");
            return;
        }
        adapter = new PictureSelectorAdapter(this, mImages, mFolder.getDir());
        mGvPhoto.setAdapter(adapter);
        adapter.setOnSelectChangeListener(new PictureSelectorAdapter.OnSelectChangeListener() {
            @Override
            public void change(int size) {
                mTvSend.setText("发送(" + size + ")");
                if (GlobalValue.size==1){
                    //刷新，如果只有一个照片
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AlbumSelectorActivity.GET_PICTURE && resultCode == AlbumSelectorActivity.SEND_PICTURE) {
            setResult(AlbumSelectorActivity.SEND_PICTURE);
            finish();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_send:
                sendPicture();
                break;
            case R.id.tv_watch:
                startWatch();
                break;
        }
    }
    @Subscribe
   public void onEvent(PictureSelectorEvent event){
        if (adapter!=null){
            adapter.notifyDataSetChanged();
            mTvSend.setText("发送("+GlobalValue.mSelectImages.size()+")");
        }
   }
    private void sendPicture() {
        if (GlobalValue.mSelectImages == null || GlobalValue.mSelectImages.size() == 0) {
            ToastUtils.showToast("对不起，你尚未选中任何图片");
        } else {
            mSelectImages.clear();
            mSelectImages.addAll(GlobalValue.mSelectImages);

            UpPhotoEvent upPhotoEvent=new UpPhotoEvent();
            upPhotoEvent.setList(mSelectImages);
            EventBus.getDefault().post(upPhotoEvent);
            setResult(AlbumSelectorActivity.SEND_PICTURE);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterEventBus(this);
        GlobalValue.mSelectImages.clear();
        GlobalValue.mSelectImages.add("add");
    }
}
