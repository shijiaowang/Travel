package com.yunspeak.travel.ui.appoint.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.global.CancelDialogClick;
import com.yunspeak.travel.global.IState;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.global.ParentPopClick;
import com.yunspeak.travel.global.SendTextClick;
import com.yunspeak.travel.ui.appoint.together.togetherdetail.AppointTogetherDetailEvent;
import com.yunspeak.travel.ui.appoint.withme.withmedetail.MyCreateAppointAdapter;
import com.yunspeak.travel.ui.appoint.withme.withmedetail.MyCreateAppointBean;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.me.myappoint.MyAppointActivity;
import com.yunspeak.travel.utils.JsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.TypefaceUtis;
import com.yunspeak.travel.utils.UIUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.json.JSONObject;
import org.xutils.common.util.DensityUtil;

import java.util.List;
import java.util.Map;

import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by wangyang on 2016/9/14 0014.
 * 报名dialog
 */
public class EnterAppointDialog {


    public static int select=-1;

    public static void showDialogSuccess(final Context context) {
        //创建视图
        View dialogView = View.inflate(context, R.layout.dialog_appoint_enlist_success, null);
       final Dialog dialog = new Dialog(context,R.style.noTitleDialog);

        dialogView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialogView.findViewById(R.id.tv_my_appoint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, MyAppointActivity.class));
            }
        });
        Window window = dialog.getWindow(); //得到对话框
        window.setWindowAnimations(R.style.dialogAnima); //设置窗口弹出动画
        window.setGravity(Gravity.CENTER);

        //创建 Dialog
//		Dialog dialog=new Dialog(上下文,风格style);

        //layout_width layout_height
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(285),DensityUtil.dip2px(179));
        dialog.setContentView(dialogView, params);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
    /**
     * 改变性别
     */
    public static void showMyAppoint(Context context, final List<MyCreateAppointBean.DataBean> dataBeen, final ParentPopClick parentPopClick) {
        //创建视图
        View dialogView = View.inflate(context, R.layout.dialog_appoint_list, null);
        final Dialog dialog = new Dialog(context,R.style.noTitleDialog);
        final MyCreateAppointAdapter myCreateAppointAdapter=new MyCreateAppointAdapter(dataBeen,context);
        RecyclerView recyclerView = (RecyclerView) dialogView.findViewById(R.id.rv_appoint);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        recyclerView.setAdapter(myCreateAppointAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        if (dataBeen.size()>3){
            ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
            layoutParams.height=DensityUtil.dip2px(270);
            recyclerView.setLayoutParams(layoutParams);//最高不能超过三个孩子高度
        }
        myCreateAppointAdapter.setItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (dataBeen.get(position).getIs_push()==1){
                    ToastUtils.showToast("您已经向该用户推送过此约伴信息，无法再次推送！");
                }else {
                    if (select !=-1){
                        dataBeen.get(select).setSelect(false);
                    }
                    select =position;
                    dataBeen.get(position).setSelect(true);
                    myCreateAppointAdapter.notifyDataSetChanged();
                }
            }
        });
        dialogView.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select==-1){
                    ToastUtils.showToast("您未选择任何约伴！");
                }else {
                    parentPopClick.onClick(select);
                    dialog.dismiss();
                }
            }
        });
        dialogView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow(); //得到对话框
        window.setGravity(Gravity.CENTER);
        //创建 Dialog
//		Dialog dialog=new Dialog(上下文,风格style);
        //layout_width layout_height
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(285), ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(dialogView, params);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
    /**
     * 改变性别
     * @param sex
     */
    public static void showChangeSex(Context context,String sex, final SendTextClick parentPopClick) {
        //创建视图
        View dialogView = View.inflate(context, R.layout.pop_change_sex, null);
        final Dialog dialog = new Dialog(context,R.style.noTitleDialog);

        RadioButton rbBoy = (RadioButton) dialogView.findViewById(R.id.rb_boy);
        rbBoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentPopClick.onClick("1");
                dialog.dismiss();
            }
        });
        RadioButton rbGirl = (RadioButton) dialogView.findViewById(R.id.rb_girl);
        rbGirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentPopClick.onClick("0");
                dialog.dismiss();
            }
        });
        if (sex.equals("1")){
            rbBoy.setChecked(true);
            rbGirl.setChecked(false);
        }else {
            rbBoy.setChecked(false);
            rbGirl.setChecked(true);
        }
        dialogView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentPopClick.onClick("2");
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow(); //得到对话框
        window.setGravity(Gravity.CENTER);
        //创建 Dialog
