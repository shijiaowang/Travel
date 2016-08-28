package com.example.administrator.travel.ui.activity.drag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.travel.R;
import com.example.administrator.travel.event.DragEvent;

import org.greenrobot.eventbus.EventBus;

import github.chenupt.dragtoplayout.Logger;
import github.chenupt.multiplemodel.recycler.ModelRecyclerAdapter;

/**
 * @author bohe
 * @ClassName:
 * @Description:
 * @date 2016/4/28 13:58
 */
public class RecyclerFragmentAlbum extends RecyclerBaseFragment {

    private static String TAG = RecyclerFragmentAlbum.class.getSimpleName();
    private RecyclerView recyclerView;

    public static RecyclerFragmentAlbum newInstance(String name) {
        RecyclerFragmentAlbum fragment = new RecyclerFragmentAlbum();
        TAG = name;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_other_album, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {
        recyclerView = (RecyclerView) getView().findViewById(R.id.rv_album);
        // init recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        ModelRecyclerAdapter adapter = new ModelRecyclerAdapter(getActivity(), DataService.getInstance().getModelManager());
        adapter.setList(DataService.getInstance().getList());
        recyclerView.setAdapter(adapter);
        // set data source
        adapter.notifyDataSetChanged();

        // Todo
        setRecyclerView(recyclerView);
        // attach top listener
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Logger.e("TAG= ", TAG + ", getShouldDelegateTouch= " + getShouldDelegateTouch());
                EventBus.getDefault().post(new DragEvent(1, getShouldDelegateTouch()));
            }
        });

    }

}


