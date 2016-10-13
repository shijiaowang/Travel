package com.example.administrator.travel.ui.me.othercenter.userinfo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.db.DBManager;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.fragment.LoadBaseFragment;
import com.example.administrator.travel.ui.me.myhobby.UserLabelBean;
import com.example.administrator.travel.ui.me.othercenter.INotify;
import com.example.administrator.travel.ui.me.othercenter.UserBean;
import com.example.administrator.travel.utils.FormatDateUtils;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.XEventUtils;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/10/4.
 * 个人信息
 */

public class UserInfoFragment extends LoadBaseFragment<UserInfoEvent> implements INotify<UserBean>{
    @BindView(R.id.fl_title) FlexboxLayout flTitle;
    @BindView(R.id.tv_name) TextView tvName;
    @BindView(R.id.tv_sex) TextView tvSex;
    @BindView(R.id.tv_address) TextView tvAddress;
    @BindView(R.id.tv_des) TextView tvDes;
    @BindView(R.id.tv_work) TextView tvWork;
    @BindView(R.id.tv_level) TextView tvLevel;
    @BindView(R.id.tv_add_time) TextView tvAddTime;
    private String userId = "";
    private LayoutInflater inflater;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = getArguments().getString(IVariable.USER_ID);
        inflater = LayoutInflater.from(getContext());

    }

    public static UserInfoFragment newInstance(String userId) {
        UserInfoFragment tabFragment = new UserInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(IVariable.USER_ID, userId);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    protected int initResLayout() {
        return R.layout.activity_other_information;
    }

    @Override
    protected Fragment registerEvent() {
        return this;
    }

    @Override
    public Class<? extends HttpEvent> registerEventType() {
        return UserInfoEvent.class;
    }

    @Override
    public void onSuccess(UserInfoEvent userInfoEvent) {
        UserInfoBean userInfoBean = GsonUtils.getObject(userInfoEvent.getResult(), UserInfoBean.class);
        UserBean user = userInfoBean.getData().getUser();
        initUserInfo(user);

    }

    /**
     * 填充数据
     * @param user
     */
    private void initUserInfo(UserBean user) {
        tvAddTime.setText(FormatDateUtils.FormatLongTime(IVariable.Y_M_D,user.getAdd_time()));
        tvLevel.setText("Lv."+user.getLevel());
        tvName.setText(user.getNick_name());
        tvDes.setText(user.getContent());
        tvSex.setText(user.getSex().equals("1")?"男":"女");
        tvAddress.setText(DBManager.getStringById("name",user.getProvince())+"-"+DBManager.getStringById("name",user.getCity()));
        List<UserLabelBean> interestLabel = user.getInterest_label();
        flTitle.removeAllViews();
        if (interestLabel==null || interestLabel.size()==0){
           //设置什么
        }else {
            for (UserLabelBean userLabelBean:interestLabel){
                TextView textView = (TextView) inflater.inflate(R.layout.item_activity_other_title_item, flTitle, false);
                textView.setText(userLabelBean.getName());
                flTitle.addView(textView);
            }
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onLoad(int type) {
        Map<String, String> userInfoMap = MapUtils.Build().addKey(getContext()).addMyId().add(IVariable.USER_ID, userId).addType("3").end();
        XEventUtils.postUseCommonBackJson(IVariable.OTHER_USER_INFO, userInfoMap, 0, new UserInfoEvent());
    }



    @Override
    public void notifys(List<UserBean> t) {

    }

    @Override
    public void notify(UserBean userBean) {
        initUserInfo(userBean);
    }
}
