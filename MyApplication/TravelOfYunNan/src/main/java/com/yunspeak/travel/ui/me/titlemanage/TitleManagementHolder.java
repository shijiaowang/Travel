package com.yunspeak.travel.ui.me.titlemanage;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/9/7 0007.
 */
public class TitleManagementHolder extends BaseHolder<OfficialLabelBean> {
    @BindView(R.id.tv_use) TextView mTvUse;
    @BindView(R.id.tv_name) TextView mTvName;
    @BindView(R.id.tv_progress) TextView mTvProgress;
    @BindView(R.id.tv_count) TextView mTvCount;
    @BindView(R.id.tv_toggle) TextView mTvToggle;
    @BindView(R.id.tv_status) TextView mTvStatus;
    @BindView(R.id.ll_toggle) LinearLayout mLlToggle;
    @BindView(R.id.tv_des) TextView mTvDes;
    @BindView(R.id.tv_way) TextView mTvWay;
    @BindView(R.id.pb_progress) ProgressBar mPbProgress;
    public TitleManagementHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(OfficialLabelBean datas, Context mContext, int position) {
        String status = datas.getStatus();
        String text="已佩戴";
        String statusString="已佩戴";
        int color=R.color.otherFcae04;
        int bg=R.drawable.rectangle_yellow_bg;
        if (status.equals("0")){
            text="未获得";
            statusString=text;
            color=R.color.color646464;
            bg=R.drawable.rectangle_646464_bg;
        }else if (status.equals("1")){
            text="佩戴";
            statusString="已获得";
            color=R.color.otherTitleBg;
            bg=R.drawable.rectangle_other_title_bg;
        }
        mTvUse.setText(text);
        mTvUse.setTextColor(mContext.getResources().getColor(color));
        mTvUse.setBackgroundResource(bg);
        mTvStatus.setText(statusString);
        mTvStatus.setTextColor(mContext.getResources().getColor(color));
        Integer total = null;
        Integer current = null;
        try {
            total = Integer.valueOf(datas.getGoal());
            current = Integer.valueOf(datas.getScore());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            current=current==null?0:current;
            total=total==null?100:total;
        }
        int parent=current*100/total;
        mPbProgress.setProgress(parent);
        mTvProgress.setText(datas.getScore()==null?0+"":datas.getScore());
        mTvCount.setText("/"+datas.getGoal());
        mTvName.setText(datas.getName());
        mTvDes.setText("称号说明："+datas.getContent());
        mTvWay.setText("获得条件："+datas.getRequirement());

    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_fragment_title_management);
    }
}
