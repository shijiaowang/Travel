package com.yunspeak.travel.ui.home;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yunspeak.travel.R;

/**
 * Created by wangyang on 2017/1/9.
 */

public class UpAppDialog extends Dialog implements View.OnClickListener {

    private TextView tvDownCurrent;
    private ProgressBar pbCurrent;
    private long total;

    public UpAppDialog(Context context) {
        super(context);
    }

    public UpAppDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected UpAppDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    private void init() {
        setContentView(R.layout.dialog_up_progress);
        TextView tvUpTitle = (TextView) findViewById(R.id.tv_up_title);
        tvDownCurrent = (TextView) findViewById(R.id.tv_current);
        TextView tvCancel = (TextView) findViewById(R.id.tv_cancel);
        pbCurrent = (ProgressBar) findViewById(R.id.pb_progress);
        tvUpTitle.setText("更新应用");
        tvDownCurrent.setText("正在下载");
        tvCancel.setOnClickListener(this);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(false);
        Window window = this.getWindow(); //得到对话框
        if (window != null) {
            window.setGravity(Gravity.CENTER);
        }
    }
    public void setTotal(long total){
        if (total!=this.total) {
            this.total = total;
            pbCurrent.setMax((int) total);
        }
    }
    public void upProgress(long current){
        tvDownCurrent.setText("正在下载"+current+"/"+total);
        pbCurrent.setProgress((int) current);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }
}
