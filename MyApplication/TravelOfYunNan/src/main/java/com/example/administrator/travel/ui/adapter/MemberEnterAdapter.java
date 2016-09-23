package com.example.administrator.travel.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.HotSpots;
import com.example.administrator.travel.utils.LogUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/7/6 0006.
 * 申请加入
 */
public class MemberEnterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private Context mContext;
    private List<HotSpots> mDatas;

    public MemberEnterAdapter(Context mContext, List<HotSpots> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_activity_member_enter, parent, false);
        return new MemberDetailAdapter(inflate);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MemberDetailAdapter memberDetailAdapter = (MemberDetailAdapter) holder;
        memberDetailAdapter.mTvCatAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeShowWay(memberDetailAdapter.mTvDiscuss,memberDetailAdapter.mTvCatAll);
            }
        });
        memberDetailAdapter.mTvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (memberDetailAdapter.mTvOkText.getVisibility()!=View.VISIBLE) {
                    memberDetailAdapter.mTvOk.setTextColor(Color.parseColor("#ffbf75"));
                    memberDetailAdapter.mTvOkText.setVisibility(View.VISIBLE);
                    memberDetailAdapter.mTvDelete.setVisibility(View.GONE);
                }
            }
        });
        memberDetailAdapter.mTvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               notifyItemRemoved(memberDetailAdapter.getLayoutPosition());
                LogUtils.e(memberDetailAdapter.getLayoutPosition()+"个孩子被移除了");
            }
        });

    }

    @Override
    public int getItemCount() {
        return 10;
    }


    public class MemberDetailAdapter extends RecyclerView.ViewHolder {

        private TextView mTvOk;
        private TextView mTvDelete;
        private TextView mTvCatAll;
        private final TextView mTvDiscuss;
        private final TextView mTvOkText;

        public MemberDetailAdapter(View itemView) {
            super(itemView);
            mTvOk = ((TextView) itemView.findViewById(R.id.tv_ok));
            mTvDelete = ((TextView) itemView.findViewById(R.id.tv_delete));
            mTvCatAll = (TextView) itemView.findViewById(R.id.tv_cat_all);
            mTvDiscuss = (TextView) itemView.findViewById(R.id.tv_discuss);
            mTvOkText = (TextView) itemView.findViewById(R.id.tv_ok_text);

        }
    }
    /**
     * 查看全部设置
     */
    private boolean isShowAllFlag=false;
    private void changeShowWay(TextView show,TextView upOrDown) {
        if (isShowAllFlag){
            show.setEllipsize(TextUtils.TruncateAt.END);
            show.setMaxLines(1);
            upOrDown.setText(mContext.getResources().getString(R.string.down));
        }else {
            show.setEllipsize(null);
            show.setMaxLines(Integer.MAX_VALUE);
            upOrDown.setText(mContext.getResources().getString(R.string.up));
        }
        isShowAllFlag=!isShowAllFlag;
    }


}
