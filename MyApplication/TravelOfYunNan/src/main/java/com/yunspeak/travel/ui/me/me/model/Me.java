package com.yunspeak.travel.ui.me.me.model;

import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.yunspeak.travel.R;
import com.yunspeak.travel.aop.CheckNetwork;
import com.yunspeak.travel.bean.UserInfo;
import com.yunspeak.travel.bean.UserLabelBean;
import com.yunspeak.travel.global.TravelsObject;
import com.yunspeak.travel.ui.me.fansandfollow.FollowAndFanActivity;
import com.yunspeak.travel.ui.me.identityauth.IdentityAuthenticationActivity;
import com.yunspeak.travel.ui.me.level.LevelActivity;
import com.yunspeak.travel.ui.me.messagecenter.MessageCenterActivity;
import com.yunspeak.travel.ui.me.myalbum.MyAlbumActivity;
import com.yunspeak.travel.ui.me.myappoint.MyAppointActivity;
import com.yunspeak.travel.ui.me.mycollection.MyCollectionActivity;
import com.yunspeak.travel.ui.me.myhobby.MyHobbyActivity;
import com.yunspeak.travel.ui.me.mytheme.MyThemeActivity;
import com.yunspeak.travel.ui.me.ordercenter.OrdersCenterActivity;

import com.yunspeak.travel.ui.me.titlemanage.TitleManagementActivity;

import com.yunspeak.travel.ui.me.userservice.ServiceActivity;
import com.yunspeak.travel.ui.view.BadgeView;
import com.yunspeak.travel.utils.AiteUtils;
import com.yunspeak.travel.utils.ShowImageUtils;

import java.util.List;

/**
 * Created by wangyang on 2017/3/14.
 * 我的实体类
 */

public class Me extends TravelsObject{


    private Me.DataBean data;


    public Me.DataBean getData() {
        return data;
    }

    public void setData(Me.DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private UserInfo user;
        private String follow;
        private String fans;
        private int count_msg;
        private int count_travel;
        private int count_order;

        public int getCount_travel() {
            return count_travel;
        }

        public void setCount_travel(int count_travel) {
            this.count_travel = count_travel;
        }

        public int getCount_order() {
            return count_order;
        }

        public void setCount_order(int count_order) {
            this.count_order = count_order;
        }

        private List<UserLabelBean> user_label;

        public UserInfo getUser() {
            return user;
        }

        public void setUser(UserInfo user) {
            this.user = user;
        }

        public String getFollow() {
            return follow;
        }

        public void setFollow(String follow) {
            this.follow = follow;
        }

        public String getFans() {
            return fans;
        }

        public void setFans(String fans) {
            this.fans = fans;
        }

        public int getCount_msg() {
            return count_msg;
        }

        public void setCount_msg(int count_msg) {
            this.count_msg = count_msg;
        }

        public List<UserLabelBean> getUser_label() {
            return user_label;
        }

        public void setUser_label(List<UserLabelBean> user_label) {
            this.user_label = user_label;
        }

        @BindingAdapter("icon_2")
        public static void setIcon(ImageView icon,String url){
            ShowImageUtils.showCircle(icon,R.drawable.boy,url,2);
        }
        @BindingAdapter("bind:setFlex")
        public static void setFlex(FlexboxLayout flex,List<UserLabelBean> labelBeen){
            flex.removeAllViews();
            LayoutInflater layoutInflater = (LayoutInflater) flex.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (labelBeen==null || labelBeen.size()==0){
                return;
            }

            for (UserLabelBean userLabelBean:labelBeen){
                TextView textView= (TextView) layoutInflater.inflate(R.layout.item_fragment_me_title, flex, false);
                textView.setText(userLabelBean.getName());
                flex.addView(textView);
            }
        }
        @BindingAdapter("bind:badge")
        public static void bindCount(View view,int count){
            BadgeView badgeView=new BadgeView(view.getContext());
            badgeView.setTargetView(view);
            int badge=count;
            if (view.getId()==R.id.tv_message_center){
                badge=count+AiteUtils.getUnReadMessage();
            }
            badgeView.setBadgeCount(badge);
        }
        @CheckNetwork
        public void onClick(View v) {
            Context context = v.getContext();
            switch (v.getId()) {
                case R.id.tv_message_center:
                    start(context,MessageCenterActivity.class);
                    break;
                case R.id.tv_follow:
                    Intent followIntent = new Intent(context, FollowAndFanActivity.class);
                    followIntent.putExtra(FollowAndFanActivity.FOLLOW_SELECT, true);
                    context.startActivity(followIntent);
                    break;
                case R.id.tv_fan:
                    Intent fanIntent = new Intent(context, FollowAndFanActivity.class);
                    fanIntent.putExtra(FollowAndFanActivity.FOLLOW_SELECT, false);
                    context.startActivity(fanIntent);
                    break;
                case R.id.iv_album:
                    start(context,MyAlbumActivity.class);
                    break;
                case R.id.tv_appoint:
                    start(context,MyAppointActivity.class);
                    break;
                case R.id.tv_my_collection:
                    start(context,MyCollectionActivity.class);
                    break;
                case R.id.tv_my_order:
                    start(context,OrdersCenterActivity.class);
                    break;
                case R.id.iv_service:
                    ServiceActivity.start(context,true);
                    break;
                case R.id.iv_card:
                    start(context,IdentityAuthenticationActivity.class);
                    break;
                case R.id.tv_title_edit:
                    start(context,TitleManagementActivity.class);
                    break;
                case R.id.tv_level:
                    start(context,LevelActivity.class);
                    break;
                case R.id.iv_hobby:
                    start(context,MyHobbyActivity.class);
                    break;
                case R.id.iv_theme:
                    start(context,MyThemeActivity.class);
                    break;
            }
        }

        private void start(Context context,Class c) {
            context.startActivity(new Intent(context,c));
        }
    }
}
