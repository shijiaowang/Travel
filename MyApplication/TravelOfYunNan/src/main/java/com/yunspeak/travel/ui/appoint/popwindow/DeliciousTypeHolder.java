package com.yunspeak.travel.ui.appoint.popwindow;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.find.findcommon.CityBean;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/12/9 0009.
 */

public class DeliciousTypeHolder extends BaseRecycleViewHolder<CityBean> {
    @BindView(R.id.radioButton)
    TextView radioButton;
    private RadioButton preButton;
    public DeliciousTypeHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void childBindView(int position, CityBean data, Context mContext) {
        radioButton.setText("·"+data.getName());
        if (data.isChecked()){
            radioButton.setBackgroundResource(R.drawable.activity_select_destination_search_bg);
        }else {
            radioButton.setBackgroundColor(Color.TRANSPARENT);
        }

    }
}
