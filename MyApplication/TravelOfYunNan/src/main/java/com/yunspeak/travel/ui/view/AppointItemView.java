package com.yunspeak.travel.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.TypefaceUtis;

/**
 * Created by wangyang on 2016/11/24 0024.
 * 我的约伴展示模版
 */

public class AppointItemView extends TextView {

    private String firstIcon;
    private String fourthIcon;
    private int firstIconColor;
    private int fourthIconColor;
    private int thirdIconColor;
    private int secondIconColor;
    private float iconSize;
    private int firstTextColor;
    private int secondTextColor;
    private int thirdTextColor;
    private int fourthTextColor;

    public AppointItemView(Context context) {
        this(context,null);
    }

    public AppointItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AppointItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs,defStyleAttr);
    }

    /**
     * 初始化
     * @param attrs
     * @param defStyleAttr
     */
    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LoginEditText, defStyleAttr, 0);
        int indexCount = typedArray.getIndexCount();//获取自定义属性个数
        for (int i = 0; i < indexCount; i++) {
            int index = typedArray.getIndex(i);
            switch (index) {
                case R.styleable.AppointItemView_item_first_icon:
                    firstIcon = typedArray.getString(index);
                    firstIcon= setStringIfEmpty(firstIcon,R.string.fragment_play_with_me_air);
                    break;
                case R.styleable.AppointItemView_item_second_icon:
                    String secondIcon = typedArray.getString(index);
                    secondIcon = setStringIfEmpty(secondIcon,R.string.fragment_play_with_me_time);
                    break;
                case R.styleable.AppointItemView_item_third_icon:
                    String thirdIcon = typedArray.getString(index);
                    thirdIcon = setStringIfEmpty(thirdIcon,R.string.fragment_play_with_me_add);
                    break;
                case R.styleable.AppointItemView_item_fourth_icon:
                    fourthIcon = typedArray.getString(index);
                    fourthIcon= setStringIfEmpty(fourthIcon,R.string.fragment_play_with_me_people);
                    break;
                case R.styleable.AppointItemView_item_first_icon_color:
                    firstIconColor = typedArray.getColor(index, getColor(R.color.colorb5b5b5));
                    break;
                case R.styleable.AppointItemView_item_second_icon_color:
                    secondIconColor = typedArray.getColor(index, getColor(R.color.otherTitleBg));
                    break;
                case R.styleable.AppointItemView_item_third_icon_color:
                    thirdIconColor = typedArray.getColor(index, getColor(R.color.colorb5b5b5));
                    break;
                case R.styleable.AppointItemView_item_fourth_icon_color:
                    fourthIconColor = typedArray.getColor(index, getColor(R.color.colorb5b5b5));
                    break;
                case R.styleable.AppointItemView_item_icon_size:
                    iconSize = typedArray.getDimension(index,18);
                    break;
                case R.styleable.AppointItemView_item_first_text_color:
                    firstTextColor = typedArray.getColor(index,getColor(R.color.color323232));
                    break;
                case R.styleable.AppointItemView_item_second_text_color:
                    secondTextColor = typedArray.getColor(index,getColor(R.color.otherTitleBg));
                    break;
                case R.styleable.AppointItemView_item_third_text_color:
                    thirdTextColor = typedArray.getColor(index,getColor(R.color.color646464));
                    break;
                case R.styleable.AppointItemView_item_fourth_text_color:
                    fourthTextColor = typedArray.getColor(index,getColor(R.color.color646464));
                    break;
            }

        }
        typedArray.recycle();
    }
    public void setContent(String firstText,String secondText,String thirdText,String fourthText){
        setText("");
        String firstLine=firstIcon+"\u3000"+firstText;
        SpannableString firstSpan=new SpannableString(firstLine);
        firstSpan.setSpan(new IconTypefaceSpan(),0,1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
       firstSpan.setSpan(new StyleSpan(R.style.firstTextStyle),firstLine.length()-firstText.length(),firstText.length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        setText(firstSpan+"\n");
       /* String secondLine=secondIcon+"\u3000"+secondText+"\n";
        SpannableString secondSpan=new SpannableString(secondLine);
        secondSpan.setSpan(new IconTypefaceSpan(),0,1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        secondSpan.setSpan(new StyleSpan(R.style.secondTextStyle),secondLine.length()-1,secondText.length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        append(secondSpan);
        String thirdLine=thirdIcon+"\u3000"+thirdText+"\n";
        SpannableString thirdSpan=new SpannableString(thirdLine);
        thirdSpan.setSpan(new IconTypefaceSpan(),0,1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        thirdSpan.setSpan(new StyleSpan(R.style.thirdTextStyle),thirdLine.length()-1,thirdText.length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        append(thirdSpan);
        String fourthLine=fourthIcon+"\u3000"+fourthText;
        SpannableString fourthSpan=new SpannableString(fourthLine);
        fourthSpan.setSpan(new IconTypefaceSpan(),0,1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        fourthSpan.setSpan(new StyleSpan(R.style.thirdTextStyle),fourthLine.length()-1,fourthText.length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        append(fourthSpan);*/


    }


    /**
     * 获取颜色
     * @param res
     * @return
     */
    private int getColor(int res) {
        return getContext().getResources().getColor(res);
    }

    /**
     * 如果为空就设置
     * @return
     */
    private String setStringIfEmpty(String temp,int res) {
        return StringUtils.isEmpty(temp)?getResources().getString(res):temp;
    }
    class IconTypefaceSpan extends TypefaceSpan{
        private  Typeface newType;

        private IconTypefaceSpan() {
            super("sans-serif");
            newType = TypefaceUtis.getTypeface(getContext());
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            applyCustomTypeFace(ds, newType);
        }

        @Override
        public void updateMeasureState(TextPaint paint) {
            applyCustomTypeFace(paint, newType);
        }

        private  void applyCustomTypeFace(Paint paint, Typeface tf) {
            int oldStyle;
            Typeface old = paint.getTypeface();
            if (old == null) {
                oldStyle = 0;
            } else {
                oldStyle = old.getStyle();
            }
            int fake = oldStyle & ~tf.getStyle();
            if ((fake & Typeface.BOLD) != 0) {
                paint.setFakeBoldText(true);
            }
            if ((fake & Typeface.ITALIC) != 0) {
                paint.setTextSkewX(-0.25f);
            }
            paint.setTypeface(tf);
        }
    }

}
