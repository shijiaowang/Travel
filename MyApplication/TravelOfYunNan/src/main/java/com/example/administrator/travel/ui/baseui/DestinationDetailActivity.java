package com.example.administrator.travel.ui.baseui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.CommonClickLikeBean;
import com.example.administrator.travel.bean.DestinationDetail;
import com.example.administrator.travel.bean.FindLastReply;
import com.example.administrator.travel.bean.TravelReplyBean;
import com.example.administrator.travel.event.DetailCommonEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.DiscussCommonAdapter;
import com.example.administrator.travel.ui.view.FlowLayout;
import com.example.administrator.travel.ui.view.ToShowAllListView;
import com.example.administrator.travel.ui.view.refreshview.XScrollView;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.ImageOptionsUtil;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.xutils.common.util.DensityUtil;
import org.xutils.x;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2016/7/30.
 * 目的地详情
 */
public class DestinationDetailActivity extends LoadingBarBaseActivity<DetailCommonEvent> implements View.OnClickListener {




    private TextView mTvDestinationDes;
    private TextView mTvShow;
    private boolean isShowAllFlag = false;
    private XScrollView mSsvScroll;
    private ToShowAllListView mLvDiscuss;
    private ImageView mIvbg;
    private LinearLayout mLlSearchAppoint;
    private ImageView mIvAddPicture;
    private TextView mTvAdd;
    private String tId;
    private String tName;
    private boolean isFirst = true;
    private FlowLayout mFlowLayout;
    private List<TravelReplyBean> travelReply;
    private DiscussCommonAdapter discussCommonAdapter;
    private String haveNextPage = "-1";
    private int pageSize = 10;//page大小
    private int count;

    @Override
    protected int setContentLayout() {

        return R.layout.activity_add_detail;
    }