//		Dialog dialog=new Dialog(上下文,风格style);
        //layout_width layout_height
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(200),DensityUtil.dip2px(160));
        dialog.setContentView(dialogView, params);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
    /**
     * 设置目的地
     */
    public static void showInputTextView(final Context context, String hint, String title, String okText, final SendTextClick parentPopClick) {
        //创建视图
        View dialogView = View.inflate(context, R.layout.dialog_appoint_add_destination, null);
        final Dialog dialog = new Dialog(context,R.style.noTitleDialog);
        final EditText mEtDestination = (EditText) dialogView.findViewById(R.id.et_destination);
        ((TextView) dialogView.findViewById(R.id.tv_title)).setText(title);
        mEtDestination.requestFocus();
        UIUtils.setEmojiFilter(mEtDestination);
        mEtDestination.setHint(hint);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mEtDestination,InputMethodManager.SHOW_FORCED);
            }
        });
        TextView tvOk = (TextView) dialogView.findViewById(R.id.tv_ok);
        tvOk.setText(okText);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = mEtDestination.getText().toString().trim();
                if (StringUtils.isEmptyNotNull(trim)){
                    ToastUtils.showToast("填写内容不能为空!");
                    return;
                }
                if (parentPopClick!=null){
                    parentPopClick.onClick(trim);
                }
                dialog.dismiss();

            }
        });
        dialogView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow(); //得到对话框
        window.setWindowAnimations(R.style.dialogAnima); //设置窗口弹出动画
        window.setGravity(Gravity.CENTER);

        //创建 Dialog
//		Dialog dialog=new Dialog(上下文,风格style);

        //layout_width layout_height
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(285),DensityUtil.dip2px(159));
        dialog.setContentView(dialogView, params);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
    /**
     * 添加目的地
     * @param isStart
     */
    public static void showDialogAddDestination(final Context context, final TextView textView, final boolean isStart) {
        //创建视图
        View dialogView = View.inflate(context, R.layout.dialog_appoint_add_destination, null);
        final Dialog dialog = new Dialog(context,R.style.noTitleDialog);
        final EditText mEtDestination = (EditText) dialogView.findViewById(R.id.et_destination);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mEtDestination,InputMethodManager.SHOW_FORCED);
            }
        });
        UIUtils.setEmojiFilter(mEtDestination);
        dialogView.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = mEtDestination.getText().toString().trim();
                if (StringUtils.isEmpty(trim)){
                    ToastUtils.showToast("目的地不能为空!");
                    return;
                }

                try {
                    JSONObject basecJsonObject = JsonUtils.getBasecJsonObject();
                    String key=isStart?IVariable.MEET_ADDRESS:IVariable.OVER_ADDRESS;
                    JsonUtils.putString(key,trim, basecJsonObject);
                    textView.setText(trim);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                 if (StringUtils.isEmpty(trim)){
                     textView.setBackgroundResource(R.drawable.activity_line_plan_add_bg);
                     textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
                     textView.setTypeface(TypefaceUtis.getTypeface(context));
                 }else {
                     textView.setBackgroundColor(Color.TRANSPARENT);
                     textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                     textView.setTypeface(Typeface.DEFAULT);
                 }
                dialog.dismiss();

            }
        });
        dialogView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow(); //得到对话框
        if (window!=null) {
            window.setGravity(Gravity.CENTER);
        }

        //创建 Dialog
//		Dialog dialog=new Dialog(上下文,风格style);

        //layout_width layout_height
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(285),DensityUtil.dip2px(159));
        dialog.setContentView(dialogView, params);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();



    }
    /**
     * 投诉
     * @param context
     * @param id
     */
    public static void showDialogAddComplaint(final Context context, final String id, final String type, final String typeClass, final String rid) {
        //创建视图
        final View dialogView = View.inflate(context, R.layout.dialog_appoint_add_complaint, null);
        final Dialog dialog = new Dialog(context,R.style.noTitleDialog);
        final RadioGroup mRbGroup = (RadioGroup) dialogView.findViewById(R.id.rg_group);
        final EditText mEtContent = (EditText) dialogView.findViewById(R.id.et_conetnet);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mEtContent,InputMethodManager.SHOW_FORCED);
            }
        });
        dialogView.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedRadioButtonId = mRbGroup.getCheckedRadioButtonId();
                if (checkedRadioButtonId==-1){
                    ToastUtils.showToast("请选者投诉类型！");
                    return;
                }
                if (StringUtils.isEmpty(mEtContent.getText().toString())){
                    ToastUtils.showToast("请输入投诉的详细描述！");
                    return;
                }
                String text = ((RadioButton) dialogView.findViewById(checkedRadioButtonId)).getText().toString();
                Map<String, String> end = MapUtils.Build().addKey().addUserId().addFId(id).addType(type).add("type_class", typeClass).addClass(text).addContent(mEtContent.getText().toString().trim()).addRId(rid).end();
                XEventUtils.postUseCommonBackJson(IVariable.REPORT,end, IState.TYPE_OTHER,new AppointTogetherDetailEvent());
                dialog.dismiss();
            }
        });
        dialogView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow(); //得到对话框
        window.setWindowAnimations(R.style.dialogAnima); //设置窗口弹出动画
        window.setGravity(Gravity.CENTER);

        //创建 Dialog
