package com.yunspeak.travel.utils;

import com.yunspeak.travel.bean.CityNameBean;
import com.yunspeak.travel.db.CityDao;

import simpledao.cityoff.com.easydao.BaseDaoFactory;

/**
 * Created by wangyang on 2017/3/6.
 */

public class CityUtils {
   private static CityDao cityDao= BaseDaoFactory.getInstance().getDaoHelper(CityDao.class, CityNameBean.class);
    public static String getStringById(String  id){
        return cityDao.getStringById(Integer.parseInt(id));
    }
}
