package com.yunspeak.travel.ui.home;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.aop.CheckNetwork;
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
    @BindView(R.id.iv_bg)
    SimpleDraweeView ivBg;
    @BindView(R.id.tv_home_search_text)
    TextView tvHomeSearchText;
    @BindView(R.id.tv_home_together_text)
    TextView tvHomeTogetherText;
    @BindView(R.id.tv_home_with_me_text)
    TextView tvHomeWithMeText;
    @BindView(R.id.ll_home_search_menu)
    LinearLayout llHomeSearchMenu;
    @BindView(R.id.ll_home_together_menu)
    LinearLayout llHomeTogetherMenu;
    @BindView(R.id.ll_home_with_me)
    LinearLayout llHomeWithMeMenu;
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
        if (indexTextBeen != null) {
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
                    startDetail(activit);
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

    @CheckNetwork
    private void startDetail(ActivityBean activit) {
        ActivateDetailActivity.startShareElement(getContext(), activit.getId(), ivBg, activit.getActivity_img());
    }

    private void initMenuText(List<HomeBean.DataBean.IndexTextBean> indexTextBeen) {
        for (final HomeBean.DataBean.IndexTextBean indexTextBean : indexTextBeen) {
            switch (indexTextBean.getType()) {
                case 1:
                    initTextAndListener(tvHomeSearchText, llHomeSearchMenu, indexTextBean);
                    break;
                case 2:
                    initTextAndListener(tvHomeTogetherText, llHomeTogetherMenu, indexTextBean);
                    break;
                case 3:
                    initTextAndListener(tvHomeWithMeText, llHomeWithMeMenu, indexTextBean);
                    break;
            }

        }
    }

    private void initTextAndListener(TextView textView, final LinearLayout linearLayout, final HomeBean.DataBean.IndexTextBean indexTextBean) {
        textView.setText(indexTextBean.getTitle());
        linearLayout.setTag(indexTextBean);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSwitch(linearLayout);
            }
        });
    }

    /**
     *进入定制约伴等页面选择
     * @param linearLayout
     */
   @CheckNetwork
    private void startSwitch(LinearLayout linearLayout) {
        HomeBean.DataBean.IndexTextBean tag = (HomeBean.DataBean.IndexTextBean) linearLayout.getTag();
        HomeSwitchActivity.start(getContext(), tag.getUrl(), tag.getType());
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
                startSearch();
                break;
            case R.id.tv_focus:
                ToastUtils.showToast("暂时只支持云南");
                break;

        }
    }
    @CheckNetwork
    private void startSearch() {
        startActivity(new Intent(getContext(), HomeSearchActivity.class));
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
                    startBanner(data.get(position));
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
    @CheckNetwork
    private void startBanner(HomeBean.DataBean.BannerBean bannerBean) {
        String title = bannerBean.getTitle();
        String url = bannerBean.getUrl();
        if (StringUtils.isEmpty(title) || StringUtils.isEmpty(url)) {
            return;
        }
        WebViewActivity.start(getContext(), title, url);
    }

    @Override
    protected void setLoading() {

    }
}
