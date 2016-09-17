package com.example.administrator.travel.ui.appoint.lineplan;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.activity.BarBaseActivity;
import com.example.administrator.travel.ui.appoint.dialog.EnterAppointDialog;

import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by Administrator on 2016/8/4 0004.
 * 路线计划
 */
public class LinePlanActivity extends BarBaseActivity {
    @ViewInject(R.id.lv_line)
    private ListView mLvLine;
    @ViewInject(R.id.tv_start_add)
    private TextView mTvAddStart;
    @ViewInject(R.id.tv_end_add)
    private TextView tvAddEnd;
    @ViewInject(R.id.ssv_scroll)
    private ScrollView mSsvScroll;

    private LinePlanAdapter linePlanAdapter;


    @Override
    protected int setContentLayout() {
        return R.layout.activity_line_plan;
    }

    @Override
    protected void initEvent() {
        registerEventBus(this);
        mTvAddStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterAppointDialog.showDialogAddDestination(LinePlanActivity.this);
            }
        });

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

        } else {
            LineBean lineBean = GlobalValue.mLineBeans.get(linePlanEvent.getPosition());
            lineBean.getDestinations().add(linePlanEvent.getAdd());
            linePlanAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterEventBus(this);
    }
}