   public static void start(Context context,String tid, String name){
       Intent intent=new Intent(context,DestinationDetailActivity.class);
       intent.putExtra(IVariable.T_ID,tid);
       intent.putExtra(IVariable.NAME,name);
       context.startActivity(intent);
  }
    private void initScrollView() {
        tId = getIntent().getStringExtra(IVariable.T_ID);
        tName = getIntent().getStringExtra(IVariable.NAME);
        changeTitle(tName);
        mSsvScroll = getxScrollView();
        LinearLayout inflate = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_destination_detail_content, null);
        if (inflate != null && mSsvScroll != null) {
            initXScrollView(false,true);
            mLvDiscuss = (ToShowAllListView) inflate.findViewById(R.id.content_list);
            mLvDiscuss.setFocusable(false);
            mLvDiscuss.setFocusableInTouchMode(false);
            mTvDestinationDes = ((TextView) inflate.findViewById(R.id.tv_destination_des));
            mTvShow = ((TextView) inflate.findViewById(R.id.tv_show));
            mIvbg = ((ImageView) inflate.findViewById(R.id.iv_bg));
            mLlSearchAppoint = ((LinearLayout) inflate.findViewById(R.id.ll_search_appoint));
            mFlowLayout = ((FlowLayout) inflate.findViewById(R.id.fl_play_title));
            mIvAddPicture = ((ImageView) inflate.findViewById(R.id.iv_add_picture));
            mTvAdd = ((TextView) inflate.findViewById(R.id.tv_add));
            mSsvScroll.setView(inflate);
            mTvShow.setOnClickListener(this);
            mLvDiscuss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Map<String, String> destinationMap = MapUtils.Build().addKey(DestinationDetailActivity.this).addFId(travelReply.get(position).getF_id()).addUserId().
                            addContent("这只是一个测试评论而已，而已").addPId(travelReply.get(position).getId()).add(IVariable.TYPE, IVariable.TYPE_DESTINATION).
                            addNextPage(haveNextPage).addCount(travelReply.size()).
                            end();
                    XEventUtils.postUseCommonBackJson(IVariable.FIND_REPLY_DISCUSS, destinationMap, TYPE_DISCUSS, new DetailCommonEvent());
                }
            });
        }

    }

    @Override
    protected void initEvent() {

        initScrollView();

    }

    @Override
    protected void onLoad(int typeRefresh) {
        if (!StringUtils.isEmpty(tId)) {
            count = travelReply == null ? 0 : travelReply.size();
            Map<String, String> destinationDetailMap = MapUtils.Build().addKey(this).addPageSize(pageSize).addCount(count).addTId(tId).addUserId().end();
            XEventUtils.getUseCommonBackJson(IVariable.FIND_DESTINATION_DETAIL, destinationDetailMap, TYPE_LOAD, new DetailCommonEvent());
        }
    }

    @Override
    protected Activity initViewData() {
        return this;
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
    protected String setTitleName() {
        return "目的地";
    }

    @Override
    protected boolean isXScrollView() {
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_show:
                changeShowWay();
                break;
        }
    }

    /**
     * 查看全部设置
     */
    private void changeShowWay() {
        if (isShowAllFlag) {
            mTvDestinationDes.setEllipsize(TextUtils.TruncateAt.END);
            mTvDestinationDes.setMaxLines(7);
            mTvShow.setText(getResources().getString(R.string.cat_more));
        } else {
            mTvDestinationDes.setEllipsize(null);
            mTvDestinationDes.setMaxLines(Integer.MAX_VALUE);
            mTvShow.setText(getResources().getString(R.string.close_more));
        }
        isShowAllFlag = !isShowAllFlag;
    }



    private void dealData(DetailCommonEvent event) {
        switch (event.getType()) {
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

    /**
     * 评论回复
     *
     * @param event
     */
    private void dealReplyData(DetailCommonEvent event) {
        if (haveNextPage.equals("0")){
            //如果没有更多数据则会返回评论的数据
            addNewData(event);
        }
    }

    /**
     * 添加新返回的评论数据
     * @param event
     */
    private void addNewData(DetailCommonEvent event) {
        try {
            FindLastReply findLastReply = GsonUtils.getObject(event.getResult(), FindLastReply.class);
            //数据强转
            travelReply.addAll(findLastReply.getData());
            discussCommonAdapter.notifyData(travelReply);
        }catch (Exception e){
            LogUtils.e("目的地详情评论返回数据时出现异常");
            e.printStackTrace();
        }
    }

    /**
     * 点击信息
     *
     * @param event
     */
    private void dealClickData(DetailCommonEvent event) {
        CommonClickLikeBean commonClickLikeBean = GsonUtils.getObject(event.getResult(), CommonClickLikeBean.class);
        if (commonClickLikeBean == null) return;
        TravelReplyBean travelReplyBean = travelReply.get(event.getClickPosition());
        travelReplyBean.setIs_like("1");
        travelReplyBean.setLike_count(commonClickLikeBean.getData().getCount_like());
        //// TODO: 2016/8/25 0025 将来设置单独更新一条item
        discussCommonAdapter.notifyData(travelReply);
    }

    /**
     * 目的地详情
     *
     * @param event
     */
    private void dealDestinationDetailData(DetailCommonEvent event) {
        DestinationDetail destinationDetail = GsonUtils.getObject(event.getResult(), DestinationDetail.class);
        if (destinationDetail == null) return;
        haveNextPage = destinationDetail.getData().getHave_next().getNextpage();
        if (isFirst) {
            isFirst = false;
            firstLoad(destinationDetail);
        }
        List<TravelReplyBean> travelData = destinationDetail.getData().getTravel_reply();
        if (travelData == null) return;//数据为空取消
        if (discussCommonAdapter == null) {
            travelReply = travelData;
            discussCommonAdapter = new DiscussCommonAdapter(this, travelReply, IVariable.TYPE_DESTINATION);
            mLvDiscuss.setAdapter(discussCommonAdapter);
        } else {
            travelReply.addAll(travelData);
            discussCommonAdapter.notifyData(travelReply);
        }


    }

    /**
     * 初次加载时才初始化的数据
     *
     * @param destinationDetail
     */
    private void firstLoad(DestinationDetail destinationDetail) {
        DestinationDetail.DataBean.TravelBean travel = destinationDetail.getData().getTravel();
        changeTitle(travel.getTitle());
        mTvDestinationDes.setText(travel.getContent());
        mTvAdd.setText("·  " + travel.getAddress());
        String travel_img = travel.getTravel_img();
        String play_way = travel.getPlay_way();
        String url = "";
        if (!StringUtils.isEmpty(travel_img)) url = travel_img.split(",")[0];
        x.image().bind(mIvbg, url, ImageOptionsUtil.getBySetSize(DensityUtil.getScreenWidth(), DensityUtil.dip2px(200)));
        initPlayWay(play_way);
    }

    /**
     * 目的地标签，游玩方式
     *
     * @param play_way
     */
    private void initPlayWay(String play_way) {
        String[] split = null;
        if (!StringUtils.isEmpty(play_way)) {
            split = play_way.split(",");
        }
        if (split == null) return;
        for (int i = 0; i < split.length; i++) {
            TextView mTvTitle = (TextView) LayoutInflater.from(this).inflate(R.layout.item_fragment_me_title, mFlowLayout, false);
            mTvTitle.setText(split[i]);
            mFlowLayout.addView(mTvTitle);
        }
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        onLoad(TYPE_REFRESH);
    }

    @Override
    protected void onSuccess(DetailCommonEvent detailCommonEvent) {
        dealData(detailCommonEvent);
    }
}
