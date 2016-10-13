package com.example.administrator.travel.ui.circle.circlenav.circledetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import org.xutils.x;

import java.util.List;

/**
 * Created by wangyang on 2016/8/17 0017.
 * 圈子页面的照片
 */
public class CircleDetailPhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
      private List<String> mDatas;
      private Context mContext;

    public CircleDetailPhotoAdapter(List<String> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
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
      FrescoUtils.displayNormal(circleDetailPhotoHolder.mIvImage, (String) circleDetailPhotoHolder.mIvImage.getTag());
        //x.image().bind( circleDetailPhotoHolder.mIvImage,mDatas.get(position));
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
