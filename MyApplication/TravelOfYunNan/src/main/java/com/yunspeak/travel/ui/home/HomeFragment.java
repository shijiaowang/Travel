package com.yunspeak.travel.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.ActivityBean;
import com.yunspeak.travel.bean.HomeBean;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.SystemBarHelper;
import com.yunspeak.travel.ui.find.active.activedetail.ActivateDetailActivity;
import com.yunspeak.travel.ui.fragment.LoadBaseFragment;
import com.yunspeak.travel.ui.home.homesearch.HomeSearchActivity;
import com.yunspeak.travel.ui.home.welcome.homeswitch.HomeSwitchActivity;
import com.yunspeak.travel.ui.home.welcome.splash.register.WebViewActivity;
import com.yunspeak.travel.ui.view.PagerCursorView;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangyang on 2016/7/6 0006.
 * 主页Fragment
 */
public class HomeFragment extends LoadBaseFragment<HomeEvent> implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    /*@BindView(R.id.gv_chosen) RecyclerView mRvChosen;//精选
    @BindView(R.id.rv_hot_spots) RecyclerView mRvHotSpots;
    @BindView(R.id.lv_travels) RecyclerView mRvTravels;*/
    @BindView(R.id.vp_active)
    ViewPager mVpActive;
    @BindView(R.id.rl_search)
    RelativeLayout mRlSearch;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_people)
    TextView tvPeople;
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.tv_focus)
    TextView mTvFocus;
    @BindView(R.id.rl_active)
    RelativeLayout rlActive;
   /* @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwipe;*/
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pager_cursor)
    PagerCursorView pagerCursorView;
    @BindView(R.id.fl_home_search_appoint)
    FrameLayout flHomeSearchAppoint;
    @BindView(R.id.fl_home_together)
    FrameLayout flHomeTogether;
    @BindView(R.id.sdv_home_with_me_bg)
    SimpleDraweeView sdvHomeWithMeBg;
    @BindView(R.id.fl_home_with_me)
    FrameLayout flHomeWithMe;
    @BindView(R.id.iv_bg)
    SimpleDraweeView ivBg;
    @BindView(R.id.sdv_home_search_bg)
    SimpleDraweeView sdvHomeSearchBg;
    @BindView(R.id.tv_home_search_text)
    TextView tvHomeSearchText;
    @BindView(R.id.sdv_home_together_bg)
    SimpleDraweeView sdvHomeTogetherBg;
    @BindView(R.id.tv_home_together_text)
    TextView tvHomeTogetherText;
    @BindView(R.id.tv_home_with_me_text)
    TextView tvHomeWithMeText;
    private List<HomeBean.DataBean.BannerBean> banner;

    @Override
    protected int initResLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onFail(HomeEvent event) {
        super.onFail(event);
       // mSwipe.setRefreshing(false);

    }

    @Override
    public void onSuccess(HomeEvent event) {
      //  mSwipe.setRefreshing(false);
        HomeBean homeBean = GsonUtils.getObject(event.getResult(), HomeBean.class);
        HomeBean.DataBean data = homeBean.getData();
        List<HomeBean.DataBean.IndexTextBean> indexTextBeen = data.getIndex_text();
        if (indexTextBeen!=null) {
            initMenuText(indexTextBeen);
        }

        /*List<HomeBean.DataBean.ForumBean> forum = data.getForum();
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

        canSmoothInNetScroll(mRvTravels, linearLayoutManager);*/

        final ActivityBean activit = data.getActivit();
        if (activit != null) {
            tvType.setText(activit.getType().equals("2") ? "线下活动" : "线上活动");
            FrescoUtils.displayNormal(ivBg, activit.getActivity_img(), R.drawable.normal_1_3);
            tvPeople.setText(activit.getNow_people() + "人参加");
            tvName.setText(activit.getTitle());
            rlActive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivateDetailActivity.startShareElement(getContext(), activit.getId(), ivBg, activit.getActivity_img());
                }
            });
        } else {
            rlActive.setVisibility(View.GONE);
        }
        if (banner == null) {
            banner = data.getBanner();
            mVpActive.setOffscreenPageLimit(banner.size());
            pagerCursorView.setViewPager(mVpActive, banner.size(), true, this);
            mVpActive.setAdapter(new HomePagerAdapter(banner));
        }


    }

    private void initMenuText(List<HomeBean.DataBean.IndexTextBean> indexTextBeen) {
        for (final HomeBean.DataBean.IndexTextBean indexTextBean:indexTextBeen){
            switch (indexTextBean.getType()){
                case 1:
                    initTextAndListener(sdvHomeSearchBg,tvHomeSearchText,flHomeSearchAppoint,indexTextBean);
                    break;
                case 2:
                    initTextAndListener(sdvHomeTogetherBg,tvHomeTogetherText,flHomeTogether,indexTextBean);
                    break;
                case 3:
                    initTextAndListener(sdvHomeWithMeBg,tvHomeWithMeText,flHomeWithMe,indexTextBean);
                    break;
            }

        }
    }

    private void initTextAndListener(SimpleDraweeView simpleDraweeView, TextView textView, final FrameLayout frameLayout, final HomeBean.DataBean.IndexTextBean indexTextBean) {
        FrescoUtils.displayNormal(simpleDraweeView,indexTextBean.getImg());
        textView.setText(indexTextBean.getTitle());
        frameLayout.setTag(indexTextBean);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeBean.DataBean.IndexTextBean tag = (HomeBean.DataBean.IndexTextBean) frameLayout.getTag();
                HomeSwitchActivity.start(getContext(),tag.getUrl(),tag.getType());
            }
        });
    }

    @Override
    protected void initListener() {
        /*mRvChosen.addItemDecoration(new AlbumSpace(6));
        mRvHotSpots.addItemDecoration(new HotSpotsItemDecoration(12));//设置孩子间距为24px;
        mRvTravels.addItemDecoration(new TopDecoration(6));*/
        SystemBarHelper.setHeightAndPadding(getContext(), toolbar);
       // mSwipe.setOnRefreshListener(this);
       // mSwipe.setColorSchemeResources(R.color.otherTitleBg);
        mRlSearch.setOnClickListener(this);
        mEtSearch.setOnClickListener(this);
        mTvFocus.setOnClickListener(this);
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
            case R.id.tv_focus:
                ToastUtils.showToast("暂时只支持云南");
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
        public Object inflateView(ViewGroup container, final int position) {
            SimpleDraweeView imageView = new SimpleDraweeView(getContext());
            FrescoUtils.displayNormal(imageView, data.get(position).getPath(), 600, 450, R.drawable.normal_2_1);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = data.get(position).getTitle();
                    String url = data.get(position).getUrl();
                    if (StringUtils.isEmpty(title) || StringUtils.isEmpty(url)) {
                        return;
                    }
                    WebViewActivity.start(getContext(), title, url);
                }
            });
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
