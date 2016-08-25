package com.example.administrator.travel.event;

import java.util.List;

/**
 * Created by Administrator on 2016/8/25 0025.
 */
public class CreatePostEvent{
    private List<String> mImages;

    public List<String> getmImages() {
        return mImages;
    }

    public void setmImages(List<String> mImages) {
        this.mImages = mImages;
    }
}
