package com.yunspeak.travel.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by wangyang on 2017/2/9.
 */

public class SlideCursorView extends View {
    private static char[] words=new char[28];
    static {
        words[0]='*';
        words[27]='#';
        char start='A';
        for (int i=1;i<=26;i++){
            words[i]=start++;
        }
    }

    private int width;
    private int wordHeight;//一个单词的高度
    private Paint wordPaint;
    private Rect textMesure;
    private float preY=-1f;

    public SlideCursorView(Context context) {
        this(context,null);
    }

    public SlideCursorView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlideCursorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        wordPaint = new Paint();
        wordPaint.setAntiAlias(true);
        wordPaint.setTextSize(28);
        wordPaint.setColor(Color.DKGRAY);
        textMesure = new Rect();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i=0;i<words.length;i++){
            String currentWord = String.valueOf(words[i]);
            wordPaint.getTextBounds(currentWord,0,currentWord.length(),textMesure);
            int textWidth = textMesure.width();
            int textHeight = textMesure.height();
            float x=(width-textWidth)/2f;
            float y=(i+1)*wordHeight-(wordHeight-textHeight)/2f;
            canvas.drawText(currentWord,x,y,wordPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                setBackgroundColor(Color.parseColor("#21969696"));
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                if ((wordSelectChangeListener!=null && canUseListener(y)) || (wordSelectChangeListener!=null && preY==-1f)){
                    if (floatToInt(y)<words.length && floatToInt(y)>0) {
                        wordSelectChangeListener.wordChange(words[floatToInt(y)]);
                }
                }
                preY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                setBackgroundColor(Color.parseColor("#00000000"));
                if (wordSelectChangeListener!=null){
                    wordSelectChangeListener.wordCancel();
                }
                break;
        }
        return true;
    }

    /**
     * 是否触发listener
     * @param y
     */
    private boolean canUseListener(float y) {
        if (floatToInt(preY) == floatToInt(y)) {
            return false;
        }
        return true;
    }

    private int floatToInt(float y) {
        return (int) (y/wordHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        wordHeight = h/words.length;
    }
    private WordSelectChangeListener wordSelectChangeListener;



    public interface WordSelectChangeListener{
        void wordChange(char word);
        void wordCancel();
    }

    public void setWordSelectChangeListener(WordSelectChangeListener wordSelectChangeListener) {
        this.wordSelectChangeListener = wordSelectChangeListener;
    }
}
