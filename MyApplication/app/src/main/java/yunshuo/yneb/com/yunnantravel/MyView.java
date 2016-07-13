package yunshuo.yneb.com.yunnantravel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/7/12 0012.
 */
public class MyView extends View {

    private Paint mPaint;
    private Rect rect;
    private String s;

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(Color.YELLOW);
        rect = new Rect();
        s = "123";
        mPaint.getTextBounds(s, 0, s.length(), rect);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawText(s,rect.left, rect.top, mPaint);
    }
    public void setChecked(){
        mPaint.setColor(Color.RED);
        invalidate();
    }
}
