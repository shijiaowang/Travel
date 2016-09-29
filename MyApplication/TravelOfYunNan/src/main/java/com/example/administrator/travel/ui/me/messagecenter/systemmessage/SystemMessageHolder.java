package com.example.administrator.travel.ui.me.messagecenter.systemmessage;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.view.ShowAllTextView;
import com.example.administrator.travel.utils.FormatDateUtils;

import org.xutils.x;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/26 0026.
 */
public class SystemMessageHolder extends BaseHolder<SystemMessageBean.DataBean> {
    @BindView(R.id.tv_cat_more)
    TextView mTvCatMore;
    @BindView(R.id.tv_message)
    ShowAllTextView mTvMessage;
    @BindView(R.id.iv_icon)
    ImageView mIvIcon;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.tv_time)
    TextView mTvTime;

    public SystemMessageHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(SystemMessageBean.DataBean datas, Context mContext, int position) {
        x.image().bind(mIvIcon,datas.getImg());
        mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd HH:mm",datas.getAdd_time()));
        mTvType.setText(datas.getTitle());
        mTvMessage.setText(datas.getContent());
    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_system_message);
    }
}
