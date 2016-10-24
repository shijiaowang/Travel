package com.yunspeak.travel.ui.baseui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.CommonClickLikeBean;
import com.yunspeak.travel.bean.DeliciousDetail;
import com.yunspeak.travel.bean.FindLastReply;
import com.yunspeak.travel.bean.TravelReplyBean;
import com.yunspeak.travel.event.DetailCommonEvent;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.DeliciousDetailAdapter;
import com.yunspeak.travel.ui.adapter.DiscussCommonAdapter;
import com.yunspeak.travel.ui.view.ToShowAllListView;
import com.yunspeak.travel.ui.view.refreshview.XScrollView;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.ImageOptionsUtil;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.xutils.common.util.DensityUtil;
import org.xutils.x;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2016/7/26 0026.
 * 美食详情
 */
public class DeliciousDetailActivity extends LoadingBarBaseActivity<DetailCommonEvent> implements XScrollView.IXScrollViewListener {
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
    protected void onLoad(int typeRefresh) {
        int count =travelReply==null?0:travelReply.size();
        Map<String, String> deliciousDetail = MapUtils.Build().addKey(this).addPageSize().addCount(count).addFId(id).addUserId().end();
        XEventUtils.getUseCommonBackJson(IVariable.FIND_FOOD_DETAIL, deliciousDetail, TYPE_LOAD, new DetailCommonEvent());

    }
    public static void start(Context context, String tid, String name){
        Intent intent=new Intent(context,DeliciousDetailActivity.class);
        intent.putExtra(IVariable.T_ID,tid);
        intent.putExtra(IVariable.NAME,name);
        context.startActivity(intent);
    }
    private void init() {
        id = getIntent().getStringExtra(IVariable.T_ID);
        String tName = getIntent().getStringExtra(IVariable.NAME);
        changeTitle(tName);
        mSsvScroll = getxScrollView();
        LinearLayout inflate = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_delicious_detail_content, null);
        if (inflate != null && mSsvScroll != null) {
           initXScrollView(false,true);
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
    protected void onSuccess(DetailCommonEvent detailCommonEvent) {
        dealData(detailCommonEvent);
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
        CommonClickLikeBean commonClickLikeBean = GsonUtils.getObject(event.getResult(), CommonClickLikeBean.class);
        if (commonClickLikeBean == null) return;
        TravelReplyBean travelReplyBean = travelReply.get(event.getClickPosition());
        travelReplyBean.setIs_like("1");
        travelReplyBean.setLike_count(commonClickLikeBean.getData().getCount_like());
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
