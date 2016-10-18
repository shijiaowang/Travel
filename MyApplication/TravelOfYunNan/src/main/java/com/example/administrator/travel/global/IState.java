package com.example.administrator.travel.global;

/**
 * Created by wangyang on 2016/10/12 0012.
 */

public interface IState {
     int TYPE_LIKE_DISCUSS = 0;//点赞请求
     int TYPE_LOAD = 1;//普通读取请求
     int TYPE_DISCUSS = 2;//留言
     int TYPE_REFRESH = 3;//刷新
     int TYPE_DELETE = 4;//删除
     int TYPE_SEARCH = 5;//搜索
     int TYPE_SAVE = 6;//保存
     int TYPE_UPDATE = 7;//更新
     int TYPE_VER_MSG = 8;//发送短信验证
     int TYPE_LIKE = 9;//点赞
     int TYPE_FOLLOW_CIRCLE = 10;//关注圈子
     int RESULT_CODE = 11;
     int TYPE_UP_FILE = 12;//上传文件
     int TYPE_CHANGE = 13;//上传文件
     int TYPE_COLLECTION = 14;//收藏
     int TYPE_CANCEL_COLLECTION = 15;//取消收藏
    String isTrue="1";
    String isFalse="0";
    int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;
     int TYPE_OTHER = 103;//其他
     int REQ_CODE = 104;
}
