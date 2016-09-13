package yunshuo.yneb.com.yunnantravel;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class SingleView extends LinearLayout implements Checkable {

    private TextView mText;
    private CheckBox mCheckBox;
    private TextView mTvPlace;
    private CheckBox mCbBox;

    public SingleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    public SingleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SingleView (Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context){
        // 填充布局
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_fragment_circle_nav_left, this, true);
        mTvPlace = (TextView) v.findViewById(R.id.tv_place);
        mCbBox = (CheckBox) v.findViewById(R.id.cb_check);
    }

    @Override
    public void setChecked( boolean checked) {
        mCbBox.setChecked(checked);
        mTvPlace.setTextColor(checked? Color.RED:Color.BLACK);
    }

    @Override
    public boolean isChecked() {
        return mCbBox.isChecked();
    }

    @Override
    public void toggle() {
        mCbBox.toggle();
    }

    public void setTitle(String text){
        mTvPlace.setText(text);
    }
}