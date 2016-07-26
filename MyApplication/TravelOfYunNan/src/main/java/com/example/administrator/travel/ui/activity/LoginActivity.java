package com.example.administrator.travel.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Login;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class LoginActivity extends BaseActivity {

    private EditText mEdPassword;
    private EditText mEdName;
    private Button mBtLogin;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        mEdName = (EditText) findViewById(R.id.ed_name);
        mEdPassword = (EditText) findViewById(R.id.ed_password);
        mBtLogin = (Button) findViewById(R.id.bt_login);
    }

    @Override
    protected void initListener() {
        mBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = mEdName.getText().toString();
                final String password = mEdPassword.getText().toString();
                if (StringUtils.isEmpty(name) || StringUtils.isEmpty(password)) {
                    ToastUtils.showToast(LoginActivity.this, "密码或者用户名为空");
                    return;
                }
                final String url = "http://192.168.1.158/api.php?s=/api/getKeys";
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL loginUrl = new URL(url);
                            HttpURLConnection urlConnection = (HttpURLConnection) loginUrl.openConnection();
                            urlConnection.setConnectTimeout(5000);
                            urlConnection.setRequestMethod("GET");
                            urlConnection.setReadTimeout(5000);
                            urlConnection.connect();
                            if (urlConnection.getResponseCode() == 200) {
                                InputStream inputStream = urlConnection.getInputStream();
                                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                int length = 0;
                                byte[] b = new byte[1024];
                                while ((length = inputStream.read(b)) != -1) {
                                    outputStream.write(b, 0, length);
                                }
                                inputStream.close();
                                outputStream.close();
                                String result = new String(outputStream.toByteArray());
                                LogUtils.e(result);
                                Gson gson = new Gson();
                                Login login = gson.fromJson(result, Login.class);


                            } else {
                                LogUtils.e("网络错误");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            LogUtils.e("网络错误");
                        }
                    }
                }).start();
            }
        });
    }

    @Override
    protected void initData() {

    }
}
