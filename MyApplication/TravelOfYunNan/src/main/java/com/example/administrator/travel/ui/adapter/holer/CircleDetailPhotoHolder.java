package com.example.administrator.travel.ui.adapter.holer;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.example.administrator.travel.R;
import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;
/**
 * Created by Administrator on 2016/8/17 0017.
 * 圈子图片
 */
public class CircleDetailPhotoHolder extends BaseHolder<String> {
    ImageOptions imageOptions = new ImageOptions.Builder()
            .setCrop(true)//不使用参数可能造成图片越界
            .setUseMemCache(true)
            .setImageScaleType(ImageView.ScaleType.FIT_XY)
            .setSize(DensityUtil.dip2px(105), DensityUtil.dip2px(93)).build();
    private ImageView mImage;

    public CircleDetailPhotoHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(String datas, Context mContext) {
        x.image().bind(mImage, datas, imageOptions);
    }

    @Override
    public View initRootView(Context mContext) {
        mImage = (ImageView) inflateView(R.layout.item_activity_circle_detail_photo);
        return mImage;
    }
}
