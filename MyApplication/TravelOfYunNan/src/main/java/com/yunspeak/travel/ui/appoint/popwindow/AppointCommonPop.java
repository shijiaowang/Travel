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
import com.yunspeak.travel.global.ParentPopClick;
import com.yunspeak.travel.bean.CityBean;
import com.yunspeak.travel.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2016/9/8 0008.
 */
public class AppointCommonPop implements View.OnClickListener {

    private  ParentPopClick parentPopClick;
    private List<CityBean> selectCommonBeans=new ArrayList<>();
    private  List<CityBean> lefts;
    private Map<String,List<CityBean>> rights;
    private PopupWindow window;
    private int prePosition=0;
    private AppointCommonPopAdapter leftAdapter;
    private AppointCommonPopAdapter rightAdapter;
    private String checkId;

    private AppointCommonPop(List<CityBean> lefts, Map<String, List<CityBean>> rights, ParentPopClick parentPopClick) {
        this.lefts = lefts;
        this.rights = rights;
        this.parentPopClick = parentPopClick;
    }

    /**
     * 创建
     * @param lefts
     * @param rights
     * @return
     */
    public static AppointCommonPop newInstance(List<CityBean> lefts, Map<String, List<CityBean>> rights, ParentPopClick parentPopClick){
        if (lefts==null || rights==null)return null;
       return new AppointCommonPop(lefts,rights,parentPopClick);
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
                    if (rights.get(checkId).get(position).isChecked()) {
                        rights.get(checkId).get(position).setChecked(false);
                        selectCommonBeans.remove(rights.get(checkId).get(position));
                    } else {
                        rights.get(checkId).get(position).setChecked(true);
                        selectCommonBeans.add(rights.get(checkId).get(position));
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
                break;
            case R.id.tv_cancel:
                window.dismiss();
                break;
            case R.id.tv_sure:
                parentPopClick.onClick(0);
                window.dismiss();
                break;
        }
    }

    /**
     * 清除已选择的
     */
    private void clearSelect() {
        for (CityBean cityBean:selectCommonBeans){
            cityBean.setChecked(false);
        }
        selectCommonBeans.clear();
        rightAdapter.notifyDataSetChanged();
    }
    public String getTyepName(){
        if (selectCommonBeans.size()==0){
            return "";
        }
        StringBuilder stringBuilder=new StringBuilder();

        for (int i=0;i<selectCommonBeans.size();i++){
            if (i==selectCommonBeans.size()-1){
                String id = selectCommonBeans.get(i).getName();
                stringBuilder.append(id);
            }else {
                String id = selectCommonBeans.get(i).getName();
                stringBuilder.append(id).append(",");
            }
        }
        return stringBuilder.toString();
    }
    public String getPlayList(){
        return getList("1");
    }
    public String getTypeList(){
        return getList("2");
    }

    public String getList(String key){
        if (selectCommonBeans.size()==0){
            return "";
        }
        StringBuilder stringBuilder=new StringBuilder();
        List<CityBean> cityBeanList = rights.get(key);
        if (cityBeanList==null || cityBeanList.size()==0)return "";
        for (int i=0;i<selectCommonBeans.size();i++){
            if (cityBeanList.contains(selectCommonBeans.get(i))){
                stringBuilder.append(selectCommonBeans.get(i).getId());
            }
        }
        String string = stringBuilder.toString();
        if (!StringUtils.isEmpty(string) && string.length()>0){
            string=string.substring(string.length()-1,string.length());
        }
        return string;
    }

    public String getTyepString(){
        if (selectCommonBeans.size()==0){
            return "";
        }
        StringBuilder stringBuilder=new StringBuilder();

        for (int i=0;i<selectCommonBeans.size();i++){
            if (i==selectCommonBeans.size()-1){
                String id = selectCommonBeans.get(i).getId();
                stringBuilder.append(id);
            }else {
                String id = selectCommonBeans.get(i).getId();
                stringBuilder.append(id).append(",");
            }
        }
        return stringBuilder.toString();
    }
}
