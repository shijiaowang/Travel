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
import com.yunspeak.travel.ui.view.dateview.DateRecycleView;
import com.yunspeak.travel.utils.UIUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
    DateRecycleView rvTimeSelectDate;
    private SimpleDateFormat dateInstance;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_time_select;
    }

    @Override
    protected void initOptions() {
        setHowLong(0);
        dateInstance = new SimpleDateFormat("MM月dd日", Locale.CHINESE);
        formatDate(null,null);
        rvTimeSelectDate.setSelectListener(new DateRecycleView.SelectListener() {
            @Override
            public void onCallBack(Calendar in, Calendar out) {
                formatDate(in,out);
            }
        });
    }

    private void formatDate(Calendar start,Calendar end) {
        String startText;
        String endText;
        if (start==null){
            startText=endText=dateInstance.format(new Date());
        }else if (end==null){
            startText=endText=dateInstance.format(start.getTime());
        }else {
            startText=dateInstance.format(start.getTime());
            endText=dateInstance.format(end.getTime());
        }
        if (startText.startsWith("0")){
            startText=startText.substring(1,startText.length());
        }
        if (endText.startsWith("0")){
            endText=endText.substring(1,endText.length());
        }
        tvTimeSelectTime.setText(startText+"\n"+endText);
    }

    private void setHowLong(int day) {
        String text="共 "+day+" 晚";
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
