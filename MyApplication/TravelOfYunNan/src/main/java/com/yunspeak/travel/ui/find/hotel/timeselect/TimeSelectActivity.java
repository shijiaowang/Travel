package com.yunspeak.travel.ui.find.hotel.timeselect;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.TextView;

import com.hyphenate.util.DensityUtil;
import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.baseui.BaseToolBarActivity;
import com.yunspeak.travel.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangyang on 2017/2/23.
 * 酒店预订日期选择
 */

public class TimeSelectActivity extends BaseToolBarActivity {
    @BindView(R.id.tv_time_select_time)
    TextView tvTimeSelectTime;
    @BindView(R.id.tv_time_select_day)
    TextView tvTimeSelectDay;
    @BindView(R.id.rv_time_select_date)
    RecyclerView rvTimeSelectDate;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_time_select;
    }

    @Override
    protected void initOptions() {
        String text="共 2 晚";
        SpannableStringBuilder spannableStringBuilder=new SpannableStringBuilder(text);
        ColorStateList colorStateList=ColorStateList.valueOf(UIUtils.getColor(R.color.otherTitleBg));
        spannableStringBuilder.setSpan(new TextAppearanceSpan(null, Typeface.BOLD, DensityUtil.sp2px(this,24),colorStateList,null)
        ,1,text.length()-1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE
        );
        tvTimeSelectDay.setText(spannableStringBuilder);
    }

    @Override
    protected String initTitle() {
        return "到店/离店时间";
    }


}
