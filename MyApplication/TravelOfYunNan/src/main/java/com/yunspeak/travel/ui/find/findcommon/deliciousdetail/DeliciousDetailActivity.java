package com.yunspeak.travel.ui.find.findcommon.deliciousdetail;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.baseui.BaseBarChangeColorActivity;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.CommonClickLikeBean;
import com.yunspeak.travel.bean.FindLastReply;
import com.yunspeak.travel.event.DetailCommonEvent;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.DeliciousDetailAdapter;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.post.photopreview.CirclePreviewActivity;
import com.yunspeak.travel.ui.find.findcommon.BaseFindDetailActivity;
import com.yunspeak.travel.ui.view.ToShowAllListView;
import com.yunspeak.travel.ui.view.refreshview.XScrollView;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.ImageOptionsUtil;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.xutils.common.util.DensityUtil;
import org.xutils.x;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2016/7/26 0026.
 * 美食详情
 */
public class DeliciousDetailActivity extends BaseFindDetailActivity<DetailCommonEvent,DeliciousDetailBean> {
    private SimpleDraweeView mIvBg;
    private TextView mTvDes;

    @Override
    protected void initEvent() {
        super.initEvent();
        vsContent.setLayoutResource(R.layout.activity_delicious_detail_content);
        vsContent.inflate();
        mIvBg = (SimpleDraweeView) findViewById(R.id.iv_bg);
        mTvDes = (TextView) findViewById(R.id.tv_des);
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
    protected String detailType() {
        return IVariable.TYPE_DELICIOUS;
    }

    @Override
    protected void initHeader(DetailCommonEvent detailCommonEvent) {
        DeliciousDetailBean deliciousDetail = GsonUtils.getObject(detailCommonEvent.getResult(), DeliciousDetailBean.class);
        DeliciousDetailBean.DataBean.TravelBean travel = deliciousDetail.getData().getTravel();
        mTvDes.setText(travel.getContent());
        mTvTitle.setText( travel.getTitle());
        String foodUrl = travel.getFood_img();
        if (!StringUtils.isEmpty(foodUrl)){
            final String[] split = foodUrl.split(",");
            FrescoUtils.displayNormal(mIvBg,split[0]);
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
