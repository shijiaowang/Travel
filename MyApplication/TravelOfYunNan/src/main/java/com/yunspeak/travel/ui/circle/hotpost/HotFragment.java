package com.yunspeak.travel.ui.circle.hotpost;


import com.yunspeak.travel.bean.HotPostBean;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.baseui.LoadAndPullBaseFragment;
import com.yunspeak.travel.ui.circle.CircleFragment;

import java.util.List;

/**
 * Created by wangyang on 2016/7/7 0007.
 * 热帖
 */
public class HotFragment extends LoadAndPullBaseFragment<HotEvent,HotPostBean,HotPostBean.DataBean> {

    @Override
    protected BaseRecycleViewAdapter<HotPostBean.DataBean> initAdapter(List<HotPostBean.DataBean> httpData) {
        return new CircleHotAdapter(httpData,getContext());
    }

    @Override
    protected void initListener() {
      super.initListener();
        changeMargin(10,5);
    }

    @Override
    public void onSuccess(HotEvent hotEvent) {
        super.onSuccess(hotEvent);
        try {
            boolean isEmpty=getListSize(mDatas)==0;
            ((CircleFragment) this.getParentFragment()).showEmpty(isEmpty);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected String initUrl() {
        return IVariable.HOT_POST;
    }


}
