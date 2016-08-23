package com.example.administrator.travel.ui.activity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.ImageFolder;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.SelectFolderAdapter;
import com.example.administrator.travel.utils.ToastUtils;

import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AlbumSelectorActivity extends BarBaseActivity {
    @ViewInject(R.id.lv_photo)
    private ListView mLvPhoto;
    private ProgressDialog mProgressDialog;
    private List<ImageFolder> imageFolders = new ArrayList<>();
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mProgressDialog.dismiss();
            if (imageFolders.size()==0){
                ToastUtils.showToast("没有图片！");
            }else {
                mLvPhoto.setAdapter(new SelectFolderAdapter(AlbumSelectorActivity.this,imageFolders));
            }

        }
    };

    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashSet<String> mDirPaths = new HashSet<String>();
    private TextView mTvCancel;

    private void initImages() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            mProgressDialog = ProgressDialog.show(this, null, "扫描中...");
            //子线程遍历图片
            new Thread(new Runnable() {
                @Override
                public void run() {
                    searchPhotoOnSd();//获取图片
                }
            }).start();

        } else {
            ToastUtils.showToast("SD卡未挂载");
        }
    }

    /**
     * 扫描SD卡图片
     */
    private void searchPhotoOnSd() {

        Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = AlbumSelectorActivity.this.getContentResolver();
        // 只查询jpeg和png的图片
        Cursor mCursor = mContentResolver.query(imageUri, null,
                MediaStore.Images.Media.MIME_TYPE + "=? or "
                        + MediaStore.Images.Media.MIME_TYPE + "=?",
                new String[]{"image/jpeg", "image/png"},
                MediaStore.Images.Media.DATE_MODIFIED);
        if (mCursor==null)return;
        while (mCursor.moveToNext()) {
            // 获取图片的路径
            String path = mCursor.getString(mCursor
                    .getColumnIndex(MediaStore.Images.Media.DATA));
            // 获取该图片的父路径名
            File parentFile = new File(path).getParentFile();
            if (parentFile == null) {
                continue;
            }
            String dirPath = parentFile.getAbsolutePath();
            ImageFolder imageFolder = null;
            if (mDirPaths.contains(dirPath)) {
                continue;//继续下次
            } else {
                mDirPaths.add(dirPath);
                imageFolder = new ImageFolder();
                imageFolder.setDir(dirPath);
                imageFolder.setFirstImagePath(path);
                //计算当前文件夹一共有多少张图片
                int dirCount = parentFile.list(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String filename) {
                        if (filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".png")) {
                            return true;
                        }
                        return false;
                    }
                }).length;

                imageFolder.setCount(dirCount);
                imageFolders.add(imageFolder);
            }
        }
        mCursor.close();
        mDirPaths=null;
        //发送消息遍历完毕
        mHandler.sendEmptyMessage(0);

    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_album_selector;
    }

    @Override
    protected void initEvent() {
        getmVsRightIcon().inflate();
        mTvCancel = (TextView) findViewById(R.id.tv_ok);
        mTvCancel.setText("取消");
        mTvCancel.setTextSize(12, TypedValue.COMPLEX_UNIT_SP);
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mLvPhoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(AlbumSelectorActivity.this,PictureSelectorActivity.class);
                ImageFolder imageFolder = imageFolders.get(position);
                intent.putExtra(IVariable.IMAGE_FOLDER,imageFolder);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void initViewData() {
        initImages();
        getmTvBack().setVisibility(View.GONE);


    }

    @Override
    protected String setTitleName() {
        return "相册目录";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }

    @Override
    protected boolean haveRightIcon() {
        return true;
    }
}
