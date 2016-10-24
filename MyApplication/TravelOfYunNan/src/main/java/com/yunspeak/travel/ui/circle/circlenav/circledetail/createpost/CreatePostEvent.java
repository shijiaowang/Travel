package com.yunspeak.travel.ui.circle.circlenav.circledetail.createpost;

import com.yunspeak.travel.event.HttpEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyang on 2016/8/25 0025.
 */
public class CreatePostEvent extends HttpEvent {
    private List<String> mImages=new ArrayList<>();

    public List<String> getmImages() {
        return mImages;
    }

    public void setmImages(List<String> mImages) {
        this.mImages.addAll(mImages);
    }
}
