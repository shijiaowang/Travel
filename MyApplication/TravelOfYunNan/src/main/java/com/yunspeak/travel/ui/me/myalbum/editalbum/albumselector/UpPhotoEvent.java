package com.yunspeak.travel.ui.me.myalbum.editalbum.albumselector;

import com.yunspeak.travel.global.TravelsObject;

import java.util.List;

/**
 * Created by wangyang on 2016/10/8 0008.
 * 上传图片
 */

public class UpPhotoEvent extends TravelsObject {
    private List<String> list;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
