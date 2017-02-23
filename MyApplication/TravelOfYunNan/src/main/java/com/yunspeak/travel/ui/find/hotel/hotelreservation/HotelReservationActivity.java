package com.yunspeak.travel.ui.find.hotel.hotelreservation;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.CityNameBean;
import com.yunspeak.travel.event.HttpEvent;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.ui.find.hotel.HotelIndexActivity;
import com.yunspeak.travel.ui.find.hotel.timeselect.TimeSelectActivity;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;


/**
 * Created by wangyang on 2017/2/23.
 * 预订酒店
 */

public class HotelReservationActivity extends BaseNetWorkActivity<HttpEvent> implements View.OnClickListener {

    @BindView(R.id.iv_hotel_reservation_location)
    ImageView ivHotelReservationLocation;
    @BindView(R.id.tv_hotel_reservation_city_name)
    TextView tvHotelReservationCityName;
    @BindView(R.id.tv_hotel_reservation_cursor)
    TextView tvHotelReservationCursor;
    @BindView(R.id.tv_hotel_reservation_address)
    TextView tvHotelReservationAddress;
    @BindView(R.id.iv_hotel_reservation_date)
    ImageView ivHotelReservationDate;
    @BindView(R.id.tv_hotel_reservation_in)
    TextView tvHotelReservationIn;
    @BindView(R.id.tv_hotel_reservation_out)
    TextView tvHotelReservationOut;
    private LocationClient mLocationClient;
    public BDLocationListener myListener = new MyLocationListener();
    @Override
    protected void initEvent() {
        getLocation();
        ivHotelReservationLocation.setOnClickListener(this);
        ivHotelReservationDate.setOnClickListener(this);
        tvHotelReservationCityName.setOnClickListener(this);
        tvHotelReservationCursor.setOnClickListener(this);

    }
    /**
     * 获取地址信息
     */
    private void getLocation() {
        //声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener);

        LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if(!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            Toast.makeText(this, "打开GPS定位将更准确", Toast.LENGTH_SHORT).show();
        }
        initLocation();
        mLocationClient.start();

    }
    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("BD09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        //option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            boolean isSuccess=false;
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                isSuccess=true;
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
               isSuccess=true;
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                isSuccess=true;
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                isSuccess=false;
            }
            if (isSuccess){
                double latitude = location.getLatitude();
                double longtitude = location.getLongitude();
                String city = location.getCity();
                String addrStr = location.getAddrStr();
                if (!TextUtils.isEmpty(addrStr)) tvHotelReservationAddress.setText(addrStr);
                if (!TextUtils.isEmpty(city)) tvHotelReservationCityName.setText(city);
            }else {
                ToastUtils.showToast("定位失败");
            }
        }
    }
    @Override
    protected boolean isAutoLoad() {
        return false;
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {

    }

    @Override
    protected String initUrl() {
        return null;
    }

    @Override
    protected void onSuccess(HttpEvent httpEvent) {

    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_hotel_reservation;
    }

    @Override
    protected String initTitle() {
        return "预订酒店";
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_hotel_reservation_date:
                startActivity(new Intent(this, TimeSelectActivity.class));
                break;
            case R.id.iv_hotel_reservation_location:
                if (mLocationClient!=null) {
                    mLocationClient.stop();
                }
                getLocation();

                break;
            case R.id.tv_hotel_reservation_cursor:
            case R.id.tv_hotel_reservation_city_name:
                startActivity(new Intent(this, HotelIndexActivity.class));
                break;
        }
    }
    @Subscribe
    public void onEvent(CityNameBean cityNameBean){
        if (cityNameBean==null)return;
        tvHotelReservationCityName.setText(cityNameBean.getName()+"市");
        tvHotelReservationAddress.setText("");
    }

}
