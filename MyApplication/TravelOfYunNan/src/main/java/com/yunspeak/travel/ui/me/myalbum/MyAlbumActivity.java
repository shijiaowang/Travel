package com.yunspeak.travel.ui.me.myalbum;

import android.content.Intent;
import android.support.annotation.ColorInt;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewActivity;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.me.myalbum.createalbum.CreateAlbumActivity;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.utils.GsonUtils;

import org.xutils.common.util.DensityUtil;

import java.util.List;

import butterknife.BindColor;
/**
 * Created by wangyang on 2016/7/18 0018.
 * 我的相册
 */
public class MyAlbumActivity extends BaseRecycleViewActivity<MyAlbumEvent,MyAlbumBean,MyAlbumBean.DataBean> implements View.OnClickListener {
    private FontsIconTextView mTvAdd;
    private TextView mTvCreateAlbum;
    private TextView mTvAlbumSum;
    private boolean initAlbumSize=false;
    @BindColor(R.color.meLine) @ColorInt int bgColor;

    @Override
    protected void initEvent() {
        super.initEvent();
        mVsContent.setLayoutResource(R.layout.activity_my_album_header);
        mVsContent.inflate();
        mTvAdd = (FontsIconTextView)findViewById(R.id.tv_add);
        mTvCreateAlbum = (TextView)findViewById(R.id.tv_create_album);
        mTvAlbumSum = (TextView)findViewById(R.id.tv_sum);
        mTvAlbumSum.setText(getString(R.string.total_album,0));
        mRvCommon.setBackgroundColor(bgColor);
        mTvAdd.setOnClickListener(this);
        mTvCreateAlbum.setOnClickListener(this);
        float width = getResources().getDimension(R.dimen.x165);
        int screenWidth = DensityUtil.getScreenWidth();
        int leftMargin= (int) ((screenWidth-width*2)/2);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mRvCommon.getLayoutParams();
        layoutParams.leftMargin=leftMargin;
        mRvCommon.setLayoutParams(layoutParams);
    }

    @Override
    protected void onSuccess(MyAlbumEvent event) {
        super.onSuccess(event);
        if (!initAlbumSize){
            initAlbumSize=true;
            MyAlbumBean myAlbumBean = GsonUtils.getObject(event.getResult(), MyAlbumBean.class);
            int count = myAlbumBean.getData().get(0).getCount();
            mTvAlbumSum.setText(getString(R.string.total_album,count));
        }

    }


    @Override
    protected String initTitle() {
        return "我的相册";
    }
    @Override
    protected String initUrl() {
        return IVariable.ALBUM_LIST;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_create_album:
            case R.id.tv_add:
                startActivity(new Intent(MyAlbumActivity.this, CreateAlbumActivity.class));
                break;
        }

    }
    @Override
    protected boolean isGridLayoutManager() {
        return true;
    }
    @Override
    protected BaseRecycleViewAdapter<MyAlbumBean.DataBean> initAdapter(List<MyAlbumBean.DataBean> mDatas) {
        return new MyAlbumAdapter(mDatas,this);
    }
}
