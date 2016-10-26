package com.yunspeak.travel.ui.appoint.withme;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.baseui.LoadAndPullBaseFragment;
import com.yunspeak.travel.ui.view.refreshview.XListView;

import java.util.List;


/**
 * Created by wangyang on 2016/7/21 0021.
 * 带我玩
 */
public class PlayWithMeFragment extends LoadAndPullBaseFragment<AppointWithMeEvent,AppointWithMeBean,AppointWithMeBean.DataBean> implements XListView.IXListViewListener {


    @Override
    protected BaseRecycleViewAdapter<AppointWithMeBean.DataBean> initAdapter(List<AppointWithMeBean.DataBean> httpData) {
        return new AppointWithMeAdapter(httpData,getContext());
    }
    @Override
    protected void initListener() {
       super.initListener();
        changeMargin(5,10);
    }
    @Override
    protected String initUrl() {
        return IVariable.PLAY_WITHE_ME;
    }

}
