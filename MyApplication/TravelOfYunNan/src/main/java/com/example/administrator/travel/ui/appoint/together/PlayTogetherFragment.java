package com.example.administrator.travel.ui.appoint.together;
import com.example.administrator.travel.bean.CommonClickLikeBean;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.baseui.LoadAndPullBaseFragment;
import com.example.administrator.travel.ui.view.refreshview.XListView;
import com.example.administrator.travel.utils.GsonUtils;
import java.util.List;



/**
 * Created by wangyang on 2016/7/21 0021.
 * 一起玩
 */
public class PlayTogetherFragment extends LoadAndPullBaseFragment<AppointTogetherEvent, AppointTogetherBean, AppointTogetherBean.DataBean> implements XListView.IXListViewListener {

    private AppointTogetherAdapter appointTogetherAdapter;

    @Override
    protected void initListener() {
        super.initListener();
        setXListViewChildSpace(10);
        changeMarginTop(10);
    }

    @Override
    protected TravelBaseAdapter initAdapter(List<AppointTogetherBean.DataBean> httpData) {
        return  appointTogetherAdapter = new AppointTogetherAdapter(getContext(), httpData);
    }
    @Override
    protected String initUrl() {
        return IVariable.PLAY_TOGETHER;
    }

    @Override
    protected void doOtherSuccessData(AppointTogetherEvent appointTogetherEvent) {
        CommonClickLikeBean object = GsonUtils.getObject(appointTogetherEvent.getResult(), CommonClickLikeBean.class);
        int clickPosition = appointTogetherEvent.getClickPosition();
        getmDatas().get(clickPosition).setCount_like(object.getData().getCount_like());
        getmDatas().get(clickPosition).setIs_like("1");
        appointTogetherAdapter.notifyDataSetChanged();
    }
}
