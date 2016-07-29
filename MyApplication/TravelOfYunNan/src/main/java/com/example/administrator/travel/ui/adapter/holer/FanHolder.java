package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Fan;
import com.example.administrator.travel.bean.FollowAndFan;
import com.example.administrator.travel.utils.FontsIconUtil;

import org.xutils.x;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/7/18 0018.
 */
public class FanHolder extends BaseHolder<Fan.FanPeople> {

    private TextView mTvName;
    private CircleImageView mIvIcon;

    public FanHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Fan.FanPeople datas, Context mContext) {
        mTvName.setText(datas.getNick_name());
    }



    @Override
    public View initRootView(Context mContext) {
        View inflate = View.inflate(mContext, R.layout.item_activity_follow_and_fan, null);
        TextView mTvCursor = FontsIconUtil.findIconFontsById(R.id.tv_cursor, mContext, inflate);
        mIvIcon = (CircleImageView) inflate.findViewById(R.id.iv_icon);
        mTvName = (TextView) inflate.findViewById(R.id.tv_nike_name);

        return inflate;
    }
}
