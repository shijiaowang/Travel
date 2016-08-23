package com.example.administrator.travel.ui.activity.dragtopview;

import com.example.administrator.travel.bean.Album;


import java.util.ArrayList;
import java.util.List;

import github.chenupt.multiplemodel.ItemEntity;
import github.chenupt.multiplemodel.ItemEntityCreator;
import github.chenupt.multiplemodel.ModelManager;
import github.chenupt.multiplemodel.ModelManagerBuilder;

/**
 * Created by chenupt@gmail.com on 1/30/15.
 * Description :
 */
public class AlbumDataService {



    public ModelManager getModelManager() {
        return ModelManagerBuilder.begin().addModel(AlbumView.class).build(ModelManager.class);
    }

    public List<ItemEntity> getList() {
        List<ItemEntity> resultList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Album album=new Album();
            album.setAlbumName("毛球怪"+i);
            ItemEntityCreator.create(album).setModelView(AlbumView.class).attach(resultList);
        }
        return resultList;
    }




    private static volatile AlbumDataService instance = null;

    private AlbumDataService(){
    }

    public static AlbumDataService getInstance() {
        if (instance == null) {
            synchronized (AlbumDataService.class) {
                if (instance == null) {
                    instance = new AlbumDataService();
                }
            }
        }
        return instance;
    }

}
