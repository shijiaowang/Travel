package com.yunspeak.travel.ui.me.fans.model;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.aop.CheckNetwork;
import com.yunspeak.travel.ui.me.fans.LineDecoration;
import com.yunspeak.travel.ui.me.mycollection.collectiondetail.MyCollectionDecoration;
import com.yunspeak.travel.ui.me.othercenter.OtherUserCenterActivity;
import com.yunspeak.travel.utils.ShowImageUtils;

/**
 * Created by wangyang on 2017/3/21.
 * 关注和粉丝通用给实体
 */

public class FansFollowModel {
    private String nick_name;
    private String user_img;
    private String id;
    private String content;

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    @BindingAdapter("fan_img")
    public static void setImg(ImageView img,String url){
        ShowImageUtils.showCircle(img, R.drawable.boy,url,2);
    }
    @CheckNetwork
    public void onClick(View view){
        OtherUserCenterActivity.start(view.getContext(),getId());
    }

}
