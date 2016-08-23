package com.example.administrator.travel.ui.fragment;

import android.widget.AbsListView;
import android.widget.GridView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.OtherUserCenterAlbumAdapter;

import org.greenrobot.eventbus.EventBus;

import github.chenupt.dragtoplayout.AttachUtil;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
public class OtherCenterAlbumFragment extends BaseFragment {

    private GridView viewById;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_other_album;
    }

    @Override
    protected void initView() {
        viewById = (GridView) root.findViewById(R.id.gv_album);
        viewById.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                EventBus.getDefault().post(AttachUtil.isAdapterViewAttach(view));
            }
        });
    }

    @Override
    protected void initData() {
           viewById.setAdapter(new OtherUserCenterAlbumAdapter(getContext(),null));
    }

    @Override
    protected void initListener() {

    }
}
