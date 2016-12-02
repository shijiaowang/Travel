package com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment.costsetting.desremark.createsuccess;
import android.view.View;
import android.widget.LinearLayout;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseToolBarActivity;
import com.yunspeak.travel.utils.ActivityUtils;
import butterknife.BindView;
/**
 * Created by wangyang on 2016/9/1 0001.
 * 发布约伴成功
 */
public class CreateAppointSuccessActivity extends BaseToolBarActivity {
    @BindView(R.id.ll_key) LinearLayout mLlKey;
    protected void initEvent() {
      if (GlobalValue.mAppointType== IVariable.TYPE_WITH_ME){
          mLlKey.setVisibility(View.GONE);
      }
        try {
            finishActivity();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }




    private void finishActivity() {

    }




    @Override
    protected int initLayoutRes() {
        return R.layout.activity_create_appoint_success;
    }

    @Override
    protected void initOptions() {
      initEvent();
    }

    @Override
    protected String initTitle() {
        return "发布成功";
    }
}
