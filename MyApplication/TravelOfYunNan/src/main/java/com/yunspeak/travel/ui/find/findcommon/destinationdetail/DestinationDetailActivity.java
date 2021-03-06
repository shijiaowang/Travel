package com.yunspeak.travel.ui.find.findcommon.destinationdetail;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.hyphenate.easeui.ui.EaseWebMapActivity;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.DestinationDetailBean;
import com.yunspeak.travel.bean.TravelReplyBean;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.global.ParentPopClick;
import com.yunspeak.travel.ui.appoint.popwindow.AppointDetailMorePop;
import com.yunspeak.travel.ui.appoint.searchappoint.SearchAppointActivity;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.post.photopreview.CirclePreviewActivity;
import com.yunspeak.travel.ui.find.findcommon.BaseFindDetailActivity;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.XEventUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    private static final String moblieUrl="mcode=95:0B:B5:DE:E3:08:54:D6:EB:CD:FB:59:8C:15:C6:D1:C6:1E:8F:65;com.yunspeak.travel";
    private boolean click;
    private float startY;
    private int tapSlop;
    private TextView mTvNumber;
    private String shareUrl;
    private String title;
    private String url;


    public static void start(Context context, String tid, String name){
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
        LinearLayout  mLlRoot = ((LinearLayout) findViewById(R.id.header_root));
        mLlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftWore(v);
            }
        });
        mTvShow = ((TextView) findViewById(R.id.tv_show));
        mTvNumber = ((TextView) findViewById(R.id.tv_number));
        mIvbg = ((SimpleDraweeView) findViewById(R.id.iv_bg));
        url = getIntent().getStringExtra(IVariable.URL);
        if (!StringUtils.isEmpty(url)){
            ViewCompat.setTransitionName(mIvbg,TRANSIT_IMAGE1);
            FrescoUtils.displayNormal(mIvbg, url, 640, 360, R.drawable.normal_2_1);
        }
        LinearLayout mLlSearchAppoint = ((LinearLayout) findViewById(R.id.ll_search_appoint));
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
        settings.setLoadWithOverviewMode(false);
        mWvView.setHorizontalScrollBarEnabled(false);
        mWvView.setVerticalScrollBarEnabled(false);
        mTvShow.setOnClickListener(this);
        mLlSearchAppoint.setOnClickListener(this);

    }

    @Override
    protected String initRightText() {
        return "更多";
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        super.childAdd(builder, type);
        builder.addT_Id(tId);
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
        StringBuilder stringBuilder=new StringBuilder("http://api.map.baidu.com/staticimage/v2?ak=DOwVc765t3sy69IdYQVefrKNEsciH5EO&width=400&height=200");

        String longitude = travel.getLongitude();
        String latitude = travel.getLatitude();
        String city = travel.getCity();
        String province = travel.getProvince();
        String cityDes=province+city;
        if (StringUtils.isEmpty(city) || StringUtils.isEmpty(province)){//都为空默认设置云南
            cityDes="云南";
            if (StringUtils.isEmpty(city)){
                city="云南";
            }
        }
        stringBuilder.append("&center=").append(city);


        final Intent intent = new Intent(DestinationDetailActivity.this, EaseWebMapActivity.class);
        intent.putExtra("title",travel.getTitle());
        if (!(StringUtils.isEmpty(longitude) || StringUtils.isEmpty(latitude))){
            stringBuilder.append("&markers=").append(longitude).append(",").append(latitude);
            intent.putExtra("latitude", Double.parseDouble(travel.getLatitude()));
            intent.putExtra("longitude", Double.parseDouble(travel.getLongitude()));
        }else {//没有经纬度使用默认位置
            stringBuilder.append("&markers=").append(cityDes);
            intent.putExtra("longitude", 102.728219);
            intent.putExtra("latitude", 25.022114);
        }
        stringBuilder.append("&zoom=8");
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
                            intent.putExtra("isNew", true);
                            startActivity(intent);
                        }
                        break;
                }

                return click;
            }
        });
        mTvDestinationDes.setText(travel.getContent());
        shareUrl = travel.getShare_url();
        title = travel.getTitle();
        isCollect = travel.getIs_collect();
        tName = travel.getTitle();
        mTvTitle.setText(tName);
        mTvAdd.setText("·  " + travel.getAddress());
        String travel_img = travel.getTravel_img();
        String play_way = travel.getPlay_way();
        if (!StringUtils.isEmpty(travel_img)) {
            final String[] split = travel_img.split(",");
            mTvNumber.setText("共"+split.length+"张图片");
            if (StringUtils.isEmpty(url)){
                FrescoUtils.displayNormal(mIvbg, split[0], 640, 360, R.drawable.normal_2_1);
            }
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
    protected void otherOptionsItemSelected(MenuItem item) {
        if (StringUtils.isEmpty(isCollect))return;
        String collection = isCollect.equals(isTrue) ? "已收藏" : "收藏";
        //没有举报第三个参数无所谓
        AppointDetailMorePop.showMorePopIsNotCompliant(this,tId,mToolbar, "0", collection, new ParentPopClick() {
            @Override
            public void onClick(int t) {
                String url = isCollect.equals(isTrue) ? IVariable.CANCEL_COMMON_COLLECTION : IVariable.COLLECTION;
                int type=isCollect.equals(isTrue)?TYPE_CANCEL_COLLECTION:TYPE_COLLECTION;
                Map<String, String> collectionMap = MapUtils.Build().addKey().addUserId().addType("2").addId(tId).end();
                XEventUtils.postUseCommonBackJson(url, collectionMap, type, new DetailCommonEvent());
            }
        },"城外旅游目的地分享",title,false,shareUrl,mIvbg);
    }

    @Override
    protected String detailType() {
        return IVariable.TYPE_DESTINATION;
    }
}
