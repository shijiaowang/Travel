package com.yunspeak.travel.ui.find.hotel.hotellist;

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
import com.yunspeak.travel.bean.CityBean;
import com.yunspeak.travel.ui.appoint.popwindow.AppointCommonPopAdapter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2016/9/8 0008.
 * 单选的popwindow
 */
public class SinglePop implements View.OnClickListener {

    private  OnSelectListener parentPopClick;
    private Map<String,CityBean> selectCommonBeans=new HashMap<>();
    private  List<CityBean> lefts;
    private Map<String,List<CityBean>> rights;
    private PopupWindow window;
    private int prePosition=0;
    private AppointCommonPopAdapter leftAdapter;
    private AppointCommonPopAdapter rightAdapter;
    private String checkId;


    public SinglePop(List<CityBean> lefts,Map<String, List<CityBean>> datas, OnSelectListener parentPopClick) {
        if (datas==null)return;
        this.lefts=lefts;
        this.rights = datas;
        this.parentPopClick = parentPopClick;
    }
    public interface OnSelectListener{
        void onChange(Map<String,CityBean> cityBeanMap);
        void clear();
        void onCancel(int size);
    }

    /**
     * 显示在指定控件的下面
     * @param view  参考的view
     */
    public void showDown(final Context context, View view){
        if (window==null) {
            View viewPopup = LayoutInflater.from(context).inflate(R.layout.item_appoint_pop_select_play_way, null);
            LinearLayout mLlClear = ((LinearLayout) viewPopup.findViewById(R.id.ll_clear));
            TextView mTvCancel = ((TextView) viewPopup.findViewById(R.id.tv_cancel));
            TextView mTvSure = ((TextView) viewPopup.findViewById(R.id.tv_sure));
            final ListView mLvLeft = ((ListView) viewPopup.findViewById(R.id.lv_left));
            ListView mLvRight = ((ListView) viewPopup.findViewById(R.id.lv_right));
            leftAdapter = new AppointCommonPopAdapter(context, lefts, AppointCommonPopAdapter.LEFT);
            mLvLeft.setAdapter(leftAdapter);
            checkId = lefts.get(0).getId();
            rightAdapter = new AppointCommonPopAdapter(context, rights.get(checkId), AppointCommonPopAdapter.RIGHT);
            mLvRight.setAdapter(rightAdapter);
            mLvLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (prePosition == position) return;
                    lefts.get(position).setChecked(true);
                    lefts.get(prePosition).setChecked(false);
                    checkId = lefts.get(position).getId();
                    leftAdapter.notifyDataSetChanged();
                    rightAdapter.notifyData(rights.get(checkId));
                    prePosition = position;

                }
            });
            mLvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CityBean preCheckBean = selectCommonBeans.get(checkId);
                    if (preCheckBean==null){
                        rights.get(checkId).get(position).setChecked(true);
                        selectCommonBeans.put(checkId,rights.get(checkId).get(position));
                    }else if (preCheckBean.getId().equals(rights.get(checkId).get(position).getId())){
                        rights.get(checkId).get(position).setChecked(false);
                        selectCommonBeans.remove(checkId);
                    }else if (!preCheckBean.getId().equals(rights.get(checkId).get(position).getId())){
                        preCheckBean.setChecked(false);
                        rights.get(checkId).get(position).setChecked(true);
                        selectCommonBeans.put(checkId,rights.get(checkId).get(position));
                    }
                    rightAdapter.notifyDataSetChanged();
                }
            });
            mLlClear.setOnClickListener(this);
            mTvCancel.setOnClickListener(this);
            mTvSure.setOnClickListener(this);

            window = new PopupWindow(viewPopup, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
            lp.alpha = 0.7f;
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
        }else {
            window.showAsDropDown(view);
        }
    }
    public void dismiss(){
        if (window!=null && window.isShowing()){
            window.dismiss();
            window=null;
            selectCommonBeans.clear();
        }
    }
    public boolean isShowing(){
        return window != null && window.isShowing();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_clear:
                clearSelect();
                parentPopClick.clear();
                break;
            case R.id.tv_cancel:
                parentPopClick.onCancel(selectCommonBeans.size());
                window.dismiss();
                break;
            case R.id.tv_sure:
                parentPopClick.onChange(selectCommonBeans);
                window.dismiss();
                break;
        }
    }

    /**
     * 清除已选择的
     */
    private void clearSelect() {
        Iterator<String> iterator = selectCommonBeans.keySet().iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            selectCommonBeans.get(next).setChecked(false);
        }
        selectCommonBeans.clear();
        rightAdapter.notifyDataSetChanged();
    }

}
