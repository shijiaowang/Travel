package com.yunspeak.travel.db;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import com.yunspeak.travel.bean.CityNameBean;
import com.yunspeak.travel.bean.User;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import simpledao.cityoff.com.easydao.BaseEasyDao;
import simpledao.cityoff.com.easydao.utils.IOUtils;

/**
 * Created by wangyang on 2017/3/3.
 * 城市数据库表
 */

public class CityDao extends BaseEasyDao<CityNameBean> {
    private static final String TABLE_NAME="yuns_district";
    private static final String SQL_TEXT="yun.sql";

    /**
     * 初始化city表
     * @param context 上下文
     * @return true初始化成功或者已经初始化过  false 初始化失败，尚未初始化
     */
    public boolean init(Context context){
        if (!cityTableIsExists()){//不存在就初始化
            InputStream in = null;
            try {
                in = context.getAssets().open(SQL_TEXT);
                String sqlData = readTextFromSDcard(in);
                String[] s = sqlData.split(";");
                for (String sql : s) {
                    if (!TextUtils.isEmpty(sql)) {
                        exec(sql);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                IOUtils.close(in);
            }
        }
        return true;
    }

    /**
     * 城市数据库是否存在
     * @return true  是  false  不存在
     */
    private boolean cityTableIsExists() {//查询是否有值
        Cursor query = sqLiteDatabase.query(TABLE_NAME, null, null, null, null, null, null, "1");
        return query.moveToNext();
    }

    /**
     * 将文件转换为字符串
     * @param is 读取的文件流
     * @return
     * @throws Exception
     */
    private  String readTextFromSDcard(InputStream is) throws Exception {
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuilder buffer = new StringBuilder("");
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
            buffer.append("\n");
        }
        return buffer.toString();
    }

    /**
     * 查询所有市级别，用户城市酒店筛选 包括香港 澳门 直辖市等等
     * @return 市级别城市
     */
    public  List<CityNameBean> queryAllCity(){
        List<CityNameBean> list=new ArrayList<>();
        Cursor cursor=null;
        try {
            //"yuns_district", null, "level=? or _id=?", new String[]{"2"},null,null, "pinyin"
            cursor = sqLiteDatabase.rawQuery("select * from "+TABLE_NAME+" where level = ? or _id in ('1','2','9','22','32','33','34','36') order by pinyin",new String[]{"2"});
            CityNameBean cityNameBean=null;
            while (cursor.moveToNext()){
                cityNameBean=new CityNameBean();
                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int upid = cursor.getInt(cursor.getColumnIndex("upid"));
                int level = cursor.getInt(cursor.getColumnIndex("level"));
                String index = cursor.getString(cursor.getColumnIndex("key"));
                String pinyin = cursor.getString(cursor.getColumnIndex("pinyin"));
                cityNameBean.set_id(id);
                cityNameBean.setName(name);
                cityNameBean.setLevel(level);
                cityNameBean.setUpId(upid);
                cityNameBean.setIndex(index);
                cityNameBean.setPinYin(pinyin);
                list.add(cityNameBean);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor!=null){
                cursor.close();
            }
        }
        return list;

    }
    /**
     * 获取省市级联列表
     *
     * @param options1Items 传入省的集合
     * @return
     */
    public  ArrayList<ArrayList<CityNameBean>> getCity(ArrayList<CityNameBean> options1Items) {
        if (options1Items==null || options1Items.size()==0)return null;
        CityNameBean queryBean=new CityNameBean();
        queryBean.setLevel(2);
        ArrayList<ArrayList<CityNameBean>> arrayLists = new ArrayList<>();
        for (CityNameBean cityNameBean : options1Items) {
            queryBean.setUpId(cityNameBean.get_id());
            ArrayList<CityNameBean> list = queryAll(queryBean);
            arrayLists.add(list);
        }
       return arrayLists;
    }

    public ArrayList<CityNameBean> queryAll(CityNameBean t) {
        if (t == null) return null;
        ExecParams execParams = null;
        try {
            execParams = new ExecParams(t).invoke();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        String whereClause = execParams.getWhereClause();
        String[] whereArgs = execParams.getWhereArgs();
        Cursor query = sqLiteDatabase.query(this.tableName, null, whereClause, whereArgs, null, null, null);
        ArrayList<CityNameBean> datas = new ArrayList<>();
        try {
            while (query.moveToNext()) {
                CityNameBean data = getEntity(t, query);
                datas.add(data);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            closeCursor(query);
        }
        return datas;
    }
    /**
     * 通过列名称 与 upId获取名称
     * @param name
     * @param upId
     * @return
     */
   public String getCityId(String name,int upId){
       CityNameBean cityNameBean=new CityNameBean();
       cityNameBean.setUpId(upId);
       cityNameBean.setName(name);
       CityNameBean query = query(cityNameBean);
       return query==null?"":query.get_id()+"";
   }

    /**
     * 通过id获取城市名称
     * @param id
     * @return
     */
   public String getStringById(int id){
       CityNameBean cityNameBean=new CityNameBean();
       cityNameBean.set_id(id);
       CityNameBean query = query(cityNameBean);
       return query==null?"未知":query.getName();
   }
}
