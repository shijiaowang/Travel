package com.yunspeak.travel.ui.circle.circlenav.circledetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.post.PostActivity;
import com.yunspeak.travel.utils.FrescoUtils;

import java.util.List;

/**
 * Created by wangyang on 2016/8/17 0017.
 * 圈子页面的照片
 */
public class CircleDetailPhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
      private List<String> mDatas;
      private Context mContext;
    private String cId;
    private String id;

    public CircleDetailPhotoAdapter(List<String> mDatas, Context mContext, String id,String cId) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        this.cId = cId;
        this.id = id;
    }
    public void setList(List<String> mDatas){
        this.mDatas=mDatas;
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_activity_circle_detail_photo, parent, false);
        return new CircleDetailPhotoHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final CircleDetailPhotoHolder circleDetailPhotoHolder = (CircleDetailPhotoHolder) holder;
        circleDetailPhotoHolder.mIvImage.setTag(mDatas.get(position));
        circleDetailPhotoHolder.mIvImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostActivity.start(mContext,id);
            }
        });
         FrescoUtils.displayNormal(circleDetailPhotoHolder.mIvImage, (String) circleDetailPhotoHolder.mIvImage.getTag(),300,200,R.drawable.normal_2_1);

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
   class CircleDetailPhotoHolder extends RecyclerView.ViewHolder{
       SimpleDraweeView mIvImage;

       public CircleDetailPhotoHolder(View itemView) {
           super(itemView);
           mIvImage= (SimpleDraweeView) itemView;
       }
   }
}
