package com.yunspeak.travel.ui.appoint.withme;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.appoint.AppointFragment;
import com.yunspeak.travel.ui.appoint.SelectEvent;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.baseui.LoadAndPullBaseFragment;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.CommonClickLikeBean;
import com.yunspeak.travel.ui.view.refreshview.XListView;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;


/**
 * Created by wangyang on 2016/7/21 0021.
 * 带我玩
 */
public class PlayWithMeFragment extends LoadAndPullBaseFragment<AppointWithMeEvent,AppointWithMeBean,AppointWithMeBean.DataBean> implements XListView.IXListViewListener {


    private String label="";
    private String timePosition="1";
    private String orderPosition="1";

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
    public void onSuccess(AppointWithMeEvent appointWithMeEvent) {
        switch (appointWithMeEvent.getType()){
            case TYPE_LIKE:
                if (!getUserVisibleHint())return;
                CommonClickLikeBean object = GsonUtils.getObject(appointWithMeEvent.getResult(), CommonClickLikeBean.class);
                int clickPosition = appointWithMeEvent.getPosition();
                getmDatas().get(clickPosition).setCount_like(object.getData().getCount_like());
                getmDatas().get(clickPosition).setIs_like("1");
                 mAdapter.notifyItemChanged(appointWithMeEvent.getPosition());
               // mAdapter.notifyDataSetChanged();
                break;
            default:
                super.onSuccess(appointWithMeEvent);
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
        timePosition = selectEvent.getTimePosition();
        orderPosition = selectEvent.getOrderPosition();
        onLoad(TYPE_REFRESH);
    }

    @Override
    protected String initUrl() {
        return IVariable.PLAY_WITHE_ME;
    }

}
