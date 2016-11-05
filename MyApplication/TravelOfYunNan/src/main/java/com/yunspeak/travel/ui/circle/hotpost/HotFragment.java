package com.yunspeak.travel.ui.circle.hotpost;


import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.baseui.LoadAndPullBaseFragment;
import com.yunspeak.travel.utils.MapUtils;
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
    protected String initUrl() {
        return IVariable.HOT_POST;
    }


}
