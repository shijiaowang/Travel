package com.example.administrator.travel.ui.me.bulltetinboard;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.utils.FormatDateUtils;

import butterknife.BindView;


/**
 * Created by wangyang on 2016/8/2 0002.
 */
public class BulletinBoardHolder extends BaseHolder<BulletinBoardBean.DataBean> {
    @BindView(R.id.tv_top) TextView mTvTop;
    @BindView(R.id.tv_title) TextView mTvTitle;
    @BindView(R.id.tv_content) TextView mTvContent;
    @BindView(R.id.tv_time) TextView mTvTime;
    @BindView(R.id.v_dot) View mVDot;
    public BulletinBoardHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(BulletinBoardBean.DataBean datas, Context mContext, int position) {
        mTvTop.setVisibility(datas.getIs_top().equals(IVariable.TRUE)?View.VISIBLE:View.GONE);
        mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy-MM-dd HH:mm:ss",datas.getAdd_time()));
        mTvContent.setText(datas.getContent());
        mTvTitle.setText(datas.getTitle());
        mVDot.setVisibility(datas.getIs_top().equals(IVariable.TRUE)?View.GONE:View.VISIBLE);
    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_bulletin_board);
    }
}
