package com.yunspeak.travel.ui.me.myhobby;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.ui.me.titlemanage.TitleChangeEvent;
import com.yunspeak.travel.ui.me.titlemanage.TitlePagerAdapter;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.google.android.flexbox.FlexboxLayout;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import butterknife.BindView;

/**
 * Created by wangyang on 2016/9/28 0028.
 * 我的兴趣
 */

public class MyHobbyActivity extends BaseNetWorkActivity<MyHobbyEvent> {
    @BindView(R.id.fl_title) FlexboxLayout mFlTitle;
    @BindView(R.id.vp_pager) ViewPager mVpPager;
    @BindView(R.id.tl_title) TabLayout mTlTitle;
    private List<Fragment> fragments=new ArrayList<>();
    private LayoutInflater inflater;
    private List<UserLabelBean> userLabel=new ArrayList<>();
    private String[] mTitles;

    @Override
    protected void initEvent() {
         inflater=LayoutInflater.from(this);
    }



    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {

    }

    @Override
    protected String initUrl() {
        return IVariable.GET_HOBBY_LIST;
    }

    @Subscribe
    public void onEvent(UserLabelBean userLabelBean){
        if (userLabelBean!=null){
            addLabel(userLabelBean);
        }
    }


    private void dealData(MyHobbyEvent event) {
        switch (event.getType()){
            case TYPE_REFRESH:
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

    @Override
    protected String initRightText() {
        return "保存";
    }

    @Override
    protected void otherOptionsItemSelected(MenuItem item) {

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
    protected void onSuccess(MyHobbyEvent event) {
        dealData(event);
    }


    @Override
    protected int initLayoutRes() {
        return  R.layout.activity_my_hobby;
    }

    @Override
    protected String initTitle() {
        return "我的兴趣";
    }
}
