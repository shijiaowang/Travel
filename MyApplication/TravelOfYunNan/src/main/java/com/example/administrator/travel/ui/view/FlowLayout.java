package com.example.administrator.travel.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.administrator.travel.R;
import com.example.administrator.travel.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2016/7/12.
 * 标签页
 */
public class FlowLayout extends ViewGroup {
    private static final int TYPE_ADD = 0;

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = 0;//宽
        int height = 0;//高

        int lineWidth = 0;//行宽
        int lineHeight = 0;//行高

        int childCount = getChildCount();//获取孩子个数
        View child;
        MarginLayoutParams marginLayoutParams;
        int childWidth;
        int childHeight;
        for (int i = 0; i < childCount; i++) {
            child = getChildAt(i);
            marginLayoutParams = (MarginLayoutParams) child.getLayoutParams();//定义的params是这个MarginLayoutParams
            measureChild(child, widthMeasureSpec, heightMeasureSpec);//通知FlowLayout去测量孩子
            //加上margin的宽高
            childWidth = child.getMeasuredWidth() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
            childHeight = child.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
            //如果需要换行，需要加上FlowLayout设置的padding
            if (lineWidth + childWidth > widthSize - getPaddingLeft() - getPaddingRight()) {
                width = Math.max(width, lineWidth);//换行前求出最宽的行为行宽
                height += lineHeight;//叠加总高
                lineWidth = childWidth;//换行重置下一行行宽
                lineHeight = childHeight;//行高，加上margin

            } else {
                lineWidth += childWidth;//叠加行宽
                lineHeight = Math.max(lineHeight, childHeight);
            }
            //最后一行不会超过行宽,所以要另外计算
            if (i == childCount - 1) {
                width = Math.max(width, lineWidth);//换行前求出最宽的行为行宽
                height += lineHeight;//叠加总高
            }

        }

        //重新测量,算上内边距
        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : width + getPaddingRight() + getPaddingLeft(),
                heightMode == MeasureSpec.EXACTLY ? heightSize : height + getPaddingTop() + getPaddingBottom()
        );

    }

    private List<List<View>> mAllViews = new ArrayList<>();//用来放置所有的子VIEW
    private List<Integer> mLineHeight = new ArrayList<>();//行高度

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        mAllViews.clear();
        mLineHeight.clear();//onLayout会执行多次，清理之前的
        //1,测量出每一个行都该放那些孩子
        List<View> lineViews = new ArrayList<>();//用来方法每一行的子View
        int childCount = getChildCount();
        int width = getWidth();//获取测量后的FlowLayout宽度
        MarginLayoutParams marginLayoutParams;
        View child;

        int lineHeight = 0;
        int lineWidth = 0;
        int chileWidth;
        int childHeight;
        for (int j = 0; j < childCount; j++) {
            child = getChildAt(j);
            marginLayoutParams = (MarginLayoutParams) child.getLayoutParams();
            chileWidth = child.getMeasuredWidth() + marginLayoutParams.rightMargin + marginLayoutParams.leftMargin;
            childHeight = child.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
            //需要换行
            if (lineWidth + chileWidth > width - getPaddingLeft() - getPaddingRight()) {
                mAllViews.add(lineViews);//添加上一行
                mLineHeight.add(lineHeight);
                lineWidth = 0;
                lineHeight = 0;
                lineViews = new ArrayList<>();//重新创建集合

            }
            lineViews.add(child);
            lineWidth += chileWidth;
            lineHeight = Math.max(lineHeight, childHeight);

        }
        //最后一行添加数据
        mAllViews.add(lineViews);//添加上一行
        mLineHeight.add(lineHeight);
        int left = getPaddingLeft();//获取左右padding
        int top = getPaddingTop();
        //确定孩子的具体位置
        for (int k = 0; k < mAllViews.size(); k++) {
            lineViews = mAllViews.get(k);
            for (int y = 0; y < lineViews.size(); y++) {
                child = lineViews.get(y);
                marginLayoutParams = (MarginLayoutParams) child.getLayoutParams();
                child.layout(left + marginLayoutParams.leftMargin, top + marginLayoutParams.topMargin, left + marginLayoutParams.leftMargin + child.getMeasuredWidth(), top + marginLayoutParams.topMargin + child.getMeasuredHeight());
                left+=child.getMeasuredWidth()+marginLayoutParams.leftMargin+marginLayoutParams.rightMargin;
            }
            left = getPaddingLeft();
            top += mLineHeight.get(k);
        }

    }
    private OnItemClickListener listener;

    public int changeColorAndBg(int type, String id) {
        for (int i=0;i<getChildCount();i++){
            TextView textView = (TextView) getChildAt(i);
            String tag = (String) textView.getTag();
            if (tag.equals(id)){
                if (type==TYPE_ADD){
                    textView.setTextColor(getContext().getResources().getColor(R.color.otherTitleBg));
                    textView.setBackgroundResource(R.drawable.activity_my_appoint_rl_bg);
                }else {
                    textView.setTextColor(getContext().getResources().getColor(R.color.colorb5b5b5));
                    textView.setBackgroundResource(R.drawable.circle_b5_bg);
                }
                return i;
            }
        }
        return -1;
    }

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
        for (int i=0;i<getChildCount();i++){
            final int position = i;
            getChildAt(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (FlowLayout.this.listener!=null){
                        FlowLayout.this.listener.OnItemClick(position);
                    }
                }
            });
        }
    }


    /**
     * 设置LayoutParams，与距离有关所以使用MarginLayoutParams
     *
     * @param attrs
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
