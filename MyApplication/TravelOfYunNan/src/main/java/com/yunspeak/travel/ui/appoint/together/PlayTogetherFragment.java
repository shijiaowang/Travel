package com.yunspeak.travel.ui.appoint.together;
import com.yunspeak.travel.ui.appoint.SelectEvent;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.CommonClickLikeBean;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.LoadAndPullBaseFragment;
import com.yunspeak.travel.ui.view.refreshview.XListView;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;


/**
 * Created by wangyang on 2016/7/21 0021.
 * 一起玩
 */
public class PlayTogetherFragment extends LoadAndPullBaseFragment<AppointTogetherEvent, AppointTogetherBean, AppointTogetherBean.DataBean> implements XListView.IXListViewListener {

    private AppointTogetherAdapter appointTogetherAdapter;

    private String label="";
    private String timePosition="1";
    private String orderPosition="1";

    @Override
    protected void initListener() {
        super.initListener();
        changeMargin(10, 10);
    }

    @Override
    protected BaseRecycleViewAdapter initAdapter(List<AppointTogetherBean.DataBean> httpData) {
        return appointTogetherAdapter = new AppointTogetherAdapter(httpData, getContext());
    }

    @Override
    protected String initUrl() {
        return IVariable.PLAY_TOGETHER;
    }

    @Override
    public void onSuccess(AppointTogetherEvent appointTogetherEvent) {
        switch (appointTogetherEvent.getType()) {
            case TYPE_LIKE:
                if (!getUserVisibleHint())return;
                CommonClickLikeBean object = GsonUtils.getObject(appointTogetherEvent.getResult(), CommonClickLikeBean.class);
                int clickPosition = appointTogetherEvent.getClickPosition();
                getmDatas().get(clickPosition).setCount_like(object.getData().getCount_like());
                getmDatas().get(clickPosition).setIs_like("1");
                appointTogetherAdapter.notifyItemChanged(appointTogetherEvent.getClickPosition());
                break;
            default:
                super.onSuccess(appointTogetherEvent);
                break;
        }

    }
    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        super.childAdd(builder, type);
        builder.addLabel(label).add("time",timePosition).add("sort",orderPosition);
    }

    @Subscribe
    public void onEvent(SelectEvent selectEvent){
        label = selectEvent.getLabel();
         timePosition=selectEvent.getTimePosition();
         orderPosition=selectEvent.getOrderPosition();
        onLoad(TYPE_REFRESH);
    }


}