//		Dialog dialog=new Dialog(上下文,风格style);

        //layout_width layout_height
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(334),DensityUtil.dip2px(211));
        dialog.setContentView(dialogView, params);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        dialog.show();
    }


    /**
     * 分享
     *
     */
    public static void showShareDialog(final Context context) {
        // 获取弹出视图对象
        final View dialogView = View.inflate(context, R.layout.pop_share, null);
        final Dialog dialog = new Dialog(context,R.style.noTitleDialog);
        View rlBottom = ((Activity) context).findViewById(R.id.rl_bottom);
        Window window = dialog.getWindow(); //得到对话框
        window.setGravity(Gravity.CENTER);
        dialogView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialogView.findViewById(R.id.tv_sina).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        ShareSDK.initSDK(context);
                        OnekeyShare oks = new OnekeyShare();
//关闭sso授权
                        //oks.disableSSOWhenAuthorize();
                        oks.setPlatform(SinaWeibo.NAME);
// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
                        oks.setTitle("云说约伴分享");
// text是分享文本，所有平台都需要这个字段
                        oks.setText("这是一个不得了的约伴分享");
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
                        oks.setComment("哇，不得了啦，有美女哟");
// 启动分享GUI
                        oks.show(context);


            }
        });
        dialogView.findViewById(R.id.tv_we_friend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSDK.initSDK(context);
                OnekeyShare oks = new OnekeyShare();
//关闭sso授权
                //oks.disableSSOWhenAuthorize();
                oks.setPlatform(Wechat.NAME);
// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
                oks.setTitle("云说约伴分享");
// text是分享文本，所有平台都需要这个字段
                oks.setText("这是一个不得了的约伴分享");
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
                oks.setUrl("http://yunspeak.cn");
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
                oks.setComment("哇，不得了啦，有美女哟");
                oks.show(context);
            }
        });
        dialogView.findViewById(R.id.tv_we_moment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSDK.initSDK(context);
                OnekeyShare oks = new OnekeyShare();
//关闭sso授权
                //oks.disableSSOWhenAuthorize();
                oks.setPlatform(WechatMoments.NAME);
// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
                oks.setTitle("云说约伴分享");
// text是分享文本，所有平台都需要这个字段
                oks.setText("这是一个不得了的约伴分享");
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
                oks.setUrl("http://yunspeak.cn");
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
                oks.setComment("哇，不得了啦，有美女哟");
                oks.show(context);
            }
        });
         dialogView.findViewById(R.id.tv_qq_zone).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 ShareSDK.initSDK(context);
                 OnekeyShare oks = new OnekeyShare();
//关闭sso授权
                 //oks.disableSSOWhenAuthorize();
                 oks.setPlatform(QZone.NAME);
// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
                 oks.setTitle("云说约伴分享");
// titleUrl是标题的网络链接，QQ和QQ空间等使用
                 oks.setTitleUrl("http://yunspeak.cn");
// text是分享文本，所有平台都需要这个字段
                 oks.setText("这是一个不得了的约伴分享");
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
               //  oks.setUrl("http://sharesdk.cn");
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
                 oks.setComment("哇，不得了啦，有美女哟");
// site是分享此内容的网站名称，仅在QQ空间使用
                 oks.setSite(context.getString(R.string.app_name));
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
                 oks.setSiteUrl("http://yunspeak.cn");
// 启动分享GUI
                 oks.show(context);
             }
         });
        WindowManager.LayoutParams wl = window.getAttributes();
        int[] location = new int[2];
        rlBottom.getLocationInWindow(location);
        wl.x = 0;
        wl.y = location[1];
        //设置显示位置
        dialog.onWindowAttributesChanged(wl);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.getScreenWidth(),DensityUtil.dip2px(200));
        dialog.setContentView(dialogView, params);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK)
                    dialog.dismiss();
                return true;
            }
        });
        dialog.show();

    }
    public static void showCommonDialog(Context context,String title, String okText, String content, final ParentPopClick parentPopClick) {
        //创建视图
        View dialogView = View.inflate(context, R.layout.dialog_appoint_enlist_fail, null);
        final Dialog dialog = new Dialog(context,R.style.noTitleDialog);
        ((TextView) dialogView.findViewById(R.id.tv_title)).setText(title);
        ((TextView) dialogView.findViewById(R.id.tv_content)).setText(UIUtils.getString(R.string.kongge)+content);
        dialogView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView tvSure = (TextView) dialogView.findViewById(R.id.tv_sure);
        tvSure.setText(okText);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentPopClick.onClick(0);
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow(); //得到对话框
        window.setGravity(Gravity.CENTER);

        //创建 Dialog
//		Dialog dialog=new Dialog(上下文,风格style);

        //layout_width layout_height
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(285),DensityUtil.dip2px(179));
        dialog.setContentView(dialogView, params);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK)
                    dialog.dismiss();
                return true;
            }
        });
        dialog.show();
    }


}
