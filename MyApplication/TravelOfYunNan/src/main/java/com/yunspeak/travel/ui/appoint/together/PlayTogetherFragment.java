package com.yunspeak.travel.ui.appoint.together;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.CommonClickLikeBean;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.baseui.LoadAndPullBaseFragment;
import com.yunspeak.travel.ui.view.refreshview.XListView;
import com.yunspeak.travel.utils.GsonUtils;
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
