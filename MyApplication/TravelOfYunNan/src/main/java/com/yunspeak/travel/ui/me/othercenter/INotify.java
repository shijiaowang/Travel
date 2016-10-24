package com.yunspeak.travel.ui.me.othercenter;

import java.util.List;

/**
 * Created by wangyang on 2016/10/13 0013.
 */

public interface INotify<T> {
   void notifys(List<T> t);
   void notify(T t);
}
