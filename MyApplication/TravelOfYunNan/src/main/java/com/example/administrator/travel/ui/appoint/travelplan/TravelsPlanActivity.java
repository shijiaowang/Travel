package com.example.administrator.travel.ui.appoint.travelplan;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.example.administrator.travel.R;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.activity.BarBaseActivity;
import com.example.administrator.travel.ui.appoint.personnelequipment.PersonnelEquipmentActivity;
import com.example.administrator.travel.ui.appoint.lineplan.LineBean;
import com.example.administrator.travel.ui.appoint.lineplan.LinePlanActivity;
import com.example.administrator.travel.ui.appoint.popwindow.AppointSpinnerPop;
import com.example.administrator.travel.ui.appoint.popwindow.SpinnerBean;
import com.example.administrator.travel.ui.view.GradientTextView;
import com.example.administrator.travel.utils.ActivityUtils;
import com.example.administrator.travel.utils.BitmapUtils;
import com.example.administrator.travel.utils.CalendarUtils;
import com.example.administrator.travel.utils.GlobalUtils;
import com.example.administrator.travel.utils.IOUtils;
import com.example.administrator.travel.utils.JsonUtils;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.model.AspectRatio;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;
import org.xutils.common.util.DensityUtil;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/8/30 0030.
 * 旅游计划
 */
