package com.yunspeak.travel.ui.me.myalbum.editalbum.albumselector;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.ImageFolder;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseToolBarActivity;
import com.yunspeak.travel.ui.me.myalbum.editalbum.albumselector.pictureselector.PictureSelectorActivity;
import com.yunspeak.travel.utils.ToastUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;

/**
 * 相册选择 powered by wangyang
 */
public class AlbumSelectorActivity extends BaseToolBarActivity {
    public static final int GET_PICTURE=16;
    public static final int SEND_PICTURE=61;
    @BindView(R.id.lv_photo) ListView mLvPhoto;
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
    private HashSet<String> mDirPaths = new HashSet<>();

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
     * 请求相关权限
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_STORAGE_READ_ACCESS_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    searchPhotoOnSd();
                }
                break;
        }
    }
    /**
     * 扫描SD卡图片
     */
    private void searchPhotoOnSd() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.permission_read_storage_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        }else {

            Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            ContentResolver mContentResolver = AlbumSelectorActivity.this.getContentResolver();
            // 只查询jpeg和png的图片
            Cursor mCursor = mContentResolver.query(imageUri, null,
                    MediaStore.Images.Media.MIME_TYPE + "=? or "
                            + MediaStore.Images.Media.MIME_TYPE + "=?",
                    new String[]{"image/jpeg", "image/png"},
                    MediaStore.Images.Media.DATE_MODIFIED);
            if (mCursor == null) return;
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
                ImageFolder imageFolder ;
                if (!mDirPaths.contains(dirPath)) {
                    mDirPaths.add(dirPath);
                    imageFolder = new ImageFolder();
                    imageFolder.setDir(dirPath);
                    imageFolder.setFirstImagePath(path);
                    //计算当前文件夹一共有多少张图片
                    int dirCount = parentFile.list(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String filename) {
                            return filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".png");
                        }
                    }).length;

                    imageFolder.setCount(dirCount);
                    imageFolders.add(imageFolder);
                }
            }
            mCursor.close();
            mDirPaths = null;
            //发送消息遍历完毕
            mHandler.sendEmptyMessage(0);
        }

    }

    @Override
    protected String initRightText() {
        return "取消";
    }

    @Override
    protected void otherOptionsItemSelected(MenuItem item) {
        initSelect();
    }

    protected void initEvent() {
        mLvPhoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AlbumSelectorActivity.this, PictureSelectorActivity.class);
                ImageFolder imageFolder = imageFolders.get(position);
                intent.putExtra(IVariable.IMAGE_FOLDER, imageFolder);
                startActivityForResult(intent,GET_PICTURE);
            }
        });

    }

    private void initSelect() {
        if (GlobalValue.mSelectImages==null || GlobalValue.mSelectImages.size()==0){
            ToastUtils.showToast("你尚未选中任何图片");
        }else {
            GlobalValue.mSelectImages=null;
            ToastUtils.showToast("所选照片已清除");
        }
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GET_PICTURE && resultCode==SEND_PICTURE){
            finish();
        }
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_album_selector;
    }

    @Override
    protected void initOptions() {
        initImages();
        initEvent();
    }

    @Override
    protected String initTitle() {
        return "相册详情";
    }
}
