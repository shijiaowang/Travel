package com.example.administrator.travel.ui.appoint.adddestination;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.administrator.travel.R;
import com.example.administrator.travel.db.DBManager;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.LoadingBarBaseActivity;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/8 0008.
 * 添加自定义景点
 */
public class AddCustomDestinationActivity extends LoadingBarBaseActivity implements View.OnClickListener {
    @ViewInject(R.id.tv_address)
    private TextView mTvAddress;
    @ViewInject(R.id.tv_add)
    private TextView mTvAdd;
    @ViewInject(R.id.bt_next)
    private Button mBtNext;//保存修改
    @ViewInject(R.id.et_des)
    private EditText mEtDes;
    @ViewInject(R.id.et_name)
    private EditText mEtName;
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private OptionsPickerView pvOptions;
    private String name;
    private String des;
    private String address;
    private String id;
    private String cityName;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_add_custom_destination;
    }

    @Override
    protected void initEvent() {
        initCity();
        mTvAddress.setOnClickListener(this);
        mBtNext.setOnClickListener(this);
    }

    @Override
    protected void onLoad() {
        setIsProgress(false);
    }

    private void showCitySelect() {
        //选项选择器
        if (pvOptions == null) {
            pvOptions = new OptionsPickerView(this);
            //三级联动效果
            pvOptions.setPicker(options1Items, options2Items, true);
            //设置选择的三级单位
//          pwOptions.setLabels("省", "市", "区");
            pvOptions.setTitle("选择城市");
            pvOptions.setSelectOptions(24,1,1);//默认选中云南 25-1
            pvOptions.setCyclic(false, true, true);
            pvOptions.setCancelable(true);
            //设置默认选中的三级项目
            pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

                @Override
                public void onOptionsSelect(int options1, int option2, int options3) {
                    //返回的分别是三个级别的选中位置
                    String tx = options1Items.get(options1).getPickerViewText()
                            + options2Items.get(options1).get(option2);
                    address = tx;
                    id = options1Items.get(options1).getId();//省得id
                    cityName = options2Items.get(options1).get(option2);
                    mTvAdd.setText(tx);
                }
            });

        }
        hideSoftWore(mEtName);

        pvOptions.show();

    }

    private void initCity() {
        x.task().run(new Runnable() {
            @Override
            public void run() {
                try {
                    options1Items = DBManager.getProvince();
                    options2Items = DBManager.getCity(options1Items);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected Activity initViewData() {
        return this;
    }

    @Override
    protected String setTitleName() {
        return "添加自定义景点";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (pvOptions != null && pvOptions.isShowing()) {
                pvOptions.dismiss();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_address:
                try {
                    showCitySelect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.bt_next:
                createSpot();
                break;
        }
    }

    private void createSpot() {
        name = getString(mEtName);
        des = getString(mEtName);
        if (StringUtils.isEmpty(name)) {
            ToastUtils.showToast("自定义景点名称不能为空。");
            return;
        }
        if (StringUtils.isEmpty(des)) {
            ToastUtils.showToast("自定义景点地址不能为空。");
            return;
        }
        String cityId = DBManager.getCityId(cityName, id);
        Map<String, String> createSpotMap = MapUtils.Build().addKey(this).addUserId().addTitle(name).addContent(des).addProvince(id).addCity(cityId).addAddress(address).end();
        XEventUtils.postUseCommonBackJson(IVariable.ADD_CUSTOM_SPOT, createSpotMap, 0, new AddCustomSpotEvent());
    }
    @Subscribe
    public void onEvent(AddCustomSpotEvent event){
        ToastUtils.showToast(event.getMessage());
        if (event.isSuccess()){
            LogUtils.e(event.getResult());
        }
    }
}
