package com.yunspeak.travel.ui.find.findcommon.deliciousdetail;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.event.DetailCommonEvent;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.post.photopreview.CirclePreviewActivity;
import com.yunspeak.travel.ui.find.findcommon.BaseFindDetailActivity;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wangyang on 2016/7/26 0026.
 * 美食详情
 */
public class DeliciousDetailActivity extends BaseFindDetailActivity<DetailCommonEvent,DeliciousDetailBean> {
    private SimpleDraweeView mIvBg;
    private TextView mTvDes;
    private TextView mTvNumber;

    @Override
    protected void initEvent() {
        super.initEvent();
        vsContent.setLayoutResource(R.layout.activity_delicious_detail_content);
        vsContent.inflate();
        LinearLayout mLlRoot = ((LinearLayout) findViewById(R.id.header_root));
        mLlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftWore(v);
            }
        });
        mIvBg = (SimpleDraweeView) findViewById(R.id.iv_bg);
        mTvDes = (TextView) findViewById(R.id.tv_des);
        mTvNumber = (TextView) findViewById(R.id.tv_number);
    }


    @Override
    protected String initUrl() {
        return IVariable.FIND_FOOD_DETAIL;
    }

    public static void start(Context context, String tid, String name){
        Intent intent=new Intent(context,DeliciousDetailActivity.class);
        intent.putExtra(IVariable.T_ID,tid);
        intent.putExtra(IVariable.NAME,name);
        context.startActivity(intent);
    }

    @Override
    protected void onSuccess(DetailCommonEvent detailCommonEvent) {
        switch (detailCommonEvent.getType()){
            case TYPE_CANCEL_COLLECTION:
                ToastUtils.showToast("取消收藏成功");
                isCollect=isFalse;
                item.setTitle("收藏");
                break;
            case TYPE_COLLECTION:
                ToastUtils.showToast("收藏成功");
                isCollect=isTrue;
                item.setTitle("已收藏");
            default:
                super.onSuccess(detailCommonEvent);
                break;
        }

    }

    @Override
    protected String detailType() {
        return IVariable.TYPE_DELICIOUS;
    }

    @Override
    protected void initHeader(DetailCommonEvent detailCommonEvent) {
        DeliciousDetailBean deliciousDetail = GsonUtils.getObject(detailCommonEvent.getResult(), DeliciousDetailBean.class);
        DeliciousDetailBean.DataBean.TravelBean travel = deliciousDetail.getData().getTravel();
        mTvDes.setText(travel.getContent());
        mTvTitle.setText( travel.getTitle());
        isCollect = travel.getIs_collect();
        item.setTitle(isCollect.equals(isFalse)?"收藏":"已收藏");
        String foodUrl = travel.getFood_img();
        if (!StringUtils.isEmpty(foodUrl)){
            final String[] split = foodUrl.split(",");
            mTvNumber.setText("共"+split.length+"张图片");
            FrescoUtils.displayNormal(mIvBg,split[0],640,360,R.drawable.normal_2_1);
            mIvBg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CirclePreviewActivity.start(DeliciousDetailActivity.this, Arrays.asList(split),0);
                }
            });

        }


    }
    @Override
    protected List<TravelReplyBean> childChangeData(DeliciousDetailBean parentBean, DetailCommonEvent detailCommonEvent) {
        haveNextPage = parentBean.getData().getHave_next().getNextpage();
        return parentBean.getData().getTravel_reply();
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        super.childAdd(builder, type);
        builder.addFId(tId);
    }


}
