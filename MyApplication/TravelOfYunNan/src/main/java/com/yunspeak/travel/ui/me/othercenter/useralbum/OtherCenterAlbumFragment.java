package com.yunspeak.travel.ui.me.othercenter.useralbum;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.fragment.LoadBaseFragment;
import com.yunspeak.travel.ui.me.othercenter.INotify;
import com.yunspeak.travel.ui.me.othercenter.useralbum.albumdetail.CatOtherUserAlbumActivity;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


/**
 * Created by wangyang on 2016/8/23 0023.
 * 相册
 */
public class OtherCenterAlbumFragment extends LoadBaseFragment<OtherAlbumEvent> implements INotify<OtherAlbumBean.DataBean.MoreBean>, OnLoadMoreListener {
    @BindView(R.id.swipe_container) SwipeToLoadLayout mSwipe;
    @BindView(R.id.swipe_target) FrameLayout mSwipeContainer;
    @BindView(R.id.rv_album) RecyclerView recyclerView;
    private String userId;
    private List<OtherAlbumBean.DataBean.MoreBean> mDatas=new ArrayList<>();
    private OtherAlbumAdapter albumAdapter;

    @Override
    protected int initResLayout() {
        return R.layout.activity_other_album;
    }


    @Override
    public void onSuccess(OtherAlbumEvent otherAlbumEvent) {
        mSwipe.setLoadingMore(false);
        OtherAlbumBean otherAlbumBean = GsonUtils.getObject(otherAlbumEvent.getResult(), OtherAlbumBean.class);
        mDatas.addAll(otherAlbumBean.getData().getMore());
        inflateData();

    }

    /**
     * 填充数据
     */
    private void inflateData() {
        if (mDatas==null ||mDatas.size()==0)return;
        if (albumAdapter==null){
            albumAdapter = new OtherAlbumAdapter(mDatas, getContext());
            recyclerView.setAdapter(albumAdapter);
            LinearLayoutManager layout = new GridLayoutManager(getContext(),2);
            recyclerView.setLayoutManager(layout);
            recyclerView.addItemDecoration(new AlbumSpace(6));
            albumAdapter.setItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    CatOtherUserAlbumActivity.start(getContext(),mDatas.get(position).getId());
                }
            });

                /*float width = getResources().getDimension(R.dimen.x162);//动态居中
                int screenWidth = DensityUtil.getScreenWidth();
                int leftMargin= (int) ((screenWidth-width*2-DensityUtil.dip2px(6))/2);
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) recyclerView.getLayoutParams();
                layoutParams.leftMargin=leftMargin;
                recyclerView.setLayoutParams(layoutParams);*/

        }else {
            albumAdapter.notifiyData(mDatas);
        }

    }

    @Override
    protected void onFail(OtherAlbumEvent event) {
        mSwipe.setLoadingMore(false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = getArguments().getString(IVariable.USER_ID);
    }

    public static OtherCenterAlbumFragment newInstance(String userId) {
        OtherCenterAlbumFragment userDynamicFragment = new OtherCenterAlbumFragment();
        Bundle bundle = new Bundle();
        bundle.putString(IVariable.USER_ID, userId);
        userDynamicFragment.setArguments(bundle);
        return userDynamicFragment;
    }


    @Override
    protected void initListener() {
        View footView = LayoutInflater.from(getContext()).inflate(R.layout.layout_google_footer, mSwipe, false);
        mSwipe.setSwipeStyle(SwipeToLoadLayout.STYLE.BLEW);
        mSwipe.setLoadMoreFooterView(footView);
        mSwipe.setOnLoadMoreListener(this);
        mSwipe.setLoadMoreEnabled(false);

    }

    @Override
    protected void onLoad(int type) {
        int count=getListSize(mDatas);
        Map<String, String> albumMap = MapUtils.Build().addKey().addMyId().add(IVariable.USER_ID, userId).addType("2").addPageSize().addCount(count).end();
        XEventUtils.getUseCommonBackJson(IVariable.OTHER_USER_INFO,albumMap,0,new OtherAlbumEvent());
    }

    @Override
    protected String initUrl() {
        return IVariable.OTHER_USER_INFO;
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {

    }


    @Override
    public void onLoadMore() {
       onLoad(TYPE_LOAD);
    }

    @Subscribe
    public void onEvent(AppBarStateEvent appBarStateEvent) {
        mSwipe.setLoadMoreEnabled(appBarStateEvent.isClose());
    }

    @Override
    public void notifys(List<OtherAlbumBean.DataBean.MoreBean> t) {
        mDatas=t;
        inflateData();
    }

    @Override
    public void notify(OtherAlbumBean.DataBean.MoreBean moreBean) {

    }
}


