package com.yunspeak.travel.ui.circle.circlenav.circledetail.post.photopreview;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.os.EnvironmentCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.yunspeak.travel.R;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.me.myalbum.editalbum.albumselector.pictureselector.previewpicture.PreviewPicturesActivity;
import com.yunspeak.travel.ui.view.zoomable.ZoomableDraweeView;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.MD5Utils;
import com.yunspeak.travel.utils.ToastUtils;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangyang on 2016/10/25 0025.
 * 圈子图片预览
 */

public class CirclePreviewActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUECT_CODE_SDCARD = 10;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.vp_pager)
    ViewPager vpPager;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_size)
    TextView tvSize;
    @BindView(R.id.tv_save)
    TextView tvSave;
    private List<String> imgListsBeen;
    private int currentPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_circle_preview);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        imgListsBeen = (List<String>) getIntent().getSerializableExtra(IVariable.DATA);
        currentPosition = getIntent().getIntExtra(IVariable.POSITION, 0);
        vpPager.setAdapter(new CirclePhotoAdapter());
        vpPager.setCurrentItem(currentPosition, false);
        ivBack.setOnClickListener(this);
        tvSave.setOnClickListener(this);
        tvSize.setText("(" + (currentPosition + 1) + "/" + imgListsBeen.size() + ")");
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvSize.setText("(" + (position + 1) + "/" + imgListsBeen.size() + ")");
                currentPosition=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public static void start(Context context, List<String> imgLists, int position) {
        Intent intent = new Intent(context, CirclePreviewActivity.class);
        intent.putExtra(IVariable.DATA, (Serializable) imgLists);
        intent.putExtra(IVariable.POSITION, position);
        context.startActivity(intent);
    }






    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_save:
                savePhoto();
                break;
        }
    }

    class CirclePhotoAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imgListsBeen == null ? 0 : imgListsBeen.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final ZoomableDraweeView zoomableDraweeView =new ZoomableDraweeView(CirclePreviewActivity.this);
            zoomableDraweeView.setAllowTouchInterceptionWhileZoomed(false);
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(imgListsBeen.get(position))
                    .build();
            zoomableDraweeView.setController(controller);
            zoomableDraweeView.setTapListener(new GestureDetector.SimpleOnGestureListener(){


                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    LogUtils.e("onSingleTapConfirmed");
                    onBackPressed();
                    return super.onSingleTapConfirmed(e);
                }

                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    return super.onDoubleTap(e);
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    savePhoto();
                }
            });
            container.addView(zoomableDraweeView);
            return zoomableDraweeView;
        }


        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            container.removeView((ZoomableDraweeView) object);
        }
    }

    /**
     * 保存图片
     */
    private void savePhoto() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED  && ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            MPermissions.requestPermissions(this, REQUECT_CODE_SDCARD, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        }else {
            save();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @PermissionGrant(REQUECT_CODE_SDCARD)
    public void requestSdcardSuccess()
    {
         save();
    }

    private void save() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            ToastUtils.showToast("没有挂载SD卡");
            return;
        }
        String url = imgListsBeen.get(currentPosition);
        String filepath = Environment.getExternalStorageDirectory()+"/yunspeaak"+"/circle"+"/";
        File fileParent=new File(filepath);
        if (!fileParent.exists()){
            fileParent.mkdirs();
        }
        String fileName=filepath+MD5Utils.encode(System.currentTimeMillis()+"")+".jpeg";
        Uri loadUri = Uri.parse(url);
        if (loadUri == null) {
            return ;
        }
        ImageRequest imageRequest = ImageRequest.fromUri(loadUri);
        CacheKey cacheKey = DefaultCacheKeyFactory.getInstance()
                .getEncodedCacheKey(imageRequest,null);
       /* if ( ImagePipelineFactory.getInstance()
                 .getMainFileCache().hasKey(cacheKey)){
            FileBinaryResource resource = (FileBinaryResource) Fresco.getImagePipelineFactory().getMainFileCache().getResource(new SimpleCacheKey(imgListsBeen.get(currentPosition)));
            File file =  resource.getFile();

            ToastUtils.showToast("有这张图片"+file.getAbsolutePath());
        }else {*/
            RequestParams params = new RequestParams(url);
            //设置断点续传
            params.setAutoResume(true);
            params.setSaveFilePath(fileName);
            x.http().get(params, new Callback.ProgressCallback<File>() {
                @Override
                public void onWaiting() {

                }

                @Override
                public void onStarted() {

                }

                @Override
                public void onLoading(long total, long current, boolean isDownloading) {

                }


                @Override
                public void onSuccess(File result) {
                    ToastUtils.showToast("保存成功,路径为:"+result.getAbsolutePath());
                }
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    ToastUtils.showToast("保存失败");
                }
                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });

    }
}
