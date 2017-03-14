package com.yunspeak.travel.aop;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import com.yunspeak.travel.R;
import com.yunspeak.travel.utils.UIUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import java.lang.reflect.Method;

/**
 * Created by wangyang on 2017/2/8.
 * aop工具类
 */
@Aspect
public class AspPermissionUtil {
    //拦截com.yunspeak.travel.ui包下所有带有@CheckAnnotation注解的方法
    private final String checkRule = "execution(@PermissionRequest * com.yunspeak.travel.ui..*.*(..))";

    @Pointcut(checkRule)
    public void checkPermissionPoint() {
    }

    /**
     * 如果有网络连接便执行方法，否则弹出提示
     *
     * @param proceedingJoinPoint
     * @throws Throwable
     */
    @Around("checkPermissionPoint()")
    public void checkPermissionBefore(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Activity activity = (Activity) proceedingJoinPoint.getTarget();
        PermissionRequest permissionRequest = getMethodAnnotation(proceedingJoinPoint);
        String value = permissionRequest.value();//权限字符串
        String describe = permissionRequest.describe();//描述
        if (!TextUtils.isEmpty(value) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ActivityCompat.checkSelfPermission(UIUtils.getContext(), value)
                != PackageManager.PERMISSION_GRANTED) {//未授权
            //之前被拒绝过
            requestPermission(activity,value,describe,1);

        } else {
            proceedingJoinPoint.proceed();
        }


    }

    protected void requestPermission(final Activity activity, final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            showAlertDialog(activity.getString(R.string.permission_title_rationale), rationale,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(activity,
                                    new String[]{permission}, requestCode);
                        }
                    }, activity.getString(R.string.label_ok), null, activity.getString(R.string.label_cancel), activity);
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
        }
    }

    protected void showAlertDialog(@Nullable String title, @Nullable String message,
                                   @Nullable DialogInterface.OnClickListener onPositiveButtonClickListener,
                                   @NonNull String positiveText,
                                   @Nullable DialogInterface.OnClickListener onNegativeButtonClickListener,
                                   @NonNull String negativeText,
                                   @NonNull Context context
    ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveText, onPositiveButtonClickListener);
        builder.setNegativeButton(negativeText, onNegativeButtonClickListener);
        builder.show();
    }


    private PermissionRequest getMethodAnnotation(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        return method.getAnnotation(PermissionRequest.class);
    }
}
