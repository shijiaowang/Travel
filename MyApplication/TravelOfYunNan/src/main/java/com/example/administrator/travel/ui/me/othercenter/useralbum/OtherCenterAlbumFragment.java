package com.example.administrator.travel.ui.me.othercenter.useralbum;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.baseui.loadMoreListener;
import com.example.administrator.travel.ui.fragment.BaseFragment;

import org.xutils.x;

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
        final List list=new ArrayList();
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
        final OtherAlbumAdapter adapter = new OtherAlbumAdapter(list);
        viewById.setAdapter(adapter);
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        viewById.setLayoutManager(layout);
        viewById.addOnScrollListener(new loadMoreListener(layout) {
            @Override
            public void onLoadMore(int childCount) {
                adapter.startLoading();
                x.task().run(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            list.add(1);
                            list.add(1);
                            list.add(1);
                           x.task().post(new Runnable() {
                               @Override
                               public void run() {
                                   adapter.endLoading();
                                   adapter.notifyDataSetChanged();
                               }
                           });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                });


            }
        });
    }

    @Override
    protected void initListener() {

    }
}
