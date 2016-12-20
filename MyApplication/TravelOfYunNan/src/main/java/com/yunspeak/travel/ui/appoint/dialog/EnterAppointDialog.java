package com.yunspeak.travel.ui.appoint.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
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
import com.yunspeak.travel.global.DiscussPopClick;
import com.yunspeak.travel.global.IState;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.global.ParentPopClick;
import com.yunspeak.travel.global.SendTextClick;
import com.yunspeak.travel.ui.appoint.together.togetherdetail.AppointTogetherDetailEvent;
import com.yunspeak.travel.ui.appoint.withme.withmedetail.MyCreateAppointAdapter;
import com.yunspeak.travel.bean.MyCreateAppointBean;
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
    public static int select = -1;

    public static void showDialogSuccess(final Context context) {
        //创建视图
        View dialogView = View.inflate(context, R.layout.dialog_appoint_enlist_success, null);
        final Dialog dialog = new Dialog(context, R.style.noTitleDialog);

        dialogView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialogView.findViewById(R.id.tv_my_appoint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAppointActivity.start(context,MyAppointActivity.PASSED);
                dialog.dismiss();
            }
        });
       showEndCommon(dialogView,dialog,R.dimen.x285);

    }

    /**
     * 改变性别
     */
    public static void showMyAppoint(Context context, final List<MyCreateAppointBean.DataBean> dataBeen, final ParentPopClick parentPopClick) {
        //创建视图
        View dialogView = View.inflate(context, R.layout.dialog_appoint_list, null);
        final Dialog dialog = new Dialog(context, R.style.noTitleDialog);
        final MyCreateAppointAdapter myCreateAppointAdapter = new MyCreateAppointAdapter(dataBeen, context);
        RecyclerView recyclerView = (RecyclerView) dialogView.findViewById(R.id.rv_appoint);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setAdapter(myCreateAppointAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        if (dataBeen.size() > 3) {
            ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
            layoutParams.height = (int) context.getResources().getDimension(R.dimen.x267);
            recyclerView.setLayoutParams(layoutParams);//最高不能超过三个孩子高度
        }
        myCreateAppointAdapter.setItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (dataBeen.get(position).getIs_push() == 1) {
                    ToastUtils.showToast("您已经向该用户推送过此约伴信息，无法再次推送！");
                } else {
                    if (select != -1) {
                        dataBeen.get(select).setSelect(false);
                    }
                    select = position;
                    dataBeen.get(position).setSelect(true);
                    myCreateAppointAdapter.notifyDataSetChanged();
                }
            }
        });
        dialogView.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select == -1) {
                    ToastUtils.showToast("您未选择任何约伴！");
                } else {
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
        showEndCommon(dialogView,dialog,R.dimen.x285);
    }

    /**
     * 改变性别
     *
     * @param sex
     */
    public static void showChangeSex(Context context, String sex, final SendTextClick parentPopClick) {
        //创建视图
        View dialogView = View.inflate(context, R.layout.pop_change_sex, null);
        final Dialog dialog = new Dialog(context, R.style.noTitleDialog);

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
        if (sex.equals("1")) {
            rbBoy.setChecked(true);
            rbGirl.setChecked(false);
        } else {
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
        showEndCommon(dialogView, dialog,R.dimen.x200);
    }

    /**
     * 设置目的地
     */
    public static void showInputTextView(final Context context, final View hideView, String hint, String title, String okText, final SendTextClick parentPopClick) {
        //创建视图
        View dialogView = View.inflate(context, R.layout.dialog_appoint_add_destination, null);
        final Dialog dialog = new Dialog(context, R.style.noTitleDialog);
        final EditText mEtDestination = (EditText) dialogView.findViewById(R.id.et_destination);
        ((TextView) dialogView.findViewById(R.id.tv_title)).setText(title);
        mEtDestination.requestFocus();
        UIUtils.setEmojiFilter(mEtDestination);
        mEtDestination.setHint(hint);
        final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                imm.showSoftInput(mEtDestination, InputMethodManager.SHOW_FORCED);
            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                imm.hideSoftInputFromWindow(hideView.getWindowToken(), 0); //强制隐藏键盘
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                imm.hideSoftInputFromWindow(hideView.getWindowToken(), 0); //强制隐藏键盘

            }
        });
        TextView tvOk = (TextView) dialogView.findViewById(R.id.tv_ok);
        tvOk.setText(okText);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = mEtDestination.getText().toString().trim();
                if (StringUtils.isEmptyNotNull(trim)) {
                    ToastUtils.showToast("填写内容不能为空!");
                    return;
                }
                if (parentPopClick != null) {
                    parentPopClick.onClick(trim);
                }
                dialog.dismiss();
                imm.hideSoftInputFromWindow(hideView.getWindowToken(), 0); //强制隐藏键盘
            }
        });
        dialogView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        showEndCommon(dialogView, dialog,R.dimen.x285);
    }

    private static void showEndCommon(View dialogView, Dialog dialog, int resWidth) {
        Window window = dialog.getWindow(); //得到对话框
        if (window!=null) {
            window.setGravity(Gravity.CENTER);
        }
        Context context = dialog.getContext();
        float dimension = context.getResources().getDimension(resWidth);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) dimension, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(dialogView, params);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
    private static void showEndCommon(View dialogView, Dialog dialog, int resWidth,int resHeiht) {
        Window window = dialog.getWindow(); //得到对话框
        if (window!=null) {
            window.setGravity(Gravity.CENTER);
        }
        Context context = dialog.getContext();
        float dimension = context.getResources().getDimension(resWidth);
        float height = context.getResources().getDimension(resHeiht);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) dimension, (int) height);
        dialog.setContentView(dialogView, params);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
    /**
     * 添加目的地
     *
     * @param isStart
     */
    public static void showDialogAddDestination(final Context context, final TextView textView, final boolean isStart) {
        //创建视图
        View dialogView = View.inflate(context, R.layout.dialog_appoint_add_destination, null);
        final Dialog dialog = new Dialog(context, R.style.noTitleDialog);
        final EditText mEtDestination = (EditText) dialogView.findViewById(R.id.et_destination);
        final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                imm.showSoftInput(mEtDestination, InputMethodManager.SHOW_FORCED);
            }
        });
        UIUtils.setEmojiFilter(mEtDestination);
        dialogView.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = mEtDestination.getText().toString().trim();
                if (StringUtils.isEmpty(trim)) {
                    ToastUtils.showToast("目的地不能为空!");
                    return;
                }

                try {
                    JSONObject basecJsonObject = JsonUtils.getBasecJsonObject();
                    String key = isStart ? IVariable.MEET_ADDRESS : IVariable.OVER_ADDRESS;
                    JsonUtils.putString(key, trim, basecJsonObject);
                    textView.setText(trim);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (StringUtils.isEmpty(trim)) {
                    textView.setBackgroundResource(R.drawable.activity_line_plan_add_bg);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                    textView.setTypeface(TypefaceUtis.getTypeface(context));
                } else {
                    textView.setBackgroundColor(Color.TRANSPARENT);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
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
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                imm.hideSoftInputFromWindow(mEtDestination.getWindowToken(), 0); //强制隐藏键盘
            }
        });
        showEndCommon(dialogView, dialog,R.dimen.x285);


    }

    /**
     * 投诉
     *
     * @param context
     * @param id
     */
    public static void showDialogAddComplaint(final Context context, final String id, final String type, final String typeClass, final String rid) {
        //创建视图
        final View dialogView = View.inflate(context, R.layout.dialog_appoint_add_complaint, null);
        final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        final Dialog dialog = new Dialog(context, R.style.noTitleDialog);
        final RadioGroup mRbGroup = (RadioGroup) dialogView.findViewById(R.id.rg_group);
        final EditText mEtContent = (EditText) dialogView.findViewById(R.id.et_conetnet);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                imm.showSoftInput(mEtContent, InputMethodManager.SHOW_FORCED);
            }
        });
        dialogView.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedRadioButtonId = mRbGroup.getCheckedRadioButtonId();
                if (checkedRadioButtonId == -1) {
                    ToastUtils.showToast("请选者投诉类型！");
                    return;
                }
                if (StringUtils.isEmpty(mEtContent.getText().toString())) {
                    ToastUtils.showToast("请输入投诉的详细描述！");
                    return;
                }
                String text = ((RadioButton) dialogView.findViewById(checkedRadioButtonId)).getText().toString();
                Map<String, String> end = MapUtils.Build().addKey().addUserId().addFId(id).addType(type).add("type_class", typeClass).addClass(text).addContent(mEtContent.getText().toString().trim()).addRId(rid).end();
                XEventUtils.postUseCommonBackJson(IVariable.REPORT, end, IState.TYPE_OTHER, new AppointTogetherDetailEvent());
                dialog.dismiss();
            }
        });
        dialogView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                imm.hideSoftInputFromWindow(mEtContent.getWindowToken(), 0); //强制隐藏键盘
            }
        });
        showEndCommon(dialogView, dialog,R.dimen.x334,R.dimen.x211);
    }


    /**
     * 分享
     */
    public static void showShareDialog(final Context context, final String title, final String content, final String url, final View cropView) {
        // 获取弹出视图对象
        final View dialogView = View.inflate(context, R.layout.pop_share, null);
        final Dialog dialog = new Dialog(context, R.style.noTitleDialog);
        Window window = dialog.getWindow(); //得到对话框
        if (window!=null) {
            window.setGravity(Gravity.CENTER);
        }
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
                oks.setPlatform(SinaWeibo.NAME);
                oks.setTitle(title);
                oks.setText(content);
                oks.setComment("小伙伴召集中，期待您的加入！");
                shareEndCommon(oks, cropView, context);


            }
        });
        dialogView.findViewById(R.id.tv_we_friend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSDK.initSDK(context);
                OnekeyShare oks = new OnekeyShare();
                oks.setPlatform(Wechat.NAME);
                oks.setTitle(title);
                oks.setText(content);
                oks.setUrl(url);
                shareEndCommon(oks, cropView, context);
            }
        });
        dialogView.findViewById(R.id.tv_we_moment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSDK.initSDK(context);
                OnekeyShare oks = new OnekeyShare();
                oks.setPlatform(WechatMoments.NAME);
                oks.setTitle(title);
                oks.setText(content);
                oks.setUrl(url);
                shareEndCommon(oks, cropView, context);
            }
        });
        dialogView.findViewById(R.id.tv_qq_zone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSDK.initSDK(context);
                OnekeyShare oks = new OnekeyShare();
                oks.setPlatform(QZone.NAME);
                oks.setTitle(title);
                oks.setText(content);
                oks.setTitleUrl(url);
                oks.setSite(context.getString(R.string.app_name));
                oks.setSiteUrl(url);
                shareEndCommon(oks, cropView, context);
            }
        });
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = DensityUtil.getScreenHeight();
        //设置显示位置
        dialog.onWindowAttributesChanged(wl);
        float height = context.getResources().getDimension(R.dimen.x200);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.getScreenWidth(), (int) height);
        dialog.setContentView(dialogView, params);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

    }

    private static void shareEndCommon(OnekeyShare oks, View cropView, Context context) {
        getPicture(oks, cropView, context);
        oks.show(context);
    }

    private static void getPicture(OnekeyShare oks, View cropView, Context context) {

        try {
                if (cropView != null) {
                    oks.setViewToShare(cropView);
                }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void showCommonDialog(Context context, String title, String okText, String content, final ParentPopClick parentPopClick) {
        //创建视图
        View dialogView = View.inflate(context, R.layout.dialog_appoint_enlist_fail, null);
        final Dialog dialog = new Dialog(context, R.style.noTitleDialog);
        ((TextView) dialogView.findViewById(R.id.tv_title)).setText(title);
        ((TextView) dialogView.findViewById(R.id.tv_content)).setText(UIUtils.getString(R.string.kongge) + content);
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
        showEndCommon(dialogView, dialog,R.dimen.x285,R.dimen.x179);
    }

    public static void showOrderDiscuss(Context context, final DiscussPopClick parentPopClick) {
        //创建视图
        View dialogView = View.inflate(context, R.layout.dialog_activity_order_disscuss, null);
        final Dialog dialog = new Dialog(context, R.style.noTitleDialog);
        final EditText etText = (EditText) dialogView.findViewById(R.id.et_content);
        final TextView tvCount = (TextView) dialogView.findViewById(R.id.tv_count);
        final RadioGroup rbGroup = (RadioGroup) dialogView.findViewById(R.id.rb_group);
        etText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String countString = etText.getText().toString();
                tvCount.setText(countString.length()+"/120");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dialogView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView tvSure = (TextView) dialogView.findViewById(R.id.tv_sure);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedRadioButtonId = rbGroup.getCheckedRadioButtonId();
                if (checkedRadioButtonId==-1){
                    ToastUtils.showToast("请选择一种评价类型");
                    return;
                }
                String trim = etText.getText().toString().trim();

                if (StringUtils.isEmptyNotNull(trim) && checkedRadioButtonId!=R.id.rb_discuss_good){//好评不用输入评论
                    ToastUtils.showToast("请输入您的宝贵评价");
                    return;
                }
                int type;
                switch (checkedRadioButtonId){
                    case R.id.rb_discuss_good:
                        type=1;
                        break;
                    case R.id.rb_discuss_normal:
                        type=2;
                        break;
                    default:
                        type=3;
                        break;
                }
                parentPopClick.onClick(type,trim);
                dialog.dismiss();
            }
        });
        showEndCommon(dialogView, dialog,R.dimen.x285);
    }
}
