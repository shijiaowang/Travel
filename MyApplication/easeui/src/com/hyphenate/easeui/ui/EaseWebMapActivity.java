/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hyphenate.easeui.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.utils.MapUtils;

import java.util.List;

public class EaseWebMapActivity extends AppCompatActivity {


    public static EaseWebMapActivity instance = null;
    private boolean isShowSned;
    private boolean isShowOldLoaction;
    private double latitude;
    private double longtitude;
    private boolean locationIsSuccess=false;




    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private String address;
    private ProgressDialog progress;
    private String title;
    private WebView mWvView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.ease_activity_web_map);
        mWvView = (WebView)findViewById(R.id.wv_html);
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener( myListener );
        Toolbar mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        Intent intent = getIntent();
        latitude = intent.getDoubleExtra("latitude", 0);
        longtitude = intent.getDoubleExtra("longitude", 0);
        isShowOldLoaction = intent.getBooleanExtra("isNew", false);//是否展示之前定位
        address = intent.getStringExtra("address");
        title = intent.getStringExtra("title");
        mWvView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mWvView.loadUrl(url);
                return true;
            }
        });
        WebSettings settings = mWvView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setDomStorageEnabled(true);//允许DCOM
        settings.setLoadWithOverviewMode(true);
        mWvView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (progress!=null && newProgress==100){
                    progress.dismiss();
                }
            }
        });
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        if (latitude == 0) {
            if (isShowOldLoaction) {
                Toast.makeText(this, "暂无位置信息", Toast.LENGTH_SHORT).show();
            } else {
                getLoaction();
            }
        } else {
            showWebMap();
        }

    }
/**/
    private void showWebMap() {
       /* String url = "http://api.map.baidu.com/marker?location=" + latitude + "," + longtitude +
                "&" + "title="+"城外旅游" + "&content=位置信息&output=html&src=城外旅游";*/
       // String url=" http://api.map.baidu.com/marker?location=40.047669,116.313082&title=我的位置&content=百度奎科大厦&output=html&src=cityoff";
        mWvView.loadUrl("file:///android_asset/test.html");
    }

    private void initLocation(){
        progress = new ProgressDialog(this);
        progress.setTitle("定位中...");
        progress.show();
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }
    /**
     * 获取地址信息
     */
    private void getLoaction() {
        LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if(!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            Toast.makeText(instance, "打开GPS定位将更准确", Toast.LENGTH_SHORT).show();
        }
        initLocation();

    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            progress.dismiss();
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            latitude=location.getLatitude();
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            longtitude=location.getLongitude();
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
                locationIsSuccess=true;
                showWebMap();
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                locationIsSuccess=true;
                showWebMap();
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                Toast.makeText(EaseWebMapActivity.this, "定位失败", Toast.LENGTH_SHORT).show();
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                locationIsSuccess=true;
                showWebMap();
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
                Toast.makeText(EaseWebMapActivity.this, "定位失败", Toast.LENGTH_SHORT).show();
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                Toast.makeText(EaseWebMapActivity.this, "定位失败", Toast.LENGTH_SHORT).show();
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            Log.i("BaiduLocationApiDem", sb.toString());
            address=location.getAddrStr();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!isShowOldLoaction) {
            getMenuInflater().inflate(R.menu.circle_map, menu);
        } else {
            getMenuInflater().inflate(R.menu.circle_map2, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                if (isShowSned && !isShowOldLoaction) {
                    sendLocation();
                }else {
                    String title = getIntent().getStringExtra("title");
                    if (MapUtils.isBaiduMapInstalled()){

                    }


                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }















    public void sendLocation() {
        if (!locationIsSuccess){
            Toast.makeText(instance, "定位失败！", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = this.getIntent();
        intent.putExtra("latitude",latitude);
        intent.putExtra("longitude",longtitude);
        intent.putExtra("address", address);
        this.setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }

}
