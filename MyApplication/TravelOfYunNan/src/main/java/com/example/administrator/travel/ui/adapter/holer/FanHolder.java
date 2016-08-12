package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Fan;
import com.example.administrator.travel.utils.FontsIconUtil;

import org.xutils.view.annotation.ViewInject;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/7/18 0018.
 */
public class FanHolder extends BaseHolder<Fan.FanPeople> {
    @ViewInject(R.id.tv_nike_name)
    private TextView mTvName;
    @ViewInject(R.id.iv_icon)
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
        View inflate =inflateView(R.layout.item_activity_follow_and_fan);
        TextView mTvCursor = FontsIconUtil.findIconFontsById(R.id.tv_cursor, mContext, inflate);
       /* mIvIcon = (CircleImageView) inflate.findViewById(R.id.iv_icon);
        mTvName = (TextView) inflate.findViewById(R.id.tv_nike_name);*/

        return inflate;
    }
}
