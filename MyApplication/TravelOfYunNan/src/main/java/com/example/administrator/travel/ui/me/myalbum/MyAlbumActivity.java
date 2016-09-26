package com.example.administrator.travel.ui.me.myalbum;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;
import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.LoadingBarBaseActivity;
import com.example.administrator.travel.ui.me.createalbum.CreateAlbumActivity;
import com.example.administrator.travel.ui.view.FontsIconTextView;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ViewInject;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2016/7/18 0018.
 *我的相册
 */
public class MyAlbumActivity extends LoadingBarBaseActivity implements View.OnClickListener {
   @ViewInject(R.id.tv_add)
    private FontsIconTextView mTvAdd;
    @ViewInject(R.id.rv_album)
    private RecyclerView mRvAlbum;
    @ViewInject(R.id.tv_create_album)
    private TextView mTvCreateAlbum;
    @ViewInject(R.id.tv_sum)
    private TextView mTvAlbumSum;




    @Override
    protected int setContentLayout() {
        return R.layout.activity_my_album;
    }


    @Override
    protected void initEvent() {
        mTvCreateAlbum.setOnClickListener(this);
        mTvAdd.setOnClickListener(this);
    }

    @Override
    protected void onLoad(int type) {
        Map<String, String> albumMap = MapUtils.Build().addKey(this).addUserId().end();
        XEventUtils.getUseCommonBackJson(IVariable.ALBUM_LIST,albumMap,0,new MyAlbumEvent());
    }


    @Override
    protected Activity initViewData() {
        return this;
    }

    @Override
    protected String setTitleName() {
        return "我的相册";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_create_album:
            case R.id.tv_add:
                startActivity(new Intent(MyAlbumActivity.this, CreateAlbumActivity.class));
                break;
        }

    }
    @Subscribe
    public void onEvent(MyAlbumEvent event){
      setIsProgress(false);
        if (event.isSuccess()){
            try {
                dealData(event);
            } catch (Exception e) {
                e.printStackTrace();
                setIsError(true);
            }
        }else {
            ToastUtils.showToast(event.getMessage());
            setIsError(true);
        }
    }

    private void dealData(MyAlbumEvent event) {
        MyAlbumBean myAlbumBean = GsonUtils.getObject(event.getResult(), MyAlbumBean.class);
        List<MyAlbumBean.DataBean> data = myAlbumBean.getData();
        mRvAlbum.setAdapter(new MyAlbumAdapter(this, data));
        mRvAlbum.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mRvAlbum.setItemAnimator(new DefaultItemAnimator());
        mTvAlbumSum.setText(getString(R.string.total_album,data.size()));

    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }
}
