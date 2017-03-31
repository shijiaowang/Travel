package com.yunspeak.travel.ui.home.homesearch.model;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.download.HttpClient;
import com.yunspeak.travel.download.ICallBack;
import com.yunspeak.travel.download.IRequestUrl;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.CircleDetailActivity;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.post.PostActivity;
import com.yunspeak.travel.ui.find.active.activedetail.ActivateDetailActivity;
import com.yunspeak.travel.ui.find.findcommon.deliciousdetail.DeliciousDetailActivity;
import com.yunspeak.travel.ui.find.findcommon.destinationdetail.DestinationDetailActivity;
import com.yunspeak.travel.ui.find.travels.travelsdetail.TravelsDetailActivity;
import com.yunspeak.travel.ui.home.homesearch.HomeSearchActivity;
import com.yunspeak.travel.ui.me.othercenter.OtherUserCenterActivity;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ShowImageUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.UIUtils;
import com.yunspeak.travel.BR;

import java.util.Map;

/**
 * Created by wangyang on 2017/3/31.
 */

public class SearchModel extends BaseObservable{
        private String id;
        private String title;
        private String logo_img;
        private String content;
        private String cname;
        private String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLogo_img() {
            return logo_img;
        }

        public void setLogo_img(String logo_img) {
            this.logo_img = logo_img;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
        @Bindable
        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
            notifyPropertyChanged(BR.cname);
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
       @BindingAdapter({"icon_url","icon_type"})
       public static void showIconByType(ImageView imageView,String url,String type){
            switch (type){
                case HomeSearchActivity.SEARCH_USER:
                case HomeSearchActivity.SEARCH_CIRCLE:
                    ShowImageUtils.showCircle(imageView,url);
                    break;
                case HomeSearchActivity.SEARCH_DESTINATION:
                case HomeSearchActivity.SEARCH_CONTENT:
                    ShowImageUtils.showRound(imageView,url);
                    break;
            }
       }
    public void onClick(View view,String type){
        Context context = view.getContext();
        switch (type){
            case HomeSearchActivity.SEARCH_USER:
                OtherUserCenterActivity.start(context,getId());
                break;
            case HomeSearchActivity.SEARCH_CIRCLE:
                if (getType().equals("1")){
                    CircleDetailActivity.start(context,getId());
                }else {
                    PostActivity.start(context,getId());
                }
                break;
            case HomeSearchActivity.SEARCH_DESTINATION:
                DestinationDetailActivity.start(context,getId(),getTitle());
                break;
            case HomeSearchActivity.SEARCH_CONTENT:
                if (getType().equals("1")){
                    TravelsDetailActivity.start(context,getId(),getTitle());
                }else if (getType().equals("2")){
                    DeliciousDetailActivity.start(context,getId(),getTitle());
                }else {
                    ActivateDetailActivity.start(context,getId());
                }
                break;
        }
    }
    public void onFollowClick(final View view, String type){
        if (!type.equals(HomeSearchActivity.SEARCH_USER))return;
        final String clickType=getCname().equals("1")?"2":"1";
        Map<String, String> followMap = MapUtils.Build().addKey().addUserId().add("u_id",getId()).addType(clickType).end();
        HttpClient.getInstance().postDataOneBack(IRequestUrl.FOLLOW_OR_CANCEL_FOLLOW, followMap, new ICallBack() {
            @Override
            public void back(boolean isSuccess, String message) {
                if (isSuccess){
                    String newCname=cname.equals("1") ? "0" : "1";
                    setCname(newCname);
                }else {
                    ToastUtils.showNetToast("关注失败");
                }
            }
        },view.getContext(),false);
    }

    //根据不同的页面展示不同字
    @BindingAdapter({"showRightText","showRightTextType"})
    public static void showRightText(TextView textView,String cname,String type){
        Context context = textView.getContext();
        int unFollowColor = UIUtils.getColor(R.color.otherFf7f6c);
        int followColor = UIUtils.getColor(R.color.colorFAFAFA);
        switch (type){
            case HomeSearchActivity.SEARCH_USER:
                if (cname.equals("1")){
                    textView.setText(R.string.activity_circle_detail_followed);
                    textView.setTextColor(followColor);
                    textView.setBackgroundResource(R.drawable.activity_home_search_followed_bg);
                }else {
                    textView.setText(R.string.activity_circle_detail_follow);
                    textView.setTextColor(unFollowColor);
                    textView.setBackgroundResource(R.drawable.activity_home_search_follow_bg);
                }
                break;
            case HomeSearchActivity.SEARCH_CIRCLE:
            case HomeSearchActivity.SEARCH_CONTENT:
                textView.setText("#"+cname+"#");
                textView.setTextColor(unFollowColor);
                break;
        }
    }
}
