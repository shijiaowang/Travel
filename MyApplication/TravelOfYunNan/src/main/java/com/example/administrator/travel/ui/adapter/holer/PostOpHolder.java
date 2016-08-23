package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Fan;
import com.example.administrator.travel.bean.PostDetail;
import com.example.administrator.travel.ui.adapter.PostImageAdapter;
import com.example.administrator.travel.ui.view.FontsIconTextView;
import com.example.administrator.travel.ui.view.ToShowAllListView;
import com.example.administrator.travel.utils.FormatDateUtils;
import com.example.administrator.travel.utils.GlobalUtils;
import com.example.administrator.travel.utils.StringUtils;

import org.xutils.view.annotation.ViewInject;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class PostOpHolder extends BaseHolder<Object> {
    @ViewInject(R.id.tv_icon)
    private ImageView mTvUserIcon;
    @ViewInject(R.id.tv_nick_name)
    private TextView mTvNickName;
    @ViewInject(R.id.tv_time)
    private TextView mTvTime;
    @ViewInject(R.id.tv_type)
    private TextView mTvType;
    @ViewInject(R.id.tv_content)
    private TextView mTvContent;
    @ViewInject(R.id.lv_post_image)
    private ToShowAllListView mLvPostImage;
    @ViewInject(R.id.fitv_like)
    private FontsIconTextView mFitvLike;
    @ViewInject(R.id.tv_like_user)
    private TextView mTvLikeUser;

    private boolean isFirst=true;
    private PostDetail.DataBean.ForumBean forum;

    public PostOpHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Object datas, Context mContext) {
        if (datas instanceof PostDetail.DataBean.ForumBean){
            forum = (PostDetail.DataBean.ForumBean) datas;
        }else {
            return;
        }
        if (isFirst){
            isFirst=false;
            mTvNickName.setText(forum.getNick_name());
            mTvTime.setText(FormatDateUtils.FormatLongTime("YYYY-MM-dd HH:mm", forum.getTime()));
            mTvContent.setText(forum.getContent());
        }
        List<PostDetail.DataBean.ForumBean.LikeBean> like = forum.getLike();
        boolean isLike=false;
        StringBuffer likeName=new StringBuffer();
        for (PostDetail.DataBean.ForumBean.LikeBean bean:like){
            if (!StringUtils.isEmpty(bean.getNick_name())){
                likeName.append(bean.getNick_name()+"、");
            }
            if (bean.getId().equals(GlobalUtils.getUserInfo().getId())){
             isLike=true;
            }
        }
        String likeUser = likeName.toString().substring(0, likeName.length() - 1);//去掉最后的点
        mTvLikeUser.setText(likeUser);
        mFitvLike.setTextColor(isLike?mContext.getResources().getColor(R.color.otherFf7f6c):mContext.getResources().getColor(R.color.color969696));
        if (!StringUtils.isEmpty(forum.getForum_img())){
            String[] split = forum.getForum_img().split(",");
            List<String> list = Arrays.asList(split);
            mLvPostImage.setAdapter(new PostImageAdapter(mContext,list));
        }
    }


    @Override
    public View initRootView(Context mContext) {
        View inflate = inflateView(R.layout.item_activity_post_op);
        return inflate;
    }
}
