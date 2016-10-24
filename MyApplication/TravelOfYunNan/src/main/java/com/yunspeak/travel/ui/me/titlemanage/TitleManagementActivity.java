package com.yunspeak.travel.ui.me.titlemanage;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.ui.baseui.LoadingBarBaseActivity;
import com.yunspeak.travel.ui.me.myhobby.LabelTitleBean;
import com.yunspeak.travel.ui.me.myhobby.UserLabelBean;
import com.yunspeak.travel.ui.view.FlowLayout;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/9/7 0007.
 * 称号管理
 */
public class TitleManagementActivity extends BaseNetWorkActivity<TitleManagementEvent> {
    public static final int ADD_TITLE=0;
    public static final int REMOVE_TITLE=1;
    private List<Fragment> fragments=new ArrayList<>();
    private String[] mTitles;
    @BindView(R.id.vp_pager) ViewPager mVpPager;
    @BindView(R.id.tl_title) TabLayout mTlTitle;
    @BindView(R.id.fl_title) FlowLayout mFlTitle;
    private List<UserLabelBean> userLabel=new ArrayList<>(5);
    private LayoutInflater inflater;


    @Override
    protected void initEvent() {
        inflater = LayoutInflater.from(this);
    }


    @Override
    protected void otherOptionsItemSelected(MenuItem item) {
        saveLabel();
    }

    /**
     * 保存称号
     */
    private void saveLabel() {
        String labelId=getLabelIds();
        Map<String, String> labelMap = MapUtils.Build().addKey(this).addUserId().addLabel(labelId).end();
        XEventUtils.postUseCommonBackJson(IVariable.SAVE_TITLE,labelMap,TYPE_SAVE,new TitleManagementEvent());
    }

    /**
     * 获取label Ids
     * @return
     */
    private String getLabelIds() {
        StringBuilder stringBuffer=new StringBuilder("");
        if (userLabel==null || userLabel.size()==0)return "";
        for (UserLabelBean labelBean:userLabel){
            if (userLabel.indexOf(labelBean)==userLabel.size()-1){
                stringBuffer.append(labelBean.getId());//最后一位没有逗号
            }else {
                stringBuffer.append(labelBean.getId()+",");
            }
        }
        return stringBuffer.toString();
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {

    }

    @Override
    protected String initRightText() {
        return "保存";
    }



    @Override
    protected String initUrl() {
        return IVariable.TITLE_LIST;
    }

    @Subscribe
    public void onEvent(TitleChangeEvent event){
        if (event.getType()==ADD_TITLE){
            addLabel(event.getUserLabelBean());
        }else {
            removeLabel(event.getUserLabelBean());
        }
    }

    /**
     * 移除标签
     * @param userLabelBean
     */
    private void removeLabel(UserLabelBean userLabelBean) {
        if (userLabel==null || userLabel.size()==0)return;
        for (UserLabelBean userLabelBean1:userLabel){
            if (userLabelBean1.getId().equals(userLabelBean.getId())){
                int index = userLabel.indexOf(userLabelBean1);
                mFlTitle.removeViewAt(index);
                userLabel.remove(index);
            }
        }
    }



    private void dealData(TitleManagementEvent event) {
        TitleManagementBean titleManagementBean = GsonUtils.getObject(event.getResult(), TitleManagementBean.class);
        TitleManagementBean.DataBean data = titleManagementBean.getData();
        initTitle(data.getUser_label());
        List<LabelTitleBean> labelTitle = data.getLabel_title();
        mVpPager.setOffscreenPageLimit(labelTitle.size());
        mTitles=new String[labelTitle.size()];
        int i=0;
        for (LabelTitleBean labelTitleBean:labelTitle){
                mTitles[i]=labelTitleBean.getName();
                i++;
        }
        List<List<OfficialLabelBean>> official_label = data.getOfficial_label();
        i=0;
        for (List<OfficialLabelBean> officialLabelBeen:official_label){
            TitleManagementFragment newInstance = TitleManagementFragment.newInstance(officialLabelBeen,labelTitle.get(i).getId());
            fragments.add(newInstance);
            i++;
        }
        mVpPager.setAdapter(new TitlePagerAdapter(getSupportFragmentManager(),fragments,mTitles));
        mTlTitle.setupWithViewPager(mVpPager);
    }

    /**
     * 初始化用户已经佩戴的标签
     * @param user_label
     */
    private void initTitle(List<UserLabelBean> user_label) {
        if (!(user_label==null|| user_label.size()==0)){
            mFlTitle.removeAllViews();
            for (UserLabelBean labelBean:user_label) {
                addLabel(labelBean);
            }
        }
    }

    private void addLabel(UserLabelBean labelBean) {
        if (labelBean==null)return;
        if (userLabel.size()==7){
            ToastUtils.showToast("您当前最多佩戴7个称号");
        }
        userLabel.add(labelBean);
        GlobalValue.count=userLabel.size();
        View inflate = inflater.inflate(R.layout.item_activity_setting_title_select, mFlTitle, false);
        View delete = inflate.findViewById(R.id.tv_delete);
        delete.setTag(labelBean);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLabelBean tag = (UserLabelBean) v.getTag();
                int index = userLabel.indexOf(tag);
                EventBus.getDefault().post(new TitleDeleteEvent(tag.getClassX(),tag.getId()));
                mFlTitle.removeViewAt(index);
                userLabel.remove(index);

            }
        });
        TextView tvTitle = (TextView) inflate.findViewById(R.id.tv_title);
        tvTitle.setText(labelBean.getName());
        mFlTitle.addView(inflate);
    }


    @Override
    protected void onSuccess(TitleManagementEvent titleManagementEvent) {
        if (titleManagementEvent.getType()==TYPE_SAVE){
            ToastUtils.showToast(titleManagementEvent.getMessage());
            finish();
        }else {
            dealData(titleManagementEvent);
        }
    }

    @Override
    protected void onFail(TitleManagementEvent titleManagementEvent) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlobalValue.count=0;
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_title_management;
    }

    @Override
    protected String initTitle() {
        return "称号管理";
    }
}
