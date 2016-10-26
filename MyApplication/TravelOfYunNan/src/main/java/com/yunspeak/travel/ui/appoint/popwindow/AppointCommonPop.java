package com.yunspeak.travel.ui.appoint.popwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.SelectCommonBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyang on 2016/9/8 0008.
 */
public class AppointCommonPop implements View.OnClickListener {

    private List<SelectCommonBean> selectCommonBeans=new ArrayList<>();
    private  List<SelectCommonBean> lefts;
    private List<SelectCommonBean> rights;
    private PopupWindow window;
    private int prePosition=0;
    private AppointCommonPopAdapter leftAdapter;
    private AppointCommonPopAdapter rightAdapter;

    private AppointCommonPop(List<SelectCommonBean> lefts, List<SelectCommonBean> rights) {
        this.lefts = lefts;
        this.rights = rights;
    }

    /**
     * 创建
     * @param lefts
     * @param rights
     * @return
     */
    public static AppointCommonPop newInstance(List<SelectCommonBean> lefts,List<SelectCommonBean> rights){
        if (lefts==null || rights==null)return null;
       return new AppointCommonPop(lefts,rights);
    }

    /**
     * 显示在指定控件的下面
     * @param context 上下文
     * @param view  参考的view
     */
    public void showDown(final Context context, View view){
        View viewPopup = LayoutInflater.from(context).inflate(R.layout.item_appoint_pop_select_play_way, null);
        LinearLayout mLlClear = ((LinearLayout) viewPopup.findViewById(R.id.ll_clear));
        TextView mTvCancel = ((TextView) viewPopup.findViewById(R.id.tv_cancel));
        TextView mTvSure = ((TextView) viewPopup.findViewById(R.id.tv_sure));
        final ListView mLvLeft = ((ListView) viewPopup.findViewById(R.id.lv_left));
        ListView mLvRight = ((ListView) viewPopup.findViewById(R.id.lv_right));
        leftAdapter = new AppointCommonPopAdapter(context,lefts,AppointCommonPopAdapter.LEFT);
        mLvLeft.setAdapter(leftAdapter);
        rightAdapter = new AppointCommonPopAdapter(context,rights,AppointCommonPopAdapter.RIGHT);
        mLvRight.setAdapter(rightAdapter);
        mLvLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (prePosition==position)return;
                lefts.get(position).setIsChecked(true);
                lefts.get(prePosition).setIsChecked(false);
                leftAdapter.notifyDataSetChanged();
                prePosition =position;

            }
        });
        mLvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (rights.get(position).isChecked()){
                    rights.get(position).setIsChecked(false);
                    selectCommonBeans.remove(rights.get(position));
                }else {
                    rights.get(position).setIsChecked(true);
                    selectCommonBeans.add(rights.get(position));
                }
                rightAdapter.notifyDataSetChanged();
            }
        });
        mLlClear.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);
        mTvSure.setOnClickListener(this);

        window = new PopupWindow(viewPopup, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha=0.7f;
        ((Activity) context).getWindow().setAttributes(lp);

        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
                lp.alpha = 1.0f;
                ((Activity) context).getWindow().setAttributes(lp);
            }
        });
        window.setOutsideTouchable(true);
        window.setFocusable(true);
        window.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(android.R.color.transparent)));
        window.showAsDropDown(view);
    }
    public void dismiss(){
        if (window!=null && window.isShowing()){
            window.dismiss();
            window=null;
            selectCommonBeans.clear();
        }
    }
    public boolean isShowing(){
        if (window!=null && window.isShowing())return true;
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_clear:
                clearSelect();
                break;
            case R.id.tv_cancel:
                window.dismiss();
                break;
        }
    }

    /**
     * 清除已选择的
     */
    private void clearSelect() {
        for (SelectCommonBean selectCommonBean:selectCommonBeans){
            selectCommonBean.setIsChecked(false);
        }
        rightAdapter.notifyDataSetChanged();
    }
}
