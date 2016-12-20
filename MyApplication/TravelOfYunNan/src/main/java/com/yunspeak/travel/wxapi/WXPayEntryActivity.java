package com.yunspeak.travel.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yunspeak.travel.Constants;
import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.me.ordercenter.orders.confirmorders.WxPaySuccessEvent;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
    private IWXAPI api;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
    	api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
    }
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}
	@Override
	public void onResp(BaseResp resp) {
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			WxPaySuccessEvent wxPaySuccessEvent=new WxPaySuccessEvent();
			switch (resp.errCode){
				case BaseResp.ErrCode.ERR_OK:
					wxPaySuccessEvent.setSuccess(true);
					break;
				default:
					wxPaySuccessEvent.setSuccess(false);
					break;
			}
			EventBus.getDefault().post(wxPaySuccessEvent);
			finish();
		}
	}
}