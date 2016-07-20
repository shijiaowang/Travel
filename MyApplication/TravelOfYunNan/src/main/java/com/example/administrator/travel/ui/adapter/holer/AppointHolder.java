package com.example.administrator.travel.ui.adapter.holer;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Appoint;
import com.example.administrator.travel.ui.view.FlowLayout;
import com.example.administrator.travel.utils.FontsIconUtil;


/**
 * Created by Administrator on 2016/7/20 0020.
 */
public class AppointHolder extends BaseHolder<Appoint> {

    private TextView mLoveIcon;
    private FlowLayout mFlTitle;
    private String[] titles = new String[]{"号码百事通", "仅限人妖", "郊区游玩", "有责任心", "大神带队"};
    private LayoutInflater inflater;

    public AppointHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Appoint datas, Context mContext) {
        for (int i = 0; i < titles.length; i++) {
            TextView textView = (TextView) inflater.inflate(R.layout.item_fragment_appoint_title, mFlTitle, false);
            textView.setText(titles[i]);
            mFlTitle.addView(textView);
        }
    }

    @Override
    public View initRootView(Context mContext) {
        View inflate = View.inflate(mContext, R.layout.item_fragment_appoint, null);
        inflater = LayoutInflater.from(mContext);
        mLoveIcon = FontsIconUtil.findIconFontsById(R.id.tv_icon_love, mContext, inflate);
        mFlTitle = (FlowLayout) inflate.findViewById(R.id.fl_title);
        TextView mEyeIcon = FontsIconUtil.findIconFontsById(R.id.tv_icon_eye, mContext, inflate);
        return inflate;
    }
}
