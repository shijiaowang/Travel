package com.example.administrator.travel.ui.me.bulltetinboard;

import android.app.Activity;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.LoadingBarBaseActivity;
import com.example.administrator.travel.ui.view.refreshview.XListView;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ViewInject;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2016/8/2 0002.
 * 公告栏
 */
public class BulletinBoardActivity extends LoadingBarBaseActivity {
   @ViewInject(R.id.lv_bulletin_board)
    private XListView mLvBulletinBoard;
    private int count=0;
    private BulletinBoardAdapter bulletinBoardAdapter;
    private List<BulletinBoardBean.DataBean> mBeanData;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_bulletin_board;
    }

    @Override
    protected void initEvent() {
        initXListView(mLvBulletinBoard,true,true);
    }

    @Override
    protected void onLoad(int type) {
        count = type==TYPE_LOAD?getListSize(mBeanData):0;
        Map<String, String> bulletinMap = MapUtils.Build().addKey(this).addtId("3").addPageSize(10).addCount(count).end();
        XEventUtils.getUseCommonBackJson(IVariable.BULLETIN_BOARD,bulletinMap,type,new BulletinBoardEvent());
    }

    @Override
    protected Activity initViewData() {

        return this;
    }
    @Subscribe
   public void onEvent(BulletinBoardEvent event){
        setIsProgress(false);
        loadEnd(mLvBulletinBoard);
       if (event.isSuccess()){
           dealData(event);
       }else {
           ToastUtils.showToast(event.getMessage());
           setIsError(true);
       }
   }
    private void dealData(BulletinBoardEvent event) {
        BulletinBoardBean bulletinBoardBean = GsonUtils.getObject(event.getResult(), BulletinBoardBean.class);
        List<BulletinBoardBean.DataBean> data = bulletinBoardBean.getData();
        if (data==null)return;
        if (bulletinBoardAdapter==null) {
            mBeanData =  data;
            bulletinBoardAdapter = new BulletinBoardAdapter(this, mBeanData);
            mLvBulletinBoard.setAdapter(bulletinBoardAdapter);
        }else {
            if (event.getType()==TYPE_REFRESH){
                mBeanData = data;
                bulletinBoardAdapter.notifyDataSetChanged();
            }else if (event.getType()==TYPE_LOAD){
                mBeanData.addAll(data);
                bulletinBoardAdapter.notifyDataSetChanged();
            }
        }
    }
    @Override
    protected String setTitleName() {
        return "公告板";
    }

    @Override
    public float getAlpha() {
        return 1f;
    }
}
