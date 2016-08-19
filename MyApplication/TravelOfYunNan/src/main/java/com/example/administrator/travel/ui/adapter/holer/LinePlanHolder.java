package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Line;
import com.example.administrator.travel.ui.view.DottedLineView;
import com.example.administrator.travel.ui.view.FontsIconTextView;
import com.example.administrator.travel.utils.FontsIconUtil;
import com.example.administrator.travel.utils.LogUtils;

import org.w3c.dom.Text;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class LinePlanHolder extends BaseHolder<Line> {
    @ViewInject(R.id.tv_add)
    public FontsIconTextView mTvAdd;
    private LinearLayout mLlLine;
    @ViewInject(R.id.dlv_line)
    private DottedLineView mDlvLine;
    @ViewInject(R.id.tv_number)
    private TextView mTvNumber;
    @ViewInject(R.id.tv_time)
    private TextView mTvTime;
    @ViewInject(R.id.tv_delete)
    private TextView mTvDelete;


    public LinePlanHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Line datas, Context mContext) {
        mTvNumber.setText(datas.getDayNumber());
        mTvTime.setText(datas.getDayTime());
        List<String> add = datas.getAdd();
        if (add!=null && add.size()>0){
            if (mLlLine.getChildCount()>1){
                mLlLine.removeViews(0,mLlLine.getChildCount()-1);
            }
            for (String s:add) {
                addAdd(s);
            }
        }
    }



    @Override
    public View initRootView(Context mContext) {
        View view = inflateView(R.layout.item_activity_line_plan);

        mLlLine = (LinearLayout) view.findViewById(R.id.ll_destination_line);
        setLineHeight();
        return view;
    }

    public void setLineHeight() {
        mLlLine.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int h = mLlLine.getHeight();
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mDlvLine.getLayoutParams();
                layoutParams.height = h + 25;//弥补空隙
                mDlvLine.setLayoutParams(layoutParams);
                mLlLine.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }
    public void addAdd(String text){
        final View view = inflateView(R.layout.item_activity_line_plan_add_item);
        TextView mTvAdd = (TextView) view.findViewById(R.id.tv_add);
        mTvAdd.setText(text);
        mTvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLlLine.removeView(view);
                setLineHeight();
            }
        });
        int childCount = mLlLine.getChildCount();
        mLlLine.addView(view,childCount-1);
        setLineHeight();
    }
}
