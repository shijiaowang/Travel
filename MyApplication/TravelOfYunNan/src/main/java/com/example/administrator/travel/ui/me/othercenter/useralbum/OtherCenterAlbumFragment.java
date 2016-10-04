package com.example.administrator.travel.ui.me.othercenter.useralbum;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;
import android.widget.GridView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.OtherUserCenterAlbumAdapter;
import com.example.administrator.travel.ui.fragment.BaseFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/8/23 0023.
 */
public class OtherCenterAlbumFragment extends BaseFragment {

    private RecyclerView viewById;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_other_album;
    }

    @Override
    protected void initView() {
        viewById = (RecyclerView) root.findViewById(R.id.rv_album);
    }

    @Override
    protected void initData() {
        List list=new ArrayList();
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        viewById.setAdapter(new OtherAlbumAdapter(list));
        viewById.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void initListener() {

    }
}