public class TravelsPlanActivity extends BarBaseActivity implements View.OnClickListener {
    @ViewInject(R.id.bt_select_line)
    private TextView mBtSelectLine;
    @ViewInject(R.id.tv_start)
    private TextView mTvStart;
    @ViewInject(R.id.tv_end)
    private TextView mTvEnd;
    @ViewInject(R.id.tv_end_time)
    private TextView mTvEndTime;
    @ViewInject(R.id.bt_next)
    private Button mBtNext;
    @ViewInject(R.id.tv_start_time)
    private TextView mTvStartTime;
    @ViewInject(R.id.tv_how_day)
    private TextView mTvHowDay;
    @ViewInject(R.id.et_remark)
    private EditText mEtRemark;
    @ViewInject(R.id.tv_start_morning)
    private GradientTextView mTvStartMorning;
    @ViewInject(R.id.tv_start_night)
    private GradientTextView mTvStartNight;
    @ViewInject(R.id.tv_end_morning)
    private GradientTextView mTvEndMorning;
    @ViewInject(R.id.tv_end_night)
    private GradientTextView mTvEndNight;
    @ViewInject(R.id.rl_start_morning)
    private RelativeLayout mRlStartMorning;
    @ViewInject(R.id.rl_start_night)
    private RelativeLayout mRlStartNight;
    @ViewInject(R.id.rl_end_morning)
    private RelativeLayout mRlEndMorning;
    @ViewInject(R.id.rl_end_night)
    private RelativeLayout mRlEndNight;
    @ViewInject(R.id.tv_traffic)
    private TextView mTvTraffic;
    @ViewInject(R.id.rl_traffic)
    private RelativeLayout mRlTraffic;
    @ViewInject(R.id.tv_icon)
    private TextView mTvIcon;
    @ViewInject(R.id.rl_icon)
    private RelativeLayout mRlIcon;
    @ViewInject(R.id.iv_icon)
    private ImageView mIvIcon;
    @ViewInject(R.id.tv_delete)
    private TextView mTvDelete;
    private TextView mTvRightNext;
    private TimePickerView pvTime;
    private Date startDate = new Date();
    private Date endDate = new Date();
    private String trafficType = "1";//交通工具
    private int dayOfYear;
    private int year;
    private Date endLine;
    private Date startLine;
    private List<SpinnerBean> traffics;
    private static final int REQUEST_SELECT_PICTURE = 0x01;
    private static final String IMAGE_NAME = "CropImage";
    private String filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.getInstance().addActivity(this);
        registerEventBus(this);

    }

    private void initTrafficeData() {
        traffics = new ArrayList<>();
        traffics.add(new SpinnerBean("自驾游","1", TRAFFIC_TYPE));
        traffics.add(new SpinnerBean("火车","2", TRAFFIC_TYPE));
        traffics.add(new SpinnerBean("汽车","3", TRAFFIC_TYPE));
        mRlTraffic.setOnClickListener(this);
    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_travels_plan;
    }

    @Override
    protected void initEvent() {

        mTvRightNext = getmTvRightIcon();
        mTvRightNext.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        mTvRightNext.setText(R.string.next);
        init();
        initTrafficeData();
        mTvRightNext.setOnClickListener(this);
        mBtSelectLine.setOnClickListener(this);
        mTvStart.setOnClickListener(this);
        mTvEnd.setOnClickListener(this);
        mBtNext.setOnClickListener(this);
        mRlStartMorning.setOnClickListener(this);
        mRlEndMorning.setOnClickListener(this);
        mRlStartNight.setOnClickListener(this);
        mRlEndNight.setOnClickListener(this);
        mTvIcon.setOnClickListener(this);
    }


    @Override
    protected void initViewData() {

    }

    @Override
    protected String setTitleName() {
        return "旅行计划";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_select_line:
                if (checkTimeAgain()) {
                    calculationDay();
                }
                break;
            case R.id.tv_start:
                showTime(mTvStartTime);
                break;
            case R.id.tv_end:
                showTime(mTvEndTime);
                break;
            case R.id.tv_right_icon:
            case R.id.bt_next:
                if (checkTimeAgain()) {
                    addJson();
                }
                break;
            case R.id.rl_start_morning:
                mTvStartMorning.setChecked(true);
                mTvStartNight.setChecked(false);
                break;
            case R.id.rl_start_night:
                mTvStartMorning.setChecked(false);
                mTvStartNight.setChecked(true);
                break;
            case R.id.rl_end_morning:
                mTvEndMorning.setChecked(true);
                mTvEndNight.setChecked(false);
                break;
            case R.id.rl_end_night:
                mTvEndMorning.setChecked(false);
                mTvEndNight.setChecked(true);
                break;
            case R.id.rl_traffic:
                AppointSpinnerPop.showSpinnerPop(this,mRlTraffic,traffics);
               break;
            case R.id.tv_icon:
                showPictureCutPop(this,mBtNext);
                break;
        }
    }


    /**
     * 计算日期，启动路线规划页面
     */
    private void calculationDay() {
        try {
            if (!(endLine != null && startLine != null && endLine == endDate && startLine == startDate)) {
                List<LineBean> lineBeans = new ArrayList<>();
                String howDay = CalendarUtils.getHowDay(startDate.getTime() + "", endDate.getTime() + "");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startDate);
                int countDay = Integer.parseInt(howDay);
                lineBeans.add(new LineBean(""));
                for (int i = 0; i <= countDay + 1; i++) {//这里判断条件是因为添加了集合地和解散地
                    if (i == countDay) {
                        lineBeans.add(new LineBean(""));
                        break;
                    }
                    int month = calendar.get(Calendar.MONTH) + 1;
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    LineBean lineBean = new LineBean(month + "月" + day + "日");
                    lineBean.setDate(calendar.getTime().getTime() + "");
                    lineBeans.add(lineBean);

                    calendar.add(Calendar.DATE, 1);
                }
                if (GlobalValue.mLineBeans == null) {
                    GlobalValue.mLineBeans = lineBeans;
                } else {
                    for (int i = 0; i < lineBeans.size(); i++) {
                        for (int j = 0; j < GlobalValue.mLineBeans.size(); j++) {
                            if (lineBeans.get(i).getTime().equals(GlobalValue.mLineBeans.get(j).getTime())) {
                                lineBeans.add(i, GlobalValue.mLineBeans.get(j));
                                lineBeans.remove(i + 1);
                                break;
                            }
                        }
                    }
                    GlobalValue.mLineBeans = lineBeans;
                    lineBeans = null;
                }
                endLine = endDate;
                startLine = startDate;
            }
            Intent intent = new Intent(this, LinePlanActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 添加json
     */
    private void addJson() {
        try {
            JSONObject basecJsonObject = JsonUtils.getBasecJsonObject();
            JsonUtils.putString(IVariable.USER_ID, GlobalUtils.getUserInfo().getId(), basecJsonObject);
            JsonUtils.putString(IVariable.START_TIME, startDate.getTime()/1000 + "", basecJsonObject);
            JsonUtils.putString(IVariable.END_TIME, endDate.getTime()/1000 + "", basecJsonObject);
            JsonUtils.putString(IVariable.TRAFFIC, trafficType, basecJsonObject);
            basecJsonObject.put(IVariable.TRAFFIC_TEXT, getTrafficText());
            LogUtils.e(basecJsonObject.toString());
            startActivity(new Intent(this, PersonnelEquipmentActivity.class));
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showToast("您尚有未填的项目");
        }
    }

    /**
     * 获取留言
     *
     * @return
     */
    private String getTrafficText() {
        return getString(mEtRemark);
    }

    @Subscribe
    public void onEvent(SpinnerBean spinnerBean){
        if (spinnerBean.getSpinnerId()== TRAFFIC_TYPE){
            trafficType =spinnerBean.getId();
            mTvTraffic.setText(spinnerBean.getType());
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlobalValue.mSelectSpot = null;
        GlobalValue.mLineBeans = null;
        GlobalValue.mPropSelects=null;
        JsonUtils.reset();//释放json
        unregisterEventBus(this);
    }

    //公用初始化
    public void init() {
        Calendar currentDay = Calendar.getInstance();
        currentDay.setTime(new Date());
        dayOfYear = currentDay.get(Calendar.DAY_OF_YEAR);
        year = currentDay.get(Calendar.YEAR);
        mTvStartTime.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        mTvEndTime.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
    }

    private void showTime(final TextView currentText) {
        if (pvTime == null) {
            pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
            //控制时间范围
            Calendar calendar = Calendar.getInstance();
            pvTime.setRange(calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR) + 1);//要在setTime 之前才有效果
            pvTime.setTime(new Date());
            pvTime.setCyclic(false);
            pvTime.setCancelable(true);
            pvTime.setTitle(currentText == mTvStartTime ? "开始时间" : "结束时间");
        } else {
            pvTime.setTitle(currentText == mTvStartTime ? "开始时间" : "结束时间");
        }
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                checkTime(currentText, date);
            }


        });
        hideSoftWore(mEtRemark);
        pvTime.show();

    }

    /**
     * 检查日期是否合法
     *
     * @param currentText
     * @param date
     */
    public void checkTime(TextView currentText, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (calendar.get(Calendar.DAY_OF_YEAR) < dayOfYear && calendar.get(Calendar.YEAR) == year) {
            ToastUtils.showToast("对不起，您所选的时间已过期");
            return;
        }

        currentText.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));
        if (currentText == mTvStartTime) {
            startDate = date;
        } else {
            endDate = date;
        }
        if (mTvHowDay != null) {
            mTvHowDay.setText("共计" + CalendarUtils.getHowDayHowNight(startDate.getTime() + "", endDate.getTime() + ""));
        }

    }

    private boolean checkTimeAgain() {
        if (endDate.getTime() < startDate.getTime()) {
            ToastUtils.showToast("对不起，结束时间不能小于出发时间");
            return false;
        }

        String howDay = CalendarUtils.getHowDay(startDate.getTime() + "", endDate.getTime() + "");
        if (Integer.parseInt(howDay) > 30) {
            ToastUtils.showToast("对不起，计划最长时间为30天，若有特殊需要请联系客服。");
            return false;
        }
        return true;
    }
    public  void showPictureCutPop(final Activity activity, View view) {
        // 获取弹出视图对象
        View viewPopup = View.inflate(activity, R.layout.pop_cut_icon, null);
        // 创建 弹出窗口
        final PopupWindow window = new PopupWindow(viewPopup, DensityUtil.getScreenWidth(), DensityUtil.dip2px(151));
        final WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        viewPopup.findViewById(R.id.tv_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        viewPopup.findViewById(R.id.tv_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGallery();
            }
        });
        viewPopup.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        lp.alpha = 0.7f;
        activity.getWindow().setAttributes(lp);
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lp.alpha = 1f;
                activity.getWindow().setAttributes(lp);
            }
        });
        // 响应 视图外的地方 点击关闭当前
        window.setOutsideTouchable(true);
        // 响应返回键的关闭
        window.setFocusable(true);
        window.setBackgroundDrawable(new ColorDrawable(activity.getResources().getColor(android.R.color.transparent)));

        // 获取控件的坐标 x y
        window.showAtLocation(view, Gravity.TOP,0,DensityUtil.getScreenHeight()-DensityUtil.dip2px(151));
    }
    private void pickFromGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.permission_read_storage_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intent, getString(R.string.label_select_picture)), REQUEST_SELECT_PICTURE);
        }
    }
    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_STORAGE_READ_ACCESS_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickFromGallery();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SELECT_PICTURE) {
                final Uri selectedUri = data.getData();
                if (selectedUri != null) {
                    startCropActivity(data.getData());
                } else {
                    Toast.makeText(TravelsPlanActivity.this, R.string.toast_cannot_retrieve_selected_image, Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == UCrop.REQUEST_CROP) {
                handleCropResult(data);
            }
        }
        if (resultCode == UCrop.RESULT_ERROR) {
            handleCropError(data);
        }
    }
    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Toast.makeText(TravelsPlanActivity.this, cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(TravelsPlanActivity.this, R.string.toast_unexpected_error, Toast.LENGTH_SHORT).show();
        }
    }

    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            //ResultActivity.startWithUri(TravelsPlanActivity.this, resultUri);
            mRlIcon.setVisibility(View.VISIBLE);
            mTvIcon.setVisibility(View.GONE);
            Bitmap bitmap = null;
            try {
                bitmap = BitmapUtils.getBitmapFormUri(this,resultUri);
                if (bitmap==null)return;
                //将图片保存在本地
                mIvIcon.setImageBitmap(bitmap);

                mTvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mRlIcon.setVisibility(View.GONE);
                        mTvIcon.setVisibility(View.VISIBLE);
                    }
                });
                saveCroppedImage(bitmap);//保存图片到本地

            } catch (Exception e) {
                e.printStackTrace();
                ToastUtils.showToast("未解析到图片");
            }


        } else {
            Toast.makeText(TravelsPlanActivity.this, R.string.toast_cannot_retrieve_selected_image, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 将截图存在本地
     * @param b
     */
    private void saveCroppedImage(final Bitmap b) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    getString(R.string.permission_write_storage_rationale),
                    REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
        } else {
            if (b != null) {
                try {
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        final String downloadsDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                        final String saveName=System.currentTimeMillis()+"icon.jpg";
                        x.task().run(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    saveMyBitmap(b, downloadsDirectoryPath, saveName);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });


                    }else{
                        ToastUtils.showToast("SD卡未挂载！");
                        filename=null;
                    }
                } catch (Exception e) {
                    Toast.makeText(TravelsPlanActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(TravelsPlanActivity.this, getString(R.string.toast_unexpected_error), Toast.LENGTH_SHORT).show();
            }
        }
    }
    public boolean saveMyBitmap(Bitmap bmp,String path, String bitName) throws IOException {
        File f = new File(path);
        if (!f.exists()){
            f.mkdirs();
        }
        String url=path+"/"+bitName;
        File file=new File(url);
        boolean flag = false;
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            flag = true;
            filename=url;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (fOut!=null) {
                fOut.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        IOUtils.close(fOut);
        return flag;
    }
    private void startCropActivity(@NonNull Uri uri) {
        String destinationFileName = IMAGE_NAME+".jpg";
        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), destinationFileName)));
        uCrop = advancedConfig(uCrop);
        uCrop.start(TravelsPlanActivity.this);
    }
    /**
     * Sometimes you want to adjust more options, it's done via {@link com.yalantis.ucrop.UCrop.Options} class.
     *
     * @param uCrop - ucrop builder instance
     * @return - ucrop builder instance
     */
    private UCrop advancedConfig(@NonNull UCrop uCrop) {
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.useSourceImageAspectRatio();
        options.setShowCropGrid(false);
        options.setShowCropFrame(false);
        options.setActiveWidgetColor(getResources().getColor(R.color.otherTitleBg));
        options.setToolbarColor(getResources().getColor(R.color.otherTitleBg));
        options.setStatusBarColor(getResources().getColor(R.color.otherTitleBg));
         options.setAspectRatioOptions(0,new AspectRatio("1",1,1));

        /*
        If you want to configure how gestures work for all UCropActivity tabs

        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        * */

        /*
        This sets max size for bitmap that will be decoded from source Uri.
        More size - more memory allocation, default implementation uses screen diagonal.

        options.setMaxBitmapSize(640);
        * */


       /*

        Tune everything (ﾉ◕ヮ◕)ﾉ*:･ﾟ✧

        options.setMaxScaleMultiplier(5);
        options.setImageToCropBoundsAnimDuration(666);
        options.setDimmedLayerColor(Color.CYAN);
        options.setCircleDimmedLayer(true);
        options.setShowCropFrame(false);
        options.setCropGridStrokeWidth(20);
        options.setCropGridColor(Color.GREEN);
        options.setCropGridColumnCount(2);
        options.setCropGridRowCount(1);

        // Color palette
        options.setToolbarColor(ContextCompat.getColor(this, R.color.your_color_res));
        options.setStatusBarColor(ContextCompat.getColor(this, R.color.your_color_res));
        options.setActiveWidgetColor(ContextCompat.getColor(this, R.color.your_color_res));
		options.setToolbarWidgetColor(ContextCompat.getColor(this, R.color.your_color_res));

		// Aspect ratio options
        options.setAspectRatioOptions(1,
            new AspectRatio("WOW", 1, 2),
            new AspectRatio("MUCH", 3, 4),
            new AspectRatio("RATIO", CropImageView.DEFAULT_ASPECT_RATIO, CropImageView.DEFAULT_ASPECT_RATIO),
            new AspectRatio("SO", 16, 9),
            new AspectRatio("ASPECT", 1, 1));

       */

        return uCrop.withOptions(options);
    }
}
