package com.example.administrator.travel.ui.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.administrator.travel.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class AppointCommonPop{


    private  List<String> lefts;
    private List<String> rights;
    private PopupWindow window;

    private AppointCommonPop(List<String> lefts, List<String> rights) {
        this.lefts = lefts;
        this.rights = rights;
    }

    /**
     * 创建
     * @param lefts
     * @param rights
     * @return
     */
    public static AppointCommonPop newInstance(List<String> lefts,List<String> rights){
        //if (lefts==null || rights==null)return null;
       return new AppointCommonPop(lefts,rights);
    }

    /**
     * 显示在指定控件的下面
     * @param context 上下文
     * @param view  参考的view
     */
    public void showDown(Context context,View view){
        View viewPopup = LayoutInflater.from(context).inflate(R.layout.item_appoint_pop_select_play_way, null);
        window = new PopupWindow(viewPopup, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setOutsideTouchable(true);
        window.setFocusable(true);
        window.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(android.R.color.transparent)));
        window.showAsDropDown(view);
    }
    public void dismiss(){
        if (window!=null && window.isShowing()){
            window.dismiss();
            window=null;
        }
    }
    public boolean isShowing(){
        if (window!=null && window.isShowing())return true;
        return false;
    }
}
