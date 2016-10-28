package com.yunspeak.travel.ui.appoint.travelplan.lineplan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseToolBarActivity;
import com.yunspeak.travel.utils.JsonUtils;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/4 0004.
 * 路线计划
 */
public class LinePlanActivity extends BaseToolBarActivity {
    @BindView(R.id.lv_line) ListView mLvLine;
    @BindView(R.id.bt_next) Button mBtNext;
    private LinePlanAdapter linePlanAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerEventBus(this);
    }

    protected void initEvent() {
        mBtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    saveRoutes();//保存路程信息
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                checkData();
            }
        });
    }


    /**
     * 保存线路计划
     */
    private void saveRoutes() throws JSONException {
        JSONArray routesJsonArray = JsonUtils.getRoutesJsonArray();
       for (int i=1;i<GlobalValue.mLineBeans.size()-1;i++){//去掉集合地和解散地
           LineBean lineBean = GlobalValue.mLineBeans.get(i);
           if (lineBean.getDestinations()!=null && lineBean.getDestinations().size()>0){
               JSONObject jsonObject=new JSONObject();
               for (LineBean.Destination destination:lineBean.getDestinations()){
                   jsonObject.put(IVariable.TD_ID,destination.getId());
                   jsonObject.put(IVariable.TIME,lineBean.getDate());
                   routesJsonArray.put(jsonObject);
               }
           }
       }

    }

    /**
     * 检查目的地等数据是否有
     */
    private void checkData() {
        // TODO: 2016/9/18 0018 之后再做数据合法性校验
        try {
            JSONObject basecJsonObject = JsonUtils.getBasecJsonObject();
            String meet = basecJsonObject.getString(IVariable.MEET_ADDRESS);
            String over = basecJsonObject.getString(IVariable.OVER_ADDRESS);
            finish();//验证数据成功就finsh
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected void initViewData() {
        //再次进入加载目的地
        linePlanAdapter = new LinePlanAdapter(this, GlobalValue.mLineBeans);
        mLvLine.setAdapter(linePlanAdapter);
    }



    @Subscribe
    public void onEvent(LinePlanEvent linePlanEvent) {
        try {
            dealData(linePlanEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void dealData(LinePlanEvent linePlanEvent) {
         if (linePlanEvent.isDelete()) {
             linePlanAdapter.notifyDataSetChanged();
        } else {
            LineBean lineBean = GlobalValue.mLineBeans.get(linePlanEvent.getPosition());
            lineBean.getDestinations().add(linePlanEvent.getDestination());
            linePlanAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterEventBus(this);
    }

    @Override
    protected int initLayoutRes() {
        return  R.layout.activity_line_plan;
    }

    @Override
    protected void initOptions() {
       initEvent();
        initViewData();
    }

    @Override
    protected String initTitle() {
        return "路线计划";
    }

}
