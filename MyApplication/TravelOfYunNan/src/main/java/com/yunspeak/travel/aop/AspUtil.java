package com.yunspeak.travel.aop;

import com.yunspeak.travel.utils.NetworkUtils;
import com.yunspeak.travel.utils.ToastUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wangyang on 2017/2/8.
 * aop工具类
 */
@Aspect
public class AspUtil {
    //拦截com.yunspeak.travel.ui包下所有带有@CheckAnnotation注解的方法
    final String checkRule="execution(@CheckNetwork * com.yunspeak.travel.ui..*.*(..))";
    @Pointcut(checkRule)
    public void checkNetworkPoint(){
    }

    /**
     * 如果有网络连接便执行方法，否则弹出提示
     * @param proceedingJoinPoint
     * @throws Throwable
     */
    @Around("checkNetworkPoint()")
    public void checkNetworkBefore(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        if (NetworkUtils.isNetworkConnected()){
            proceedingJoinPoint.proceed();
        }else {
            ToastUtils.showToast("当前网络不可用,请检查网络");
        }

    }
}
