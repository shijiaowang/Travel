package com.example.administrator.travel.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.SettingTitle;
import com.example.administrator.travel.event.SettingTitleEvent;
import com.example.administrator.travel.event.TabEvent;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.ui.view.FlowLayout;
import com.example.administrator.travel.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2016/9/6 0006.
 * 标签
 */

public class TabFragment extends BaseFragment
{
    public static final String TITLE = "title";
    public static final String TITLE_TYPE = "type";
    public  int mTitleType = -1;
    public static final int TYPE_ADD = 0;
    public static final int REMOVE = 1;
    private String[] mTitle = null;
    private FlowLayout mFlTitle;
    private Set<Integer> integerset=new HashSet<>();
    private int prePosition=-1;
    private LayoutInflater inflater;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mTitle = getArguments().getStringArray(TITLE);
            mTitleType=getArguments().getInt(TITLE_TYPE);
        }
        registerEventBus();

    }

    public static TabFragment newInstance(String[] title,int type)
    {
        TabFragment tabFragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArray(TITLE, title);
        bundle.putInt(TITLE_TYPE, type);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_tab;
    }

    @Override
    protected void initView() {
        mFlTitle = (FlowLayout) root.findViewById(R.id.fl_title);
    }

    @Override
    protected void initData() {
        int count=mTitle==null?0:mTitle.length;
        inflater = LayoutInflater.from(getContext());
        for (int i=0;i<count;i++){
            TextView textView = (TextView) inflater.inflate(R.layout.item_fragment_tab_title, mFlTitle, false);
            textView.setText(mTitle[i]);
            mFlTitle.addView(textView);
        }
        mFlTitle.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mFlTitle.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mFlTitle.setOnItemClickListener(new FlowLayout.OnItemClickListener() {
                    @Override
                    public void OnItemClick(int position) {
                        prePosition = position;
                        if (integerset.contains(position)) {
                            integerset.remove(position);
                            GlobalValue.selectTitleNumber--;
                            notifyTitle(REMOVE);
                        } else {
                            if (GlobalValue.selectTitleNumber >= 7) {
                                ToastUtils.showToast("最不起，最多选择7个称号");
                                return;
                            }
                            GlobalValue.selectTitleNumber++;
                            integerset.add(position);
                            notifyTitle(TYPE_ADD);
                        }

                    }
                });
            }
        });

    }
   public void notifyTitleByPosition(int position){
       if (integerset.contains(position))integerset.remove(position);
       notifyTitle(TYPE_ADD);
   }
    private void notifyTitle(int type) {
        //发送消息，更新activity界面
        SettingTitle settingTitle=new SettingTitle();
        settingTitle.setPosition(prePosition);
        settingTitle.setType(mTitleType);
        settingTitle.setChangeType(type);
        settingTitle.setTitle(mTitle[prePosition]);
        SettingTitleEvent settingTitleEvent = new SettingTitleEvent();
        settingTitleEvent.setSettingTitle(settingTitle);
        EventBus.getDefault().post(settingTitleEvent);
       mFlTitle.changeColorAndBg(type, prePosition);
    }


    @Override
    protected void initListener() {

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterEventBus();
    }
    @Subscribe
   public void onEvent(TabEvent event){
       if (event.getType()==mTitleType){
           mFlTitle.changeColorAndBg(REMOVE, event.getPosition());
           integerset.remove(event.getPosition());
           GlobalValue.selectTitleNumber--;
       }
   }
    public void registerEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }

    public void unregisterEventBus() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
