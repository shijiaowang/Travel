package com.yunspeak.travel.ui.find.findcommon.destinationdetail;


import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.flexbox.FlexboxLayout;
import com.hyphenate.easeui.ui.EaseBaiduMapActivity;
import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.appoint.searchappoint.SearchAppointActivity;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.post.photopreview.CirclePreviewActivity;
import com.yunspeak.travel.ui.find.findcommon.BaseFindDetailActivity;
import com.yunspeak.travel.ui.find.findcommon.deliciousdetail.TravelReplyBean;
import com.yunspeak.travel.event.DetailCommonEvent;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wangyang on 2016/7/30.
 * 目的地详情
 */
public class DestinationDetailActivity extends BaseFindDetailActivity<DetailCommonEvent,DestinationDetailBean> implements View.OnClickListener {
    private TextView mTvDestinationDes;
    private TextView mTvShow;
    private boolean isShowAllFlag = false;
    private SimpleDraweeView mIvbg;
    private TextView mTvAdd;
    private FlexboxLayout mFlowLayout;
    private WebView mWvView;
    private LinearLayout mLlSearchAppoint;
    private static final String moblieUrl="mcode=95:0B:B5:DE:E3:08:54:D6:EB:CD:FB:59:8C:15:C6:D1:C6:1E:8F:65;com.yunspeak.travel";
    private boolean click;
    private float startY;
    private int tapSlop;


    public static void start(Context context,String tid, String name){
       Intent intent=new Intent(context,DestinationDetailActivity.class);
       intent.putExtra(IVariable.T_ID,tid);
       intent.putExtra(IVariable.NAME,name);
       context.startActivity(intent);
  }

    @Override
    protected void initEvent() {
        super.initEvent();
        tapSlop = ViewConfiguration.get(this).getScaledDoubleTapSlop();
        vsContent.setLayoutResource(R.layout.activity_destination_detail_content);
        vsContent.inflate();
        mTvDestinationDes = ((TextView) findViewById(R.id.tv_destination_des));
        mTvShow = ((TextView) findViewById(R.id.tv_show));
        mIvbg = ((SimpleDraweeView) findViewById(R.id.iv_bg));
        mLlSearchAppoint = ((LinearLayout) findViewById(R.id.ll_search_appoint));
        mFlowLayout = ((FlexboxLayout)findViewById(R.id.fl_label));
        mWvView = ((WebView) findViewById(R.id.wv_html));
        mTvAdd = ((TextView)findViewById(R.id.tv_add));
        mWvView = (WebView)findViewById(R.id.wv_html);
        mWvView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });
        WebSettings settings = mWvView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(false);
        mTvShow.setOnClickListener(this);
        mLlSearchAppoint.setOnClickListener(this);
    }


    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        super.childAdd(builder, type);
        builder.addTId(tId);
    }

    @Override
    protected String initUrl() {
        return IVariable.FIND_DESTINATION_DETAIL;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_show:
                changeShowWay();
                break;
            case R.id.ll_search_appoint:
                SearchAppointActivity.start(this,tName);
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




    @Override
    protected void initHeader(DetailCommonEvent detailCommonEvent) {
        DestinationDetailBean destinationDetail = GsonUtils.getObject(detailCommonEvent.getResult(), DestinationDetailBean.class);
        final DestinationDetailBean.DataBean.TravelBean travel = destinationDetail.getData().getTravel();
        StringBuilder stringBuilder=new StringBuilder("http://api.map.baidu.com/staticimage/v2?ak=DOwVc765t3sy69IdYQVefrKNEsciH5EO&width=400&height=160");
        stringBuilder.append("&center="+travel.getCity());
        stringBuilder.append("&markers="+travel.getCity()+travel.getTitle()+"|"+travel.getLongitude()+","+travel.getLatitude());
        stringBuilder.append("&zoom=10");
        stringBuilder.append("&markerStyles=l,A,0xff0000");
        stringBuilder.append("&"+moblieUrl);
        mWvView.loadUrl(stringBuilder.toString());
        mWvView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startY = event.getRawY();
                        click=true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float distance=event.getRawY()-startY;
                        if (distance>tapSlop) {
                            click = false;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (click) {
                            Intent intent = new Intent(DestinationDetailActivity.this, EaseBaiduMapActivity.class);
                            intent.putExtra("latitude", Double.parseDouble(travel.getLatitude()));
                            intent.putExtra("longitude", Double.parseDouble(travel.getLongitude()));
                            intent.putExtra("address", travel.getAddress());
                            intent.putExtra("isNew", true);
                            startActivity(intent);
                        }
                        break;
                }

                return click;
            }
        });
        mTvDestinationDes.setText(travel.getContent());
        tName = travel.getTitle();
        mTvTitle.setText(tName);
        mTvAdd.setText("·  " + travel.getAddress());
        String travel_img = travel.getTravel_img();
        String play_way = travel.getPlay_way();
        String url = "";
        if (!StringUtils.isEmpty(travel_img)) {
            final String[] split = travel_img.split(",");
            url = split[0];
            FrescoUtils.displayNormal(mIvbg, url);
            mIvbg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CirclePreviewActivity.start(DestinationDetailActivity.this, Arrays.asList(split),0);
                }
            });

        }
        initPlayWay(play_way);
    }


    @Override
    protected List<TravelReplyBean> childChangeData(DestinationDetailBean parentBean, DetailCommonEvent detailCommonEvent) {
        haveNextPage=parentBean.getData().getHave_next().getNextpage();
        return parentBean.getData().getTravel_reply();
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
        mFlowLayout.removeAllViews();
        for (int i = 0; i < split.length; i++) {
            TextView mTvTitle = (TextView) LayoutInflater.from(this).inflate(R.layout.item_fragment_me_title, mFlowLayout, false);
            mTvTitle.setText(split[i]);
            mFlowLayout.addView(mTvTitle);
        }
    }

    @Override
    protected String detailType() {
        return IVariable.TYPE_DESTINATION;
    }
}
