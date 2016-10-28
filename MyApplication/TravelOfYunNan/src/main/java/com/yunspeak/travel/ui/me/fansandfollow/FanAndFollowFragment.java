package com.yunspeak.travel.ui.me.fansandfollow;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.aite.AiteBean;
import com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.aite.Follow;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.baseui.LoadAndPullBaseFragment;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.XEventUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2016/7/18 0018.
 */
public class FanAndFollowFragment extends LoadAndPullBaseFragment<FanAndFollowEvent,AiteBean,Follow> {
    private String type = "0";
    private int REQ_CODE = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle arguments = getArguments();
            type = arguments.getString(IVariable.TYPE);
            REQ_CODE = type.equals("1") ? IVariable.TYPE_GET_FOLLOW : IVariable.TYPE_GET_FAN;
        }
    }

    @Override
    protected int initResLayout() {
        return R.layout.fragment_follow_and_fan;
    }



    @Override
    public void onSuccess(FanAndFollowEvent fanAndFollowEvent) {
        if (fanAndFollowEvent.getChildType() != REQ_CODE) {
            return;
        }
        super.onSuccess(fanAndFollowEvent);
    }

    @Override
    protected BaseRecycleViewAdapter<Follow> initAdapter(List<Follow> httpData) {
        return new FanAdapter(httpData,getContext());
    }

    /**
     * @param type 1关注人，2粉丝
     * @return
     */
    public static FanAndFollowFragment newInstance(String type) {
        FanAndFollowFragment fanAndFollowFragment = new FanAndFollowFragment();
        Bundle bundle = new Bundle();
        bundle.putString(IVariable.TYPE, type);
        fanAndFollowFragment.setArguments(bundle);
        return fanAndFollowFragment;
    }
    @Override
    protected void onLoad(int type) {
        if (this.type.equals("0")) return;
        int count=type==TYPE_REFRESH?0:getListSize(mDatas);
        Map<String, String> fanMap = MapUtils.Build().addKey(getContext()).addUserId().addPageSize().addCount(count).addType(this.type).end();
        FanAndFollowEvent fanAndFollowEvent = new FanAndFollowEvent();
        fanAndFollowEvent.setChildType(REQ_CODE);
        XEventUtils.getUseCommonBackJson(IVariable.GET_FOLLOW_USER, fanMap, type,fanAndFollowEvent );
    }

    @Override
    protected String initUrl() {
        return IVariable.GET_FOLLOW_USER;
    }

}
