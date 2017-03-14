package com.yunspeak.travel.download;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.util.PathUtil;
import com.yalantis.ucrop.UCrop;
import com.yunspeak.travel.R;
import com.yunspeak.travel.utils.BitmapUtils;
import com.yunspeak.travel.utils.IOUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.UIUtils;

import org.xutils.common.util.DensityUtil;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by wangyang on 2017/3/14.
 * 上传图片  拍照 或者 从本地打开
 */

public abstract class UpPhotoManager {
    protected static final int REQUEST_SELECT_PICTURE = 0x01;
    private static final int TAKE_PHOTO = 0x02;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    protected static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;
    protected static final String IMAGE_NAME = "CropImage";
    protected String fileName;
    private Intent intent;//存放图片的uri
    private int currentCode;
    private File cameraFile;
    private Activity activity;

    public UpPhotoManager(Activity activity) {
        this.activity = activity;
    }

    /**
     * 弹出窗口
     */
    public void showPictureCutPop(View view) {
        // 获取弹出视图对象
        View viewPopup = View.inflate(view.getContext(), R.layout.pop_cut_icon, null);
        // 创建 弹出窗口
        final PopupWindow window = new PopupWindow(viewPopup, DensityUtil.getScreenWidth(), DensityUtil.dip2px(151));
        final WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        viewPopup.findViewById(R.id.tv_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
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
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 获取控件的坐标 x y
        window.showAtLocation(view, Gravity.TOP, 0, DensityUtil.getScreenHeight() - DensityUtil.dip2px(151));
    }
    /**
     * 拍照
     */
    private void takePhoto() {
        String state = Environment.getExternalStorageState(); //拿到sdcard是否可用的状态码
        if (state.equals(Environment.MEDIA_MOUNTED)) {   //如果可用
            cameraFile = new File(PathUtil.getInstance().getImagePath(), EMClient.getInstance().getCurrentUser()
                    + System.currentTimeMillis() + ".jpg");
            //noinspection ResultOfMethodCallIgnored
            cameraFile.getParentFile().mkdirs();
            currentCode = TAKE_PHOTO;
            activity.startActivityForResult(
                    new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile)),
                    currentCode);
        } else {
            ToastUtils.showToast("SD卡不可用");
        }

    }
    /**
     * 相册选者图片
     */
    private void pickFromGallery() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    activity.getString(R.string.permission_write_storage_rationale),
                    REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
            return;
        }
        Intent intent;
        currentCode = REQUEST_SELECT_PICTURE;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        activity.startActivityForResult(intent, currentCode);
    }
    /**
     * 处理裁剪图片  保存压缩
     *
     * @param result
     */
    private void handleCropResult(@NonNull Intent result) {
        intent = result;
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    activity.getString(R.string.permission_write_storage_rationale),
                    REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
        } else {
            Uri resultUri = UCrop.getOutput(result);
            if (resultUri != null) {

                try {
                    Bitmap bitmap = BitmapUtils.getBitmapFormUri(activity, resultUri, 200);
                    if (bitmap == null) return;
                    saveCroppedImage(bitmap);//保存图片到本地
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showToast("未解析到图片");
                }

            } else {
                Toast.makeText(activity, R.string.toast_cannot_retrieve_selected_image, Toast.LENGTH_SHORT).show();
            }
        }
    }
        /**
         * 将截图存在本地
         *
         * @param b
         */
    private void saveCroppedImage(final Bitmap b) {

        if (b != null) {
            try {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    final String downloadsDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                    final String saveName = System.currentTimeMillis() + "icon.jpg";
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
                } else {
                    ToastUtils.showToast("SD卡未挂载！");
                    fileName = null;
                }
            } catch (Exception e) {
                ToastUtils.showToast(e.getMessage());
            }
        } else {
            ToastUtils.showToast(UIUtils.getString(R.string.toast_unexpected_error));

        }

    }
    /**
     * 将bitmap写入文件，之后方便上传
     *
     * @param bmp
     * @param path
     * @param bitName
     * @return
     * @throws IOException
     */
    public boolean saveMyBitmap(Bitmap bmp, String path, String bitName) throws IOException {
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        String url = path + "/" + bitName;
        File file = new File(url);
        boolean flag = false;
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            flag = true;
            fileName = url;
            x.task().post(new Runnable() {
                @Override
                public void run() {
                    childViewShow("file://" + fileName);
                }
            });
            bmp.recycle();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (fOut != null) {
                fOut.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        IOUtils.close(fOut);
        return flag;
    }

    public abstract void childViewShow(String url);

    protected void requestPermission(final String permission, String rationale, final int requestCode) {
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
}
