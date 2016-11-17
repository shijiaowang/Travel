package com.yunspeak.travel.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yunspeak.travel.TravelsApplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UIUtils {


	public static Context getContext() {
		return TravelsApplication.getmContext();
	}

	public static Handler getHandler() {
		return TravelsApplication.getmHandler();
	}

	public static int getMainThreadId() {
		return TravelsApplication.getMainThreadId();
	}

	// /////////////////加载资源文件 ///////////////////////////

	// 获取字符�?
	public static String getString(int id) {
		return getContext().getResources().getString(id);
	}

	// 获取字符串数�?
	public static String[] getStringArray(int id) {
		return getContext().getResources().getStringArray(id);
	}

	// 获取图片
	public static Drawable getDrawable(int id) {
		return getContext().getResources().getDrawable(id);
	}

	// 获取颜色
	public static int getColor(int id) {
		return getContext().getResources().getColor(id);
	}

	//根据id获取颜色的状态�?�择�?
	public static ColorStateList getColorStateList(int id) {
		return getContext().getResources().getColorStateList(id);
	}

	// 获取尺寸
	public static int getDimen(int id) {
		return getContext().getResources().getDimensionPixelSize(id);// 返回具体像素�?
	}

	// /////////////////dip和px转换//////////////////////////

	public static int dip2px(float dip,Context mContext) {
		float density = mContext.getResources().getDisplayMetrics().density;
		return (int) (dip * density + 0.5f);
	}
	public static int px2sp( float pxValue,Context context) {
		final float fontScale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / fontScale + 0.5f);
	}
	public static int sp2px(float spValue,Context context) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	public static float px2dip(int px,Context mContext) {
		float density = mContext.getResources().getDisplayMetrics().density;
		return px / density;
	}

	// /////////////////加载布局文件//////////////////////////
	public static View inflate(int id) {
		return View.inflate(getContext(), id, null);
	}

	// /////////////////判断是否运行在主线程//////////////////////////
	public static boolean isRunOnUIThread() {
		// 获取当前线程id, 如果当前线程id和主线程id相同, 那么当前就是主线�?
		int myTid = android.os.Process.myTid();
		if (myTid == getMainThreadId()) {
			return true;
		}

		return false;
	}

	// 运行在主线程
	public static void runOnUIThread(Runnable r) {
		if (isRunOnUIThread()) {
			// 已经是主线程, 直接运行
			r.run();
		} else {
			// 如果是子线程, 借助handler让其运行在主线程
			getHandler().post(r);
		}
	}
	private static Toast mToast=null;
	//toast工具
	public static void showToast(String text){
		if (mToast==null){
			mToast=Toast.makeText(UIUtils.getContext(),text,Toast.LENGTH_SHORT);
		}
		mToast.setText(text);
		mToast.show();
	}
	/**
	 * 给edittext设置过滤器 过滤emoji
	 *
	 * @param et
	 */
	public static void setEmojiFilter(EditText et) {
		InputFilter emojiFilter = new InputFilter() {
			Pattern pattern = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

			@Override
			public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
				Matcher matcher = pattern.matcher(source);
				if(matcher.find()){
					return "";
				}
				return null;
			}
		};
		et.setFilters(new InputFilter[]{emojiFilter});
	}

	public static String getVersion(Context context)//获取版本号
	{
		try {
			PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return pi.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			return "未知版本";
		}
	}
	public static int getVersionCode(Context context)//获取版本号(内部识别号)
	{
		try {
			PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return pi.versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
}
