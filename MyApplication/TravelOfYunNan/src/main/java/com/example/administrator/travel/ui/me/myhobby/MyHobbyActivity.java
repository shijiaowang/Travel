package com.example.administrator.travel.ui.me.myhobby;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.LoadingBarBaseActivity;
import com.example.administrator.travel.ui.me.titlemanage.OfficialLabelBean;
import com.example.administrator.travel.ui.me.titlemanage.TitleChangeEvent;
import com.example.administrator.travel.ui.me.titlemanage.TitleDeleteEvent;
import com.example.administrator.travel.ui.me.titlemanage.TitleManagementActivity;
import com.example.administrator.travel.ui.me.titlemanage.TitleManagementFragment;
import com.example.administrator.travel.ui.me.titlemanage.TitlePagerAdapter;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;
import com.google.android.flexbox.FlexboxLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2016/9/28 0028.
 * 我的兴趣
 */

public class MyHobbyActivity extends LoadingBarBaseActivity{
    @ViewInject(R.id.fl_title)
    private FlexboxLayout mFlTitle;
    @ViewInject(R.id.vp_pager)
    private ViewPager mVpPager;
    @ViewInject(R.id.tl_title)
    private TabLayout mTlTitle;
    private List<Fragment> fragments=new ArrayList<>();
    private LayoutInflater inflater;
    private List<UserLabelBean> userLabel=new ArrayList<>();
    private String[] mTitles;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_my_hobby;
    }

    @Override
    protected void initEvent() {
         inflater=LayoutInflater.from(this);
    }

    @Override
    protected void onLoad(int type) {
        Map<String, String> hobbyMap = MapUtils.Build().addKey(this).addUserId().end();
        XEventUtils.getUseCommonBackJson(IVariable.GET_HOBBY_LIST,hobbyMap,TYPE_LOAD,new MyHobbyEvent());
    }
    @Subscribe
    public void onEvent(UserLabelBean userLabelBean){
        if (userLabelBean!=null){
            addLabel(userLabelBean);
        }
    }
    @Subscribe
   public void onEvent(MyHobbyEvent event){
        setIsProgress(false);
        if (event.isSuccess()){
            try {
                dealData(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            if (event.getType()==TYPE_LOAD){
                setIsError(true);
            }
            ToastUtils.showToast(event.getMessage());
        }
   }

    private void dealData(MyHobbyEvent event) {
        switch (event.getType()){
            case TYPE_LOAD:
                dealLoadData(event);
                break;
        }
    }

    /**
     * 处理第一次读取的数据
     * @param event
     */
    private void dealLoadData(MyHobbyEvent event) {
        MyHobbyBean myHobbyBean = GsonUtils.getObject(event.getResult(), MyHobbyBean.class);
        MyHobbyBean.DataBean data = myHobbyBean.getData();
        initTitle(data.getUser_label());
        List<LabelTitleBean> labelTitle = data.getLabel_title();
        mVpPager.setOffscreenPageLimit(labelTitle.size());
        mTitles = new String[labelTitle.size()];
        int i=0;
        for (LabelTitleBean labelTitleBean:labelTitle){
            mTitles[i]=labelTitleBean.getName();
            i++;
        }
        List<List<UserLabelBean>> official_label = data.getOfficial_label();
        i=0;
        for (List<UserLabelBean> userLabelBeen:official_label){
            Iterator<UserLabelBean> iterator = userLabelBeen.iterator();
            while (iterator.hasNext()){
                UserLabelBean next = iterator.next();
                for (UserLabelBean labelBean:userLabel){
                    if (labelBean.getId().equals(next.getId())){
                        iterator.remove();
                        break;
                    }
                }
            }
            MyHobbyFragment newInstance = MyHobbyFragment.newInstance(userLabelBeen,labelTitle.get(i).getId());
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
        View inflate = inflater.inflate(R.layout.item_flow_yellow, mFlTitle, false);
        View delete = inflate.findViewById(R.id.tv_delete);
        delete.setTag(labelBean);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLabelBean tag = (UserLabelBean) v.getTag();
                int index = userLabel.indexOf(tag);
                EventBus.getDefault().post(new TitleChangeEvent(0,tag));
                mFlTitle.removeViewAt(index);
                userLabel.remove(index);

            }
        });
        TextView tvTitle = (TextView) inflate.findViewById(R.id.tv_title);
        tvTitle.setText(labelBean.getName());
        mFlTitle.addView(inflate);
    }


    @Override
    protected Activity initViewData() {
        return this;
    }

    @Override
    protected String setTitleName() {
        return "我的兴趣";
    }

    @Override
    public float getAlpha() {
        return 1f;
    }
}
