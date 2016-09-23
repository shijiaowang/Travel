package com.example.administrator.travel.ui.baseui.drag;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.travel.utils.LogUtils;

import github.chenupt.dragtoplayout.AttachUtil;


/**
 * @ClassName: RecyclerBaseFragment
 * @Description:
 * @author bohe
 * @date 2016/4/28 13:54
 */
public class RecyclerBaseFragment extends Fragment {
    // 子类需要实现方法返回这个值，根据
    protected RecyclerView recyclerViewParent;

    // 子类必须调用
    protected void setRecyclerView(RecyclerView recyclerView){
        recyclerViewParent = recyclerView;
    }

    // 检查里面的RecyclerView是否滑动到了顶部
    public  boolean getShouldDelegateTouch(){
        // 第一条数据坐标<0说明未滚动到顶部，不托管touch,返回false
        if (recyclerViewParent==null){
            LogUtils.e("我是空的");
        }
        return  AttachUtil.isRecyclerViewAttach(recyclerViewParent);
    }


    public void scrollToFirstItem(){
        // 2016/4/15 所有兄弟Fragment都要统一判断，如果有一个到达顶部shouldDelegateTouch=true，那其他的再次show时都要滑动到顶部
        if(recyclerViewParent != null && recyclerViewParent.getChildCount() > 0){
            if(!getShouldDelegateTouch()){
                recyclerViewParent.getLayoutManager().scrollToPosition(0);
            }
        }

    }

}
