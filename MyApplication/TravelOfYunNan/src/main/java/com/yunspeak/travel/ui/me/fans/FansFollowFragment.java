package com.yunspeak.travel.ui.me.fans;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseLoadAndRefreshFragment;
import com.yunspeak.travel.ui.baseui.BasePullAndRefreshModel;
import com.yunspeak.travel.ui.me.fans.model.FansFollow;
import com.yunspeak.travel.ui.me.fans.model.FansFollowModel;
import com.yunspeak.travel.ui.me.fans.model.FansFollowRecycleModel;


/**
 * Created by wangyang on 2017/3/21.
 * 粉丝 和关注的人
 */

public class FansFollowFragment extends BaseLoadAndRefreshFragment<FansFollow, FansFollowModel> {
    FansFollowRecycleModel fansFollowRecycleModel;
    private String type = "-1";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle arguments = getArguments();
            type = arguments.getString(IVariable.TYPE);
        }
    }

    /**
     * @param type 3关注人，2粉丝
     * @return FansFollowFragment
     */
    public static FansFollowFragment newInstance(String type) {
        FansFollowFragment fanAndFollowFragment = new FansFollowFragment();
        Bundle bundle = new Bundle();
        bundle.putString(IVariable.TYPE, type);
        fanAndFollowFragment.setArguments(bundle);
        return fanAndFollowFragment;
    }


    @Override
    protected void onReceive(FansFollow datas) {
        super.onReceive(datas);
        if (this.type.equals("-1")) {
            statusView.showErrorView();
        }
    }

    @Override
    protected BasePullAndRefreshModel<FansFollowModel> initModel() {
        fansFollowRecycleModel = new FansFollowRecycleModel(this.type);
        return fansFollowRecycleModel;
    }
}
