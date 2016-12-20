package com.yunspeak.travel.ui.me.bulltetinboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.BulletinBoardBean;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.utils.FormatDateUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/2 0002.
 */
public class BulletinBoardAdapter extends BaseRecycleViewAdapter<BulletinBoardBean.DataBean> {


    public BulletinBoardAdapter(List<BulletinBoardBean.DataBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }



    @Override
    public BaseRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_activity_bulletin_board, parent, false);
        return new BulletinBoardHolder(inflate);
    }
    class BulletinBoardHolder extends BaseRecycleViewHolder<BulletinBoardBean.DataBean> {
        @BindView(R.id.tv_top) TextView mTvTop;
        @BindView(R.id.tv_title) TextView mTvTitle;
        @BindView(R.id.tv_content) TextView mTvContent;
        @BindView(R.id.tv_time) TextView mTvTime;
        @BindView(R.id.v_dot) View mVDot;
        public BulletinBoardHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void childBindView(int position, BulletinBoardBean.DataBean datas, Context mContext) {

            mTvTop.setVisibility(datas.getIs_top().equals(IVariable.TRUE)?View.VISIBLE:View.GONE);
            mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy-MM-dd HH:mm:ss",datas.getAdd_time()));
            mTvContent.setText(datas.getContent());
            mTvTitle.setText(datas.getTitle());
            mVDot.setVisibility(datas.getIs_top().equals(IVariable.TRUE)?View.GONE:View.VISIBLE);
        }
    }
}
