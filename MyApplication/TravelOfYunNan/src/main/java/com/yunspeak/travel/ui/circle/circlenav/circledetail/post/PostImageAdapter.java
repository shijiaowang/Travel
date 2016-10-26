package com.yunspeak.travel.ui.circle.circlenav.circledetail.post;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IState;
import com.yunspeak.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by wangyang on 2016/8/23 0023.
 */
public class PostImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mDatas;

    public PostImageAdapter(List<String> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_post_image, parent, false);
        return new PostImageHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mDatas.get(position);
                PostEvent postEvent=new PostEvent();
                postEvent.setIsSuccess(true);
                postEvent.setUrl(url);
                postEvent.setType(IState.TYPE_OTHER);
                EventBus.getDefault().post(postEvent);
            }
        });
        PostImageHolder postImageHolder = (PostImageHolder) holder;
        FrescoUtils.displayNormal(postImageHolder.mIvImage,mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
   class PostImageHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView mIvImage;
         PostImageHolder(View itemView) {
            super(itemView);
          mIvImage= (SimpleDraweeView) itemView;
        }
    }
}
