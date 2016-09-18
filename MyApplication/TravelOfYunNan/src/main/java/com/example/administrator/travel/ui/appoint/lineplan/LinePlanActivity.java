package com.example.administrator.travel.ui.appoint.lineplan;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.activity.BarBaseActivity;
import com.example.administrator.travel.ui.appoint.dialog.EnterAppointDialog;
import com.example.administrator.travel.ui.view.ToShowAllListView;
import com.example.administrator.travel.ui.view.refreshview.XListView;
import com.example.administrator.travel.utils.JsonUtils;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by Administrator on 2016/8/4 0004.
 * 路线计划
 */
public class LinePlanActivity extends BarBaseActivity {
    @ViewInject(R.id.lv_line)
    private ListView mLvLine;
    @ViewInject(R.id.bt_next)
    private Button mBtNext;

    private LinePlanAdapter linePlanAdapter;


    @Override
    protected int setContentLayout() {
        return R.layout.activity_line_plan;
    }

    @Override
    protected void initEvent() {
        mBtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    saveRoutes();//保存路程信息
                    LogUtils.e(JsonUtils.getRoutesJsonObject().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                checkData();
            }
        });
        registerEventBus(this);
    }

    /**
     * 保存线路计划
     */
    private void saveRoutes() throws JSONException {
        JSONObject routesJsonObject = JsonUtils.getRoutesJsonObject();
        JSONArray jsonArray=new JSONArray();
       for (int i=1;i<GlobalValue.mLineBeans.size()-1;i++){//去掉集合地和解散地
           LineBean lineBean = GlobalValue.mLineBeans.get(i);
           if (lineBean.getDestinations()!=null && lineBean.getDestinations().size()>0){
               JSONObject jsonObject=new JSONObject();
               for (LineBean.Destination destination:lineBean.getDestinations()){
                   jsonObject.put(IVariable.TD_ID,destination.getId());
                   jsonObject.put(IVariable.TIME,lineBean.getDate());
                   jsonArray.put(jsonObject);
               }
           }
       }
        routesJsonObject.put(IVariable.ROUTES,jsonArray);

    }

    /**
     * 检查目的地等数据是否有
     */
    private void checkData() {
        // TODO: 2016/9/18 0018 之后再做数据合法性校验
        try {
            JSONObject basecJsonObject = JsonUtils.getBasecJsonObject();
            LogUtils.e(basecJsonObject.toString());
            String meet = basecJsonObject.getString(IVariable.MEET_ADDRESS);
            String over = basecJsonObject.getString(IVariable.OVER_ADDRESS);
            finish();//验证数据成功就finsh
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initViewData() {
        linePlanAdapter = new LinePlanAdapter(this, GlobalValue.mLineBeans);
        mLvLine.setAdapter(linePlanAdapter);
    }


    @Override
    protected String setTitleName() {
        return "路线计划";
    }

    @Override
    public float getAlpha() {
        return 1f;
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
}
