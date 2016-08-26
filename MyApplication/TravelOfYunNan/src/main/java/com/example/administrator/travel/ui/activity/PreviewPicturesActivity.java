package com.example.administrator.travel.ui.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.event.CreatePostEvent;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.pageranim.ZoomOutPageTransformer;
import com.example.administrator.travel.ui.view.FontsIconTextView;
import com.example.administrator.travel.utils.ToastUtils;


import org.greenrobot.eventbus.EventBus;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2016/8/24.
 * 预览图片
 */
public class PreviewPicturesActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.vp_picture)
    private ViewPager mVpPicture;
    @ViewInject(R.id.tv_back)
    private FontsIconTextView mTvBack;
    @ViewInject(R.id.tv_cancel)
    private FontsIconTextView mTvCancel;
    @ViewInject(R.id.tv_send)
    private TextView mTvSend;
    private int currentPosition = 0;
    private List<String> mTempImage = new ArrayList<>();


    private void changeSelect(int position, TextView mTvCancel) {
        mTvSend.setText("发送("+(GlobalValue.mSelectImages.size()-mTempImage.size())+")");
        if (mTempImage.contains(GlobalValue.mSelectImages.get(position))) {
            mTempImage.remove(GlobalValue.mSelectImages.get(position));
            mTvCancel.setTextColor(getResources().getColor(R.color.otherTitleBg));

        } else {
            mTempImage.add(GlobalValue.mSelectImages.get(position));
            mTvCancel.setTextColor(getResources().getColor(R.color.colorb5b5b5));
        }
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
                    mTvCancel.setTextColor(getResources().getColor(R.color.colorb5b5b5));
                } else {
                    mTvCancel.setTextColor(getResources().getColor(R.color.otherTitleBg));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initData() {
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
            ImageView imageView=new ImageView(PreviewPicturesActivity.this);
            x.image().bind(imageView,GlobalValue.mSelectImages.get(position));
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {

            container.removeView(((ImageView) object));
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
        if (GlobalValue.mSelectImages==null || GlobalValue.mSelectImages.size()==0){
            ToastUtils.showToast("对不起，你尚未选中任何图片");
        }else {
            CreatePostEvent createPostEvent = new CreatePostEvent();
            createPostEvent.setmImages(GlobalValue.mSelectImages);
            createPostEvent.setType(CreatePostActivity.SEND_PICTURE);
            createPostEvent.setIsSuccess(true);
            GlobalValue.mSelectImages=null;
            EventBus.getDefault().post(createPostEvent);
            setResult(AlbumSelectorActivity.SEND_PICTURE);
            finish();
        }
    }

}

