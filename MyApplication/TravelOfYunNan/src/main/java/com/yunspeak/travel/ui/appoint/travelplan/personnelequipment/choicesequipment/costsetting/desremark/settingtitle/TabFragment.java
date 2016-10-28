package com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment.costsetting.desremark.settingtitle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.ui.fragment.BaseFragment;
import com.yunspeak.travel.ui.view.FlowLayout;
import com.yunspeak.travel.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by wangyang on 2016/9/6 0006.
 * 标签
 */

public class TabFragment extends BaseFragment {
    public static final String TITLE = "title";
    public static final String TITLE_TYPE = "type";
    public static final String TITLES = "titles";
    public int mTitleType = -1;
    public static final int TYPE_ADD = 0;
    public static final int REMOVE = 1;
    private List<UserLabelBean> mTitle = null;
    private FlowLayout mFlTitle;
    private Set<Integer> integerSet = new HashSet<>();
    private int prePosition = -1;
    private LayoutInflater inflater;
    private List<SettingTitle> mTitles;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = (List<UserLabelBean>) getArguments().getSerializable(TITLE);
            mTitles = (List<SettingTitle>) getArguments().getSerializable(TITLES);
            mTitleType = getArguments().getInt(TITLE_TYPE);
        }
        registerEventBus();

    }

    public static TabFragment newInstance(List<UserLabelBean> title, int type, List<SettingTitle> settingTitles) {
        TabFragment tabFragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TITLE, (Serializable) title);
        bundle.putInt(TITLE_TYPE, type);
        bundle.putSerializable(TITLES, ((Serializable)settingTitles));
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
        int count = mTitle == null ? 0 : mTitle.size();
        inflater = LayoutInflater.from(getContext());
        for (int i = 0; i < count; i++) {
            TextView textView = (TextView) inflater.inflate(R.layout.item_fragment_tab_title, mFlTitle, false);
            textView.setText(mTitle.get(i).getName());
            textView.setTag(mTitle.get(i).getId());
            //如果之前退出再次进入后，设置之前被选中的过的颜色
            changeBeforeSelect(i, textView);
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
                        if (integerSet.contains(position)) {
                            integerSet.remove(position);
                            GlobalValue.selectTitleNumber--;
                            notifyTitle(REMOVE);
                        } else {
                            if (GlobalValue.selectTitleNumber >= 7) {
                                ToastUtils.showToast("最不起，最多选择7个称号");
                                return;
                            }
                            GlobalValue.selectTitleNumber++;
                            integerSet.add(position);
                            notifyTitle(TYPE_ADD);
                        }

                    }
                });
            }
        });

    }

    private void changeBeforeSelect(int i, TextView textView) {
        if(mTitles!=null) {
            for (SettingTitle settingTitle : mTitles) {
                if (settingTitle.getId().equals(mTitle.get(i).getId()) && mTitleType==settingTitle.getType()) {
                    textView.setTextColor(getContext().getResources().getColor(R.color.otherTitleBg));
                    textView.setBackgroundResource(R.drawable.activity_my_appoint_rl_bg);
                    break;
                }
            }
        }
    }

    public void notifyTitleByPosition(int position) {
        if (integerSet.contains(position)) integerSet.remove(position);
        notifyTitle(TYPE_ADD);
    }

    private void notifyTitle(int type) {
        //发送消息，更新activity界面
        SettingTitle settingTitle = new SettingTitle();
        settingTitle.setPosition(prePosition);
        settingTitle.setType(mTitleType);
        settingTitle.setChangeType(type);
        settingTitle.setId(mTitle.get(prePosition).getId());
        settingTitle.setTitle(mTitle.get(prePosition).getName());
        SettingTitleEvent settingTitleEvent = new SettingTitleEvent();
        settingTitleEvent.setSettingTitle(settingTitle);
        EventBus.getDefault().post(settingTitleEvent);
        mFlTitle.changeColorAndBg(type, mTitle.get(prePosition).getId());
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
    public void onEvent(TabEvent event) {
        if (event.getType() == mTitleType) {
            //更改颜色
            int i = mFlTitle.changeColorAndBg(REMOVE, event.getId());
            if (i > 0) {
                integerSet.remove(i);
                GlobalValue.selectTitleNumber--;
            }
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
