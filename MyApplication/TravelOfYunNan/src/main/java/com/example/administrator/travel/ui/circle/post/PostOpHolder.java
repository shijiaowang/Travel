package com.example.administrator.travel.ui.circle.post;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.circle.post.PostDetail;
import com.example.administrator.travel.ui.adapter.PostImageAdapter;
import com.example.administrator.travel.ui.view.FontsIconTextView;
import com.example.administrator.travel.ui.view.ToShowAllListView;
import com.example.administrator.travel.utils.FormatDateUtils;
import com.example.administrator.travel.utils.GlobalUtils;
import com.example.administrator.travel.utils.StringUtils;

import org.xutils.x;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class PostOpHolder extends BaseHolder<Object> {
    @BindView(R.id.iv_icon) ImageView mIvUserIcon;
    @BindView(R.id.tv_nick_name) TextView mTvNickName;
    @BindView(R.id.tv_time) TextView mTvTime;
    @BindView(R.id.tv_type)TextView mTvType;
    @BindView(R.id.tv_content) TextView mTvContent;
    @BindView(R.id.lv_post_image) ToShowAllListView mLvPostImage;
    @BindView(R.id.fitv_like) FontsIconTextView mFitvLike;
    @BindView(R.id.tv_like_user) TextView mTvLikeUser;
    @BindView(R.id.tv_discuss_count) TextView mTvDiscussCount;

    private boolean isFirst = true;
    private PostDetail.DataBean.ForumBean forum;

    public PostOpHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Object datas, Context mContext, int position) {
        if (datas instanceof PostDetail.DataBean.ForumBean) {
            forum = (PostDetail.DataBean.ForumBean) datas;
        } else {
            return;
        }
        if (isFirst) {
            isFirst = false;
            mTvNickName.setText(forum.getNick_name());
            mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy-MM-dd HH:mm", forum.getTime()));
            mTvContent.setText(forum.getContent());
            x.image().bind(mIvUserIcon,forum.getUser_img());
        }
        List<PostDetail.DataBean.ForumBean.LikeBean> like = forum.getLike();

        boolean isLike = false;
        StringBuffer likeName = new StringBuffer();
        if (like != null && like.size() != 0) {
            for (PostDetail.DataBean.ForumBean.LikeBean bean : like) {
                if (!StringUtils.isEmpty(bean.getNick_name())) {
                    likeName.append(bean.getNick_name() + "、");
                }
                if (bean.getId().equals(GlobalUtils.getUserInfo().getId())) {
                    isLike = true;
                }
            }
            if (!StringUtils.isEmpty(likeName.toString())) {
                String likeUser = likeName.toString().substring(0, likeName.length() - 1);//去掉最后的点
                mTvLikeUser.setText(likeUser);
            }
        }
        mTvDiscussCount.setText("评论("+forum.getReplay_count()+")");
        mFitvLike.setTextColor(isLike ? mContext.getResources().getColor(R.color.otherFf7f6c) : mContext.getResources().getColor(R.color.color969696));
        if (!StringUtils.isEmpty(forum.getForum_img())) {
            String[] split = forum.getForum_img().split(",");
            List<String> list = Arrays.asList(split);
            mLvPostImage.setAdapter(new PostImageAdapter(mContext, list));
        }
    }


    @Override
    public View initRootView(Context mContext) {
        View inflate = inflateView(R.layout.item_activity_post_op);
        return inflate;
    }
}
