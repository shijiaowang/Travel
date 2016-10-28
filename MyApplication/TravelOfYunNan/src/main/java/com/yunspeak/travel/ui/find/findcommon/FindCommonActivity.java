package com.yunspeak.travel.ui.find.findcommon;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.SelectCommonBean;
import com.yunspeak.travel.event.DestinationEvent;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.global.ParentPopClick;
import com.yunspeak.travel.ui.appoint.popwindow.AppointCommonPop;
import com.yunspeak.travel.ui.appoint.popwindow.AppointOrderPop;
import com.yunspeak.travel.ui.appoint.withme.withmedetail.PricebasecBean;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewActivity;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.view.refreshview.XListView;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;

import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/30.
 * 目的地  美食共用
 */
public class FindCommonActivity extends BaseRecycleViewActivity<DestinationEvent, DestinationBean, DestinationBean.DataBean.BodyBean> implements View.OnClickListener {
    private String content = "";//搜索内容
    private String province = "";
    private String city = "";
    private String typelist = "";
    private String star = "";
    private String score = "";
    private String type;
    private String url;
    private boolean isDestination;
    private TextView mTvSearch;
    private EditText mEtSearch;
    private boolean loactionIsGet=false;
    String[] timeTypePop = {"·\u3000默认", "·\u3000按星级", "·\u3000按评分"};
    String[] orderType = {"默认", "按星级", "按评分"};
    private int orderPosition = 0;//选中的
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    private AppointOrderPop appointOrderPop;
    private AppointCommonPop appointCommonPop;
    private TextView mTvLocation;
    private TextView mTvType;
    private TextView mTvOrder;
    private List<CityBean> provinceBean;
    private List<CityBean> cityBean;
    private Map<String, List<CityBean>> citys;


    @Override
    protected void initEvent() {
        super.initEvent();
        try {
            changeNeedHideView(mSwipe);
            mFlContent.removeView(mRlEmpty);
            mRlEmpty.setVisibility(View.GONE);
            llRoot.addView(mRlEmpty);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mVsContent.setLayoutResource(R.layout.activity_destination);
        mVsContent.inflate();
        changeMargin(0, 5);
        type = getIntent().getStringExtra(IVariable.TYPE);
        isDestination = type.equals(IVariable.TYPE_DESTINATION);
        url = isDestination ? IVariable.FIND_DESTINATION : IVariable.FIND_FOOD;
        mTvSearch = (TextView) findViewById(R.id.tv_search);
        mEtSearch = (EditText) findViewById(R.id.et_destination_search);
        mTvOrder = (TextView) findViewById(R.id.tv_order);
        mTvLocation = (TextView) findViewById(R.id.tv_location);
        mTvType = (TextView) findViewById(R.id.tv_type);
        mTvOrder.setOnClickListener(this);
        mTvLocation.setOnClickListener(this);
        mTvType.setOnClickListener(this);
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                //一般输入法或搜狗输入法点击搜索按键
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    //这里调用搜索方法
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mEtSearch.getWindowToken(), 0); //强制隐藏键盘
                    search();
                    return true;
                }
                return false;
            }
        });
        mTvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
    }

    private void search() {
        content = mEtSearch.getText().toString().trim();
        resetIsFirstInflate();
        setIsProgress(true);
        onLoad(TYPE_REFRESH);
    }



    @Override
    protected List<DestinationBean.DataBean.BodyBean> childChangeData(final DestinationBean parentBean, DestinationEvent destinationEvent) {
        if (!loactionIsGet){
            try {
                x.task().run(new Runnable() {
                    @Override
                    public synchronized void run() {
                        if (loactionIsGet)return;
                        provinceBean = parentBean.getData().getProvince();
                        cityBean = parentBean.getData().getCity();
                        citys = new HashMap<String, List<CityBean>>();
                        for (CityBean provice:provinceBean){
                            List<CityBean> cityBeanList=new ArrayList<CityBean>();
                            citys.put(provice.getId(),cityBeanList);
                        }
                        for (CityBean cityBean1:cityBean){
                            if (cityBean1!=null && citys.containsKey(cityBean1.getUpid())){
                                //包含就放入集合
                                citys.get(cityBean1.getUpid()).add(cityBean1);
                            }
                        }
                        loactionIsGet=true;
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return parentBean.getData().getBody();
    }

    @Override
    protected BaseRecycleViewAdapter initAdapter(List<DestinationBean.DataBean.BodyBean> mDatas) {
        return new DestinationAdapter(mDatas, this, isDestination);
    }

    @Override
    protected boolean isChangeData() {
        return true;
    }

    @Override
    protected String initUrl() {
        return url;
    }

    private void showType() {
        if (!loactionIsGet)return;
        if (appointCommonPop == null && provinceBean!=null && provinceBean.size()!=0) {
            provinceBean.get(0).setChecked(true);
            appointCommonPop = AppointCommonPop.newInstance(provinceBean, citys, new ParentPopClick() {
                @Override
                public void onClick(int type) {
                    city = appointCommonPop.getTyepString();
                    onLoad(TYPE_REFRESH);

                }
            });
        }
        if (appointCommonPop.isShowing()) {
            appointCommonPop.dismiss();
        } else {
            appointCommonPop.showDown(this,mTvLocation);
        }
    }

     private void orderPop(final TextView textView, final String[] titile, int clickPosition) {
         if (appointOrderPop == null) {
             appointOrderPop = new AppointOrderPop();
         }
         appointOrderPop.setOnItemClickListener(new AppointOrderPop.OnItemClickListener() {
             @Override
             public void onItemClick(int type) {
                 textView.setText(orderType[type]);
                      orderPosition = type;
                 if (orderPosition==1){
                     score="1";
                     star="";
                 }else if (orderPosition==2){
                     star="1";
                     score="";
                 }else {
                     star="";
                     score="";
                 }
                 onLoad(TYPE_REFRESH);
             }
         });
         appointOrderPop.showOrderPop(this, mTvOrder, titile, clickPosition);
     }
    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        super.childAdd(builder, type);
        builder.add(IVariable.CONTENT, content).add(IVariable.PROVINCE, province).add(IVariable.CITY, city).add(IVariable.TYPELIST, typelist)
                .add(IVariable.STAR, star).add(IVariable.SCORE, score);
    }


    @Override
    protected String initTitle() {
        return isDestination ? "目的地" : "美食";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_location:
                showType();
                break;
            case R.id.tv_order:
                orderPop(mTvOrder,timeTypePop,orderPosition);
                break;
        }
    }
}
