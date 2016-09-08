package com.example.administrator.travel.ui.activity;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.ClickLike;
import com.example.administrator.travel.bean.DeliciousDetail;
import com.example.administrator.travel.bean.FindLastReply;
import com.example.administrator.travel.bean.TravelReplyBean;
import com.example.administrator.travel.event.DeliciousDetailEvent;
import com.example.administrator.travel.event.DetailCommonEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.DeliciousDetailAdapter;
import com.example.administrator.travel.ui.adapter.DiscussCommonAdapter;
import com.example.administrator.travel.ui.view.ToShowAllListView;
import com.example.administrator.travel.ui.view.refreshview.XScrollView;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.ImageOptionsUtil;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.greenrobot.eventbus.Subscribe;
import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/26 0026.
 * 美食详情
 */
public class DeliciousDetailActivity extends LoadingBarBaseActivity implements XScrollView.IXScrollViewListener {
    private ToShowAllListView mLvDeliciousDiscuss;
    private String id;
    private XScrollView mSsvScroll;
    private ImageView mIvBg;
    private TextView mTvDes;
    private DeliciousDetailAdapter deliciousDetailAdapter;
    private boolean isFirst=true;
    private DiscussCommonAdapter discussCommonAdapter;
    private List<TravelReplyBean> travelReply;
    private String haveNextPage;


    @Override
    protected int setContentLayout() {
        return R.layout.activity_delicious_detail;
    }

    @Override
    protected void initEvent() {
        init();
        mLvDeliciousDiscuss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, String> destinationMap = MapUtils.Build().addKey(DeliciousDetailActivity.this).addFId(travelReply.get(position).getF_id()).addUserId().
                        addContent("这只是一个测试评论而已，而已").addPId(travelReply.get(position).getId()).add(IVariable.TYPE, IVariable.TYPE_DELICIOUS).
                        addNextPage(haveNextPage).addCount(travelReply.size()).
                        end();
                XEventUtils.postUseCommonBackJson(IVariable.FIND_REPLY_DISCUSS, destinationMap, TYPE_DISCUSS, new DetailCommonEvent());
            }
        });
    }

    @Override
    protected void onLoad() {

        requestData(TYPE_LOAD);

    }

    private void requestData(int typeLoad) {
        int count =travelReply==null?0:travelReply.size();
        LogUtils.e("当前共有"+count+"条评论");
        Map<String, String> deliciousDetail = MapUtils.Build().addKey(this).addPageSize(10).addCount(count).addFId(id).addUserId().end();
        XEventUtils.getUseCommonBackJson(IVariable.FIND_FOOD_DETAIL, deliciousDetail, typeLoad, new DetailCommonEvent());
    }

    private void init() {
        id = getIntent().getStringExtra(IVariable.T_ID);
        String tName = getIntent().getStringExtra(IVariable.NAME);
        changeTitle(tName);
        mSsvScroll = getxScrollView();
        LinearLayout inflate = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_delicious_detail_content, null);
        if (inflate != null && mSsvScroll != null) {
            mSsvScroll.setPullRefreshEnable(false);
            mSsvScroll.setPullLoadEnable(true);
            mSsvScroll.setIXScrollViewListener(this);
            mSsvScroll.setRefreshTime(getTime());
            mLvDeliciousDiscuss = (ToShowAllListView) inflate.findViewById(R.id.lv_delicious_discuss);
            mIvBg = (ImageView) inflate.findViewById(R.id.iv_bg);
            mTvDes = (TextView) inflate.findViewById(R.id.tv_des);
            mSsvScroll.setView(inflate);
        }
    }

    @Override
    protected Activity initViewData() {
        return this;
    }

    @Override
    protected String setTitleName() {
        return "美食详情";
    }

    @Override
    protected boolean rootIsLinearLayout() {
        return false;
    }

    @Override
    protected boolean canScrollToChangeTitleBgColor() {
        return true;
    }

    @Override
    public float getAlpha() {
        return 0f;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        requestData(TYPE_LOAD);
    }

    @Subscribe
    public void onEvent(DetailCommonEvent event) {
        setIsProgress(false);
        loadEnd(mSsvScroll);
        if (event.isSuccess()) {
            dealData(event);
        } else {
            ToastUtils.showToast(event.getMessage());
        }
    }

    private void dealData(DetailCommonEvent event) {
       LogUtils.e(event.getType()+"这是类型");
        switch (event.getType()){
            case TYPE_LOAD:
                dealDestinationDetailData(event);
                break;
            case TYPE_LIKE_DISCUSS:
                dealClickData(event);
                break;
            case TYPE_DISCUSS:
                dealReplyData(event);
                break;
        }
    }

    private void dealReplyData(DetailCommonEvent event) {
        if (haveNextPage.equals("0")) {
            try {
                FindLastReply findLastReply = GsonUtils.getObject(event.getResult(), FindLastReply.class);
                //数据强转
                travelReply.addAll(findLastReply.getData());
                discussCommonAdapter.notifyData(travelReply);
            } catch (Exception e) {
                LogUtils.e("评论返回数据时出现异常");
                e.printStackTrace();
            }
        }
    }

    private void dealClickData(DetailCommonEvent event) {
        ClickLike clickLike = GsonUtils.getObject(event.getResult(), ClickLike.class);
        if (clickLike == null) return;
        TravelReplyBean travelReplyBean = travelReply.get(event.getClickPosition());
        travelReplyBean.setIs_like("1");
        travelReplyBean.setLike_count(clickLike.getData().getCount_like());
        //// TODO: 2016/8/25 0025 将来设置单独更新一条item
        discussCommonAdapter.notifyData(travelReply);
    }

    private void dealDestinationDetailData(DetailCommonEvent event) {
        DeliciousDetail deliciousDetail = GsonUtils.getObject(event.getResult(), DeliciousDetail.class);
        DeliciousDetail.DataBean.TravelBean travel = deliciousDetail.getData().getTravel();
        if (travel==null)return;
        haveNextPage = deliciousDetail.getData().getHave_next().getNextpage();
        if (isFirst){
            mTvDes.setText(travel.getContent());
            changeTitle(deliciousDetail.getData().getTravel().getTitle());
            String foodUrl = travel.getFood_img().split(",")[0];
            x.image().bind(mIvBg, foodUrl, ImageOptionsUtil.getBySetSize(DensityUtil.getScreenWidth(), DensityUtil.dip2px(228)));
        }
        initDiscuss(deliciousDetail.getData().getTravel_reply());
    }



    private void initDiscuss(List<TravelReplyBean> travelData) {
        if (travelData==null)return;
        if (discussCommonAdapter == null) {
            travelReply = travelData;
            discussCommonAdapter = new DiscussCommonAdapter(this, travelReply,IVariable.TYPE_DELICIOUS);
            mLvDeliciousDiscuss.setAdapter(discussCommonAdapter);
        } else {
            travelReply.addAll(travelData);
            discussCommonAdapter.notifyData(travelReply);
        }
    }

    @Override
    protected boolean isXScrollView() {
        return true;
    }
}
