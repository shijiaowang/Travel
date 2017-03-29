package com.yunspeak.travel.ui.view.progress;

import android.app.ProgressDialog;
import android.content.Context;

import com.yunspeak.travel.R;

/**
 * Created by wangyang on 2017/3/29.
 */

public class ProgressManager {
    private static ProgressManager progressManager=new ProgressManager();
    private ProgressDialog progressDialog;

    private ProgressManager(){

    }
    public static ProgressManager getInstance(){
        return progressManager;
    }
    public void showProgress(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("正在提交...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    public void dismiss(){
        if (progressDialog!=null){
            progressDialog.dismiss();
        }
    }
}
