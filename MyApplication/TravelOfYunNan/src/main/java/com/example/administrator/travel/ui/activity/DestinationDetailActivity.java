package com.example.administrator.travel.ui.activity;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.DestinationDetail;
import com.example.administrator.travel.event.DestinationDetailEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.DiscussCommonAdapter;
import com.example.administrator.travel.ui.view.FlowLayout;
import com.example.administrator.travel.ui.view.ToShowAllListView;
import com.example.administrator.travel.ui.view.refreshview.XScrollView;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.ImageOptionsUtil;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.greenrobot.eventbus.Subscribe;
import org.xutils.common.util.DensityUtil;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by android on 2016/7/30.
 * 目的地详情
 */
public class DestinationDetailActivity extends LoadingBarBaseActivity implements View.OnClickListener, XScrollView.IXScrollViewListener {


    private TextView mTvDestinationDes;
    private TextView mTvShow;
    private boolean isShowAllFlag =false;
    private XScrollView mSsvScroll;
    private ToShowAllListView mLvDiscuss;
    private ImageView mIvbg;
    private LinearLayout mLlSearchAppoint;
    private ImageView mIvAddPicture;
    private TextView mTvAdd;
   private int currentPosition=0;
    private String tId;
    private String tName;
    private boolean isFirst=true;
    private FlowLayout mFlowLayout;
    private List<DestinationDetail.DataBean.TravelReplyBean> travelReply=new ArrayList<>();
    private DiscussCommonAdapter discussCommonAdapter;

    @Override
    protected int setContentLayout() {

        return R.layout.activity_add_detail;
    }


    private void initScrollView() {
        tId = getIntent().getStringExtra(IVariable.T_ID);
        tName = getIntent().getStringExtra(IVariable.NAME);
        changeTitle(tName);
        mSsvScroll = getxScrollView();
        LinearLayout inflate = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_destination_detail_content, null);
        if (inflate!=null && mSsvScroll!=null){
            mSsvScroll.setPullRefreshEnable(false);
            mSsvScroll.setPullLoadEnable(true);
            mSsvScroll.setIXScrollViewListener(this);
            mSsvScroll.setRefreshTime(getTime());
            mLvDiscuss = (ToShowAllListView) inflate.findViewById(R.id.content_list);
            mLvDiscuss.setFocusable(false);
            mLvDiscuss.setFocusableInTouchMode(false);
            mTvDestinationDes= ((TextView) inflate.findViewById(R.id.tv_destination_des));
            mTvShow= ((TextView) inflate.findViewById(R.id.tv_show));
            mIvbg = ((ImageView) inflate.findViewById(R.id.iv_bg));
            mLlSearchAppoint = ((LinearLayout) inflate.findViewById(R.id.ll_search_appoint));
            mFlowLayout = ((FlowLayout) inflate.findViewById(R.id.fl_play_title));
            mIvAddPicture = ((ImageView) inflate.findViewById(R.id.iv_add_picture));
            mTvAdd = ((TextView) inflate.findViewById(R.id.tv_add));
            mSsvScroll.setView(inflate);
        }

    }

    @Override
    protected void initEvent() {

        initScrollView();
        mTvShow.setOnClickListener(this);
    }

    @Override
    protected void onLoad() {
        if (!StringUtils.isEmpty(tId)) {
            Map<String, String> destiantionDetailMap = MapUtils.Build().addKey(this).addPageSize("6").addPage(currentPosition + "").addTId(tId).addUserId().end();
            XEventUtils.getUseCommonBackJson(IVariable.FIND_DESTINATION_DETAIL,destiantionDetailMap,0,new DestinationDetailEvent());
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
        switch (v.getId()){
            case R.id.tv_show:
                changeShowWay();
                break;
        }
    }

    /**
     * 查看全部设置
     */
    private void changeShowWay() {
        if (isShowAllFlag){
            mTvDestinationDes.setEllipsize(TextUtils.TruncateAt.END);
            mTvDestinationDes.setMaxLines(7);
            mTvShow.setText(getResources().getString(R.string.cat_more));
        }else {
            mTvDestinationDes.setEllipsize(null);
            mTvDestinationDes.setMaxLines(Integer.MAX_VALUE);
            mTvShow.setText(getResources().getString(R.string.close_more));
        }
        isShowAllFlag=!isShowAllFlag;
    }
    @Subscribe
    public void onEvent(DestinationDetailEvent event){
        setIsProgress(false);
         if (event.isSuccess()){
             dealDestinationDetailData(event);
         }else {
             if (currentPosition>0)currentPosition--;
             ToastUtils.showToast(event.getMessage());
         }
    }

    /**
     * 目的地详情
     * @param event
     */
    private void dealDestinationDetailData(DestinationDetailEvent event) {
        DestinationDetail destinationDetail = GsonUtils.getObject(event.getResult(), DestinationDetail.class);
        if (destinationDetail==null)return;
        if (isFirst){
            isFirst=false;
            firstLoad(destinationDetail);
        }
        if (discussCommonAdapter ==null) {
            travelReply = destinationDetail.getData().getTravel_reply();
            discussCommonAdapter = new DiscussCommonAdapter(this, travelReply);
            mLvDiscuss.setAdapter(discussCommonAdapter);
        }else {
            travelReply=destinationDetail.getData().getTravel_reply();
            discussCommonAdapter.notifyData(travelReply);
        }
      


    }

    /**
     * 初次加载时才初始化的数据
     * @param destinationDetail
     */
    private void firstLoad(DestinationDetail destinationDetail) {
        DestinationDetail.DataBean.TravelBean travel = destinationDetail.getData().getTravel();
        changeTitle(travel.getTitle());
        mTvDestinationDes.setText(travel.getContent());
        mTvAdd.setText("·  "+travel.getAddress());
        String travel_img = travel.getTravel_img();
        String play_way = travel.getPlay_way();
        String url="";
        if (!StringUtils.isEmpty(travel_img))url=travel_img.split(",")[0];
        x.image().bind(mIvbg,url, ImageOptionsUtil.getBySetSize(DensityUtil.getScreenWidth(), DensityUtil.dip2px(200)));
        initPlayWay(play_way);
    }

    /**
     * 目的地标签，游玩方式
     * @param play_way
     */
    private void initPlayWay(String play_way) {
        String[] split=null;
        if (!StringUtils.isEmpty(play_way)) {
            split = play_way.split(",");
        }
        if (split==null)return;
        for (int i = 0; i < split.length; i++) {
            TextView mTvTitle = (TextView) LayoutInflater.from(this).inflate(R.layout.item_fragment_me_title, mFlowLayout, false);
            mTvTitle.setText(split[i]);
            mFlowLayout.addView(mTvTitle);
        }
    }

    /**
     * 测量listview的高度
     * @param mListView
     * @return
     */
    private int measureHeight(ListView mListView){
        // get ListView adapter
        ListAdapter adapter = mListView.getAdapter();
        if (null == adapter) {
            return 0;
        }

        int totalHeight = 0;

        for (int i = 0, len = adapter.getCount(); i < len; i++) {
            View item = adapter.getView(i, null, mListView);
            if (null == item) continue;
            // measure each item width and height
            item.measure(0, 0);
            // calculate all height
            totalHeight += item.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = mListView.getLayoutParams();

        if (null == params) {
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        // calculate ListView height
        params.height = totalHeight + (mListView.getDividerHeight() * (adapter.getCount() - 1));

        mListView.setLayoutParams(params);

        return params.height;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
