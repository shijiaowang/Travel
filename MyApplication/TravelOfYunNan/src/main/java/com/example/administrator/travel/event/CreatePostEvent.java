package com.example.administrator.travel.event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/25 0025.
 */
public class CreatePostEvent extends HttpEvent{
    private List<String> mImages=new ArrayList<>();

    public List<String> getmImages() {
        return mImages;
    }

    public void setmImages(List<String> mImages) {
        this.mImages.addAll(mImages);
    }
}
