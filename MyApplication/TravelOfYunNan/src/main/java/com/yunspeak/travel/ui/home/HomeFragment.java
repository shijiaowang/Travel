package com.yunspeak.travel.ui.home;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.find.active.activedetail.ActivateDetailActivity;
import com.yunspeak.travel.ui.home.homesearch.HomeSearchActivity;
import com.yunspeak.travel.ui.fragment.LoadBaseFragment;
import com.yunspeak.travel.ui.me.myappoint.withmeselect.MyWitheMeDecoration;
import com.yunspeak.travel.ui.me.othercenter.useralbum.AlbumSpace;
import com.yunspeak.travel.ui.view.PagerCursorView;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/6 0006.
 * 主页Fragment
 */
public class HomeFragment extends LoadBaseFragment<HomeEvent> implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private static final int RECYCLE_VIEW_ITEM_SPACE = 24;//子VIEW之间的间距
    public boolean isFirst = true;//避免进入主页已经调用onScrolled，造成未滑动边距就已经为0
    @BindView(R.id.gv_chosen)
    RecyclerView mRvChosen;//精选
    @BindView(R.id.rv_hot_spots)
    RecyclerView mRvHotSpots;
    @BindView(R.id.lv_travels)
    RecyclerView mRvTravels;
    @BindView(R.id.vp_active)
    ViewPager mVpActive;
    @BindView(R.id.rl_search)
    RelativeLayout mRlSearch;
    @BindView(R.id.iv_bg)
    SimpleDraweeView ivBg;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_people)
    TextView tvPeople;
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.rl_active) RelativeLayout rlActive;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwipe;
    @BindView(R.id.pager_cursor)
    PagerCursorView pagerCursorView;
    private List<HomeBean.DataBean.BannerBean> banner;

    @Override
    protected int initResLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onFail(HomeEvent event) {
        super.onFail(event);
        mSwipe.setRefreshing(false);
    }

    @Override
    public void onSuccess(HomeEvent event) {
       mSwipe.setRefreshing(false);
        HomeBean homeBean = GsonUtils.getObject(event.getResult(), HomeBean.class);
        HomeBean.DataBean data = homeBean.getData();
        List<HomeBean.DataBean.ForumBean> forum = data.getForum();
        ChosenAdapter chosenAdapter = new ChosenAdapter(forum, getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        mRvChosen.setAdapter(chosenAdapter);
        mRvChosen.setLayoutManager(gridLayoutManager);
        canSmoothInNetScroll(mRvChosen, gridLayoutManager);

        List<HomeBean.DataBean.DestinationBean> destination = data.getDestination();
        mRvHotSpots.setAdapter(new HotSpotsAdapter(destination, getContext()));
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRvHotSpots.setLayoutManager(manager);
        mRvHotSpots.setItemAnimator(new DefaultItemAnimator());
        canSmoothInNetScroll(mRvHotSpots, manager);
        List<HomeBean.DataBean.FindTravelBean> findTravel = data.getFind_travel();
        TravelsAdapter travelsAdapter = new TravelsAdapter(findTravel, getContext());
        mRvTravels.setAdapter(travelsAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRvTravels.setLayoutManager(linearLayoutManager);

        canSmoothInNetScroll(mRvTravels, linearLayoutManager);

        final HomeBean.DataBean.ActivitBean activit = data.getActivit();
        if (activit != null) {
            tvType.setText(activit.getTitle());
            FrescoUtils.displayNormal(ivBg, activit.getActivity_img());
            tvPeople.setText(activit.getNow_people() + "人参加");
            tvName.setText(activit.getTitle());
            rlActive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivateDetailActivity.start(getContext(), activit.getId());
                }
            });
        } else {
            rlActive.setVisibility(View.GONE);
        }
        if (banner==null) {
            banner = data.getBanner();
            mVpActive.setOffscreenPageLimit(banner.size());
            pagerCursorView.setViewPager(mVpActive, banner.size(), true);
            mVpActive.setAdapter(new HomePagerAdapter(banner));
        }


    }

    @Override
    protected void initListener() {
        mRvChosen.addItemDecoration(new AlbumSpace(6));
        mRvHotSpots.addItemDecoration(new HotSpotsItemDecoration(RECYCLE_VIEW_ITEM_SPACE));//设置孩子间距为24px;
        mRvTravels.addItemDecoration(new MyWitheMeDecoration(6));
        mSwipe.setOnRefreshListener(this);
        mSwipe.setColorSchemeResources(R.color.otherTitleBg);
        mRlSearch.setOnClickListener(this);
        mEtSearch.setOnClickListener(this);
        mRvHotSpots.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isFirst) {
                    isFirst = false;
                } else {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mRvHotSpots.getLayoutParams();
                    layoutParams.leftMargin = 0;
                    mRvHotSpots.setLayoutParams(layoutParams);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }


    @Override
    protected String initUrl() {
        return IVariable.HOME_PAGE;
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_search:
            case R.id.rl_search:
                startActivity(new Intent(getContext(), HomeSearchActivity.class));
                break;

        }
    }

    @Override
    public void onRefresh() {
        onLoad(TYPE_REFRESH);
    }

    class HomePagerAdapter extends PagerCursorView.CursorPagerAdapter<HomeBean.DataBean.BannerBean> {
        public HomePagerAdapter(List<HomeBean.DataBean.BannerBean> data) {
            super(data);
        }

        @Override
        public Object inflateView(ViewGroup container, int position) {
            SimpleDraweeView imageView = new SimpleDraweeView(getContext());
            FrescoUtils.displayNormal(imageView, data.get(position).getPath(),600,450);
            container.addView(imageView);
            return imageView;

        }
        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {

            container.removeView(((SimpleDraweeView) object));
        }
    }

    @Override
    protected void setLoading() {

    }
}
