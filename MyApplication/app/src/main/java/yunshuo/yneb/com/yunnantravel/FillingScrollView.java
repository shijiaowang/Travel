package yunshuo.yneb.com.yunnantravel;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;


/**
 * Created by Administrator on 2016/7/11 0011.
 * 惯性监听
 */
public class FillingScrollView extends ScrollView {
    public FillingScrollView(Context context) {
        super(context);
    }

    public FillingScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FillingScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (listener!=null){
            listener.slipping();
        }

    }
   private SlippingListener listener;
    public void setListener(SlippingListener listener){
        this.listener=listener;
    }
    /**
     * 包括惯性滑动在内的滚动监听器
     */
     interface SlippingListener{
        void slipping();
    }
}
