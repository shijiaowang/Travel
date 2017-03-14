package com.yunspeak.travel.aop;

import android.app.Activity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Created by wangyang on 2017/3/14.
 * 检查权限
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionRequest {
    String value();//需要申请的权限集合
    String describe();//再次申请该权限时的描述

}
