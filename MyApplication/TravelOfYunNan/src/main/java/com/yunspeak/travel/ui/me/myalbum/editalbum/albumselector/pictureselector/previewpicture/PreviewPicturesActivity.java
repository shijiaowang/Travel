package com.yunspeak.travel.ui.me.myalbum.editalbum.albumselector.pictureselector.previewpicture;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.yunspeak.travel.R;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.pageranim.ZoomOutPageTransformer;
import com.yunspeak.travel.ui.baseui.BaseActivity;
import com.yunspeak.travel.ui.me.myalbum.editalbum.albumselector.AlbumSelectorActivity;
import com.yunspeak.travel.ui.me.myalbum.editalbum.albumselector.UpPhotoEvent;
import com.yunspeak.travel.ui.me.myalbum.editalbum.albumselector.pictureselector.PictureSelectorEvent;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.ui.view.zoomable.DoubleTapGestureListener;
import com.yunspeak.travel.ui.view.zoomable.ZoomableDraweeView;
import com.yunspeak.travel.utils.ToastUtils;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by wangyang on 2016/8/24.
 * 预览图片
 */
public class PreviewPicturesActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.vp_picture) ViewPager mVpPicture;
    @BindView(R.id.tv_back) FontsIconTextView mTvBack;
    @BindView(R.id.tv_cancel) FontsIconTextView mTvCancel;
    @BindView(R.id.tv_send) TextView mTvSend;
    private int currentPosition = 0;
    private List<String> mTempImage = new ArrayList<>();
    private boolean sendFlag=false;


    private void changeSelect(int position, TextView mTvCancel) {

        if (mTempImage.contains(GlobalValue.mSelectImages.get(position))) {
            mTempImage.remove(GlobalValue.mSelectImages.get(position));
            mTvCancel.setTextColor(getResources().getColor(R.color.colorb5b5b5));

        } else {
            mTempImage.add(GlobalValue.mSelectImages.get(position));
            mTvCancel.setTextColor(getResources().getColor(R.color.otherTitleBg));
        }
        mTvSend.setText("发送("+mTempImage.size()+")");
    }


    @Override
    protected int initLayoutRes() {
        return R.layout.actiivity_preview_pictures;
    }

    @Override
    protected void initView() {
        mTvSend.setText("发送("+(GlobalValue.mSelectImages.size()+")"));
        mTvCancel.setTextColor(getResources().getColor(R.color.otherTitleBg));
    }

    @Override
    protected void initListener() {
        mTvBack.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);
        mTvSend.setOnClickListener(this);
        mVpPicture.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                if (mTempImage.contains(GlobalValue.mSelectImages.get(position))) {
                    mTvCancel.setTextColor(getResources().getColor(R.color.otherTitleBg));
                } else {
                    mTvCancel.setTextColor(getResources().getColor(R.color.colorb5b5b5));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initData() {
        mTempImage.clear();
        mTempImage.addAll(GlobalValue.mSelectImages);
        mVpPicture.setPageTransformer(true, new ZoomOutPageTransformer());
        mVpPicture.setAdapter(new PictureAdapter());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_cancel:
                changeSelect(currentPosition, mTvCancel);
                break;
            case R.id.tv_send:
                sendPicture();
                break;
        }
    }


    class PictureAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ZoomableDraweeView zoomableDraweeView =new ZoomableDraweeView(PreviewPicturesActivity.this);
            zoomableDraweeView.setAllowTouchInterceptionWhileZoomed(true);
            zoomableDraweeView.setTapListener(new DoubleTapGestureListener(zoomableDraweeView));
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(GlobalValue.mSelectImages.get(position))
                    .build();
            zoomableDraweeView.setController(controller);
            container.addView(zoomableDraweeView);
            return zoomableDraweeView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {

            container.removeView(((ZoomableDraweeView) object));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return GlobalValue.mSelectImages.size();
        }
    }

    /**
     * 发送图片
     */
    private void sendPicture() {
        if (mTempImage==null || mTempImage.size()==0){
            ToastUtils.showToast("对不起，你尚未选中任何图片");
        }else {
            sendFlag = true;
            UpPhotoEvent upPhotoEvent=new UpPhotoEvent();
            upPhotoEvent.setList(mTempImage);
            EventBus.getDefault().post(upPhotoEvent);
            setResult(AlbumSelectorActivity.SEND_PICTURE);
            GlobalValue.mSelectImages = null;
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!sendFlag && mTempImage!=null){//如果没发送过重新赋值
            GlobalValue.mSelectImages.clear();
            GlobalValue.mSelectImages.addAll(mTempImage);
           EventBus.getDefault().post(new PictureSelectorEvent());
        }
    }
}

