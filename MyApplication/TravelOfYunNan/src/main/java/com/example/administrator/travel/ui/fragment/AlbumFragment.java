package com.example.administrator.travel.ui.fragment;


import com.example.administrator.travel.R;

import com.example.administrator.travel.ui.adapter.AlbumAdapter;
import com.example.administrator.travel.ui.view.ToShowAllGridView;

/**
 * Created by Administrator on 2016/7/14 0014.
 */
public class AlbumFragment extends BaseFragment {

    private ToShowAllGridView mGvAlbum;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_album;
    }

    @Override
    protected void initView() {
        mGvAlbum = (ToShowAllGridView) root.findViewById(R.id.gv_album);
    }

    @Override
    protected void initData() {
         mGvAlbum.setAdapter(new AlbumAdapter(getActivity(),null));
    }

    @Override
    protected void initListener() {

    }
}
