package com.example.administrator.travel.ui.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Key;
import com.example.administrator.travel.event.AppointEvent;

import com.example.administrator.travel.ui.activity.LinePlanActivity;
import com.example.administrator.travel.ui.adapter.fragment.CommonPagerAdapter;

import com.example.administrator.travel.utils.FastBlur;
import com.example.administrator.travel.utils.FontsIconUtil;
import com.example.administrator.travel.utils.KeyUtils;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.Xutils;


import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.ByteArrayInputStream;
import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;


/**
 * Created by Administrator on 2016/7/20 0020.
 * 约伴
 */
public class AppointFragment extends BaseFragment {


    private ViewPager mVpAppoint;
    private LinearLayout mLlSwitch;
    private boolean isTogether = false;
    private TextView mTvPlayTogether;
    private TextView mTvPlayWithMe;
    private List<BaseFragment> fragments;
    private FloatingActionButton mFabAdd;
    private LinearLayout mLlRoot;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_appoint;
    }

    @Override
    protected void initView() {
        mVpAppoint = (ViewPager) root.findViewById(R.id.vp_appoint);
        mLlSwitch = (LinearLayout) root.findViewById(R.id.ll_switch);
        mTvPlayTogether = (TextView) root.findViewById(R.id.tv_play_together);
        mTvPlayWithMe = (TextView) root.findViewById(R.id.tv_play_with_me);
        mFabAdd = (FloatingActionButton) root.findViewById(R.id.fab_add);
        mLlRoot = (LinearLayout) root.findViewById(R.id.ll_root);
    }

    @Override
    protected void initData() {
        fragments = new ArrayList<>(2);
        fragments.add(new PlayTogetherFragment());
        fragments.add(new PlayWithMeFragment());
        mVpAppoint.setAdapter(new CommonPagerAdapter(getChildFragmentManager(), fragments));

    }

    public Bitmap createViewBitmap(View v) {

        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }
    @Override
    protected void initListener() {
        mFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAppointDialog();//展示约伴框
               /* Map<String, String> createPostMap = Xutils.getCreatePostMap(KeyUtils.getKey(getContext()),"测试标题", "测试内容", "1", "3");
                Map<String, File> fileMap = new HashMap<String, File>();
                Xutils.checkFileAndAdd("/storage/emulated/0/DCIM/100MEDIA/IMAG0003.jpg", fileMap);
                Xutils.postFileAndText(IVariable.CIRCLE_CREATE_POST, createPostMap, fileMap, new Callback.ProgressCallback<String>() {
                    @Override
                    public void onWaiting() {
                        LogUtils.e("onWaiting");
                    }

                    @Override
                    public void onStarted() {
                        LogUtils.e("onStarted");
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isDownloading) {
                        LogUtils.e("onLoading"+current/(float)total);
                    }

                    @Override
                    public void onSuccess(String result) {
                       ToastUtils.showToast(getContext(),result);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        LogUtils.e("onError");
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        LogUtils.e("onCancelled");
                    }

                    @Override
                    public void onFinished() {

                    }
                });*/
            }
        });
        mLlSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTogether = !isTogether;
                if (isTogether) {
                    selectPager(0);
                    mVpAppoint.setCurrentItem(0, false);
                } else {
                    selectPager(1);
                    mVpAppoint.setCurrentItem(1, false);
                }

            }
        });

        mVpAppoint.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectPager(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void showAppointDialog() {
        Bitmap viewBitmap = createViewBitmap(mLlRoot);
        Bitmap bitmap = FastBlur.zoomImage(viewBitmap, 300, 500);//压缩图片
        viewBitmap.recycle();
        Bitmap blurBg = FastBlur.doBlur( bitmap, 10,true);
        showPop(blurBg);
    }

    private void showPop(final Bitmap blurBg) {
        final View dialogView=View.inflate(getContext(), R.layout.popup_window_create_appoiint, null);
        //创建 Dialog
        ImageView mIvBg = (ImageView) dialogView.findViewById(R.id.iv_bg);
       mIvBg.setImageBitmap(blurBg);//设置模糊背景

//		Dialog dialog=new Dialog(上下文,风格style);
        final Dialog dialog=new Dialog(getContext(),R.style.myDialog);
        //layout_width layout_height
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        dialog.setContentView(dialogView, params);
        dialogView.findViewById(R.id.rl_together).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LinePlanActivity.class));
            }
        });
        dialogView.findViewById(R.id.iv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                blurBg.recycle();
            }
        });
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.myDialog);
        dialog.show();

    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    private void selectPager(int position) {
        if (position < 0 || position > fragments.size() - 1) {
            return;
        }
        if (position == 0) {
            mTvPlayTogether.setBackgroundResource(R.drawable.fragment_appoint_cursor);
            mTvPlayTogether.setTextColor(getResources().getColor(R.color.otherTitleBg));
            mTvPlayWithMe.setBackgroundColor(Color.TRANSPARENT);
            mTvPlayWithMe.setTextColor(getResources().getColor(R.color.colorFAFAFA));
        } else {
            mTvPlayWithMe.setBackgroundResource(R.drawable.fragment_appoint_cursor);
            mTvPlayWithMe.setTextColor(getResources().getColor(R.color.otherTitleBg));
            mTvPlayTogether.setBackgroundColor(Color.TRANSPARENT);
            mTvPlayTogether.setTextColor(getResources().getColor(R.color.colorFAFAFA));
        }
    }

    /**
     * 事件 与 fragment通信
     *
     * @param event
     */
    public void onEvent(AppointEvent event) {
        if (event.isSmooth()) {
            mFabAdd.setVisibility(View.INVISIBLE);
        } else {
            mFabAdd.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}
