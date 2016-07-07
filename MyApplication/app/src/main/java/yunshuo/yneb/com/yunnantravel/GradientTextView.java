package yunshuo.yneb.com.yunnantravel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/7/7 0007.
 */
public class GradientTextView extends TextView {
    public GradientTextView(Context context) {
        this(context, null);
    }

    public GradientTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        getPaint().setShader(new LinearGradient(0,0,0,getHeight(), Color.WHITE,Color.BLACK, Shader.TileMode.CLAMP));
        super.onDraw(canvas);
    }
}
