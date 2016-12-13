package yunshuo.yneb.com.myapplication.activity;

import android.content.Intent;
import android.text.InputType;
import android.view.View;

import java.util.Map;

import butterknife.BindView;
import yunshuo.yneb.com.myapplication.GlobalValue;
import yunshuo.yneb.com.myapplication.IVariable;
import yunshuo.yneb.com.myapplication.R;
import yunshuo.yneb.com.myapplication.other.bean.Key;
import yunshuo.yneb.com.myapplication.other.bean.Login;
import yunshuo.yneb.com.myapplication.other.bean.UserInfo;
import yunshuo.yneb.com.myapplication.other.db.DBManager;
import yunshuo.yneb.com.myapplication.other.event.LoginEvent;
import yunshuo.yneb.com.myapplication.other.utils.ActivityUtils;
import yunshuo.yneb.com.myapplication.other.utils.GlobalUtils;
import yunshuo.yneb.com.myapplication.other.utils.GsonUtils;
import yunshuo.yneb.com.myapplication.other.utils.MD5Utils;
import yunshuo.yneb.com.myapplication.other.utils.MapUtils;
import yunshuo.yneb.com.myapplication.other.utils.ShareUtil;
import yunshuo.yneb.com.myapplication.other.utils.StringUtils;
import yunshuo.yneb.com.myapplication.other.utils.ToastUtils;
import yunshuo.yneb.com.myapplication.other.utils.UserUtils;
import yunshuo.yneb.com.myapplication.other.utils.XEventUtils;
import yunshuo.yneb.com.myapplication.other.view.AvoidFastButton;
import yunshuo.yneb.com.myapplication.other.view.LoginEditText;

/**
 * Created by wangyang on 2016/7/26 0026.
 * 登录
 */
public class LoginActivity extends BaseEventBusActivity<LoginEvent> {
    public static final int SPLASH_RESULT=1;//返回
    @BindView(R.id.et_password)
    LoginEditText mEdPassword;
    @BindView(R.id.et_name) LoginEditText mEdName;
    @BindView(R.id.bt_login)
    AvoidFastButton mBtLogin;
    private int tryGetKey=0;
    private String name;
    private String password;
    private boolean isFirstError=true;
    private void goToLogin() {
        Map<String, String> logMap = MapUtils.Build().addKey().add(IVariable.USERNAME, name).add(IVariable.PASSWORD, MD5Utils.encode(MD5Utils.encode(password))).end();
        XEventUtils.postUseCommonBackJson(IVariable.LOGIN_URL, logMap, IVariable.TYPE_POST_LOGIN,new LoginEvent());
    }

    @Override
    protected void initEvent() {
        ActivityUtils.getInstance().addActivity(this);
        mEdName.setInputType(InputType.TYPE_CLASS_PHONE);
        mBtLogin.setOnAvoidFastOnClickListener(new AvoidFastButton.AvoidFastOnClickListener() {
            @Override
            public void onClick(View v) {
                name = mEdName.getString();
                password =mEdPassword.getString();
                if (StringUtils.isEmpty(name) || StringUtils.isEmpty(password)) {
                    ToastUtils.showToast("密码或者用户名为空");
                    return;
                }
                if (StringUtils.isEmpty(GlobalUtils.getKey())){
                    XEventUtils.getUseCommonBackJson(IVariable.GET_KEY, null, IVariable.TYPE_GET_KEY, new LoginEvent());
                    return;
                }
                setIsProgress(true);
                goToLogin();

            }
        });

    }







    @Override
    protected void onSuccess(LoginEvent event) {
            if (event.getType()==IVariable.TYPE_GET_KEY ){
                    //处理初次key出问题
                    Key key = GsonUtils.getObject(event.getResult(), Key.class);
                    GlobalValue.KEY_VALUE = MD5Utils.encode(MD5Utils.encode(key.getData().getValue()));
                    ShareUtil.putString(this, IVariable.KEY_VALUE, GlobalValue.KEY_VALUE);
                    goToLogin();
            }else {
                GlobalValue.KEY_VALUE = GlobalUtils.getKey();
                Login object = GsonUtils.getObject(event.getResult(), Login.class);
                UserInfo userInfo = object.getData();
                GlobalValue.userInfo=userInfo;//赋值
                UserUtils.saveUserInfo(userInfo);//序列化
                goToHomeActivity(event);
            }
    }

    private void goToHomeActivity(LoginEvent event) {
        Login login = GsonUtils.getObject(event.getResult(), Login.class);
        UserInfo data = login.getData();
        ShareUtil.putString(this,IVariable.SAVE_NAME, data.getName());
        ShareUtil.putString(this,IVariable.SAVE_PWD, data.getPwd());
        setResult(SPLASH_RESULT);
        Intent intent = new Intent(this, HomeActivity.class);

        if (data!=null){
            com.hyphenate.easeui.domain.UserInfo userInfo=new com.hyphenate.easeui.domain.UserInfo();
            userInfo.setId(data.getId());
            userInfo.setNick_name(data.getNick_name());
            userInfo.setUser_img(data.getUser_img());
            DBManager.insertChatUserInfo(userInfo);
        }
        intent.putExtra(IVariable.USER_INFO, data);
        startActivity(intent);

        finish();
    }

    @Override
    protected void onFail(LoginEvent event) {
        if (event.getCode()==IVariable.KEY_ERROR && isFirstError){
            isFirstError=false;//避免无限循环key错误
            if (tryGetKey<3) {
                tryGetKey++;
                XEventUtils.getUseCommonBackJson(IVariable.GET_KEY, null, IVariable.TYPE_GET_KEY, new LoginEvent());
            }
        }
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected String initTitle() {
        return "登录";
    }
}
