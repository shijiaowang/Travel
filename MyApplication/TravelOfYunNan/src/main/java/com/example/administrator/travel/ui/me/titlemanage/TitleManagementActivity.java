package com.example.administrator.travel.ui.me.titlemanage;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.LoadingBarBaseActivity;
import com.example.administrator.travel.ui.view.FlowLayout;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2016/9/7 0007.
 * 称号管理
 */
public class TitleManagementActivity extends LoadingBarBaseActivity {
    public static final int ADD_TITLE=0;
    public static final int REMOVE_TITLE=1;
    private List<Fragment> fragments=new ArrayList<>();
    private String[] mTitles;
    @ViewInject(R.id.vp_pager)
    private ViewPager mVpPager;
    @ViewInject(R.id.tl_title)
    private TabLayout mTlTitle;
    @ViewInject(R.id.fl_title)
    private FlowLayout mFlTitle;
    private List<TitleManagementBean.DataBean.UserLabelBean> userLabel=new ArrayList<>(5);
    private LayoutInflater inflater;
    private TextView mTvRight;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_title_management;
    }

    @Override
    protected void initEvent() {
        mTvRight = getmTvRightIcon();
        mTvRight.setText("保存");
        mTvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLabel();
            }
        });
        inflater = LayoutInflater.from(this);

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
        for (TitleManagementBean.DataBean.UserLabelBean labelBean:userLabel){
            if (userLabel.indexOf(labelBean)==userLabel.size()-1){
                stringBuffer.append(labelBean.getId());//最后一位没有逗号
            }else {
                stringBuffer.append(labelBean.getId()+",");
            }
        }
        return stringBuffer.toString();
    }


    @Override
    protected void onLoad(int type) {
        Map<String, String> titleMap = MapUtils.Build().addKey(this).addUserId().end();
        XEventUtils.getUseCommonBackJson(IVariable.TITLE_LIST,titleMap,TYPE_LOAD,new TitleManagementEvent());
    }

    @Override
    protected Activity initViewData() {
        return this;
    }

    @Override
    protected String setTitleName() {
        return "称号管理";
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
    private void removeLabel(TitleManagementBean.DataBean.UserLabelBean userLabelBean) {
        if (userLabel==null || userLabel.size()==0)return;
        for (TitleManagementBean.DataBean.UserLabelBean userLabelBean1:userLabel){
            if (userLabelBean1.getId().equals(userLabelBean.getId())){
                int index = userLabel.indexOf(userLabelBean1);
                mFlTitle.removeViewAt(index);
                userLabel.remove(index);
            }
        }
    }

    @Subscribe
    public void onEvent(TitleManagementEvent event){
        setIsProgress(false);
     if (event.isSuccess()){
         try {
             if (event.getType()==TYPE_SAVE){
                 ToastUtils.showToast(event.getMessage());
             }else {
                 dealData(event);
             }
         } catch (Exception e) {
             e.printStackTrace();
             setIsError(true);
         }
     }else {
         if (event.getType()==TYPE_LOAD) {
             setIsError(true);
         }
         ToastUtils.showToast(event.getMessage());
     }
    }

    private void dealData(TitleManagementEvent event) {
        TitleManagementBean titleManagementBean = GsonUtils.getObject(event.getResult(), TitleManagementBean.class);
        TitleManagementBean.DataBean data = titleManagementBean.getData();
        initTitle(data.getUser_label());
        List<TitleManagementBean.DataBean.LabelTitleBean> labelTitle = data.getLabel_title();
        mVpPager.setOffscreenPageLimit(labelTitle.size());
        mTitles=new String[labelTitle.size()];
        int i=0;
        for (TitleManagementBean.DataBean.LabelTitleBean labelTitleBean:labelTitle){
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
        mVpPager.setAdapter(new TitlePagerAdapter(getSupportFragmentManager()));
        mTlTitle.setupWithViewPager(mVpPager);
    }

    /**
     * 初始化用户已经佩戴的标签
     * @param user_label
     */
    private void initTitle(List<TitleManagementBean.DataBean.UserLabelBean> user_label) {
        if (!(user_label==null|| user_label.size()==0)){
            mFlTitle.removeAllViews();
            for (TitleManagementBean.DataBean.UserLabelBean labelBean:user_label) {
                addLabel(labelBean);
            }
        }
    }

    private void addLabel(TitleManagementBean.DataBean.UserLabelBean labelBean) {
        if (labelBean==null)return;
        if (userLabel.size()==7){
            ToastUtils.showToast("您当前最多佩戴7个称号");
        }
        userLabel.add(labelBean);
        View inflate = inflater.inflate(R.layout.item_activity_setting_title_select, mFlTitle, false);
        View delete = inflate.findViewById(R.id.tv_delete);
        delete.setTag(labelBean);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TitleManagementBean.DataBean.UserLabelBean tag = (TitleManagementBean.DataBean.UserLabelBean) v.getTag();
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
    public float getAlpha() {
        return 1.0f;
    }
    class TitlePagerAdapter extends FragmentPagerAdapter{

        public TitlePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }
}
