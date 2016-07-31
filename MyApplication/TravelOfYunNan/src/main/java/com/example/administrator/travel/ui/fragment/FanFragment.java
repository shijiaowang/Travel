package com.example.administrator.travel.ui.fragment;

import android.widget.ListView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Fan;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.FanAdapter;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/7/18 0018.
 */
public class FanFragment extends BaseFragment {

    private ListView mLvFollowFan;
    private FanAdapter fanAdapter;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_follow_and_fan;
    }

    @Override
    protected void initView() {
        mLvFollowFan = (ListView) root.findViewById(R.id.lv_follow_fan);

    }

    @Override
    protected void initData() {
        Map<String, String> follwMap = MapUtils.Build().addKey(getContext()).add("user_id", "1").end();
        XEventUtils.getUseCommonBackJson(IVariable.GET_FOLLOW_USER, follwMap, IVariable.TYPE_GET_FAN);

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    public void onEvent(HttpEvent event){
        if (event.getType()!=IVariable.TYPE_GET_FAN && event.getType()!=IVariable.TYPE_REFRESH){
            return;
        }
       if (event.isSuccess()){
           if (event.getType()==IVariable.TYPE_GET_FAN) {
               Fan fan = GsonUtils.getObject(event.getResult(), Fan.class);
               List<Fan.FanPeople> data = fan.getData();
               fanAdapter = new FanAdapter(getContext(), data);
               mLvFollowFan.setAdapter(fanAdapter);
           }else {
               Fan fan = GsonUtils.getObject(event.getResult(), Fan.class);
               List<Fan.FanPeople> data = fan.getData();
               fanAdapter.notifyData(data);
           }
       }else {
           ToastUtils.showToast(getContext(),event.getMessage());
       }
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}