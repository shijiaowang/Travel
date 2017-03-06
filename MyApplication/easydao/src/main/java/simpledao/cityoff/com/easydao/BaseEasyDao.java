package simpledao.cityoff.com.easydao;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import simpledao.cityoff.com.easydao.annotation.RenameField;
import simpledao.cityoff.com.easydao.annotation.TableName;
import simpledao.cityoff.com.easydao.annotation.UpdateKey;
import simpledao.cityoff.com.easydao.update.UpdateManager;

/**
 * Created by wangyang on 2017/2/13.
 */

public abstract class BaseEasyDao<T> implements IEasyDao<T> {
    protected SQLiteDatabase sqLiteDatabase;
    private Map<String, Field> fieldMap = null;//维护实体对象与数据列名的关系
    protected String tableName;
    private boolean isInit = false;
    private boolean isIgnoreZero = true;//是否忽略int Double等为0的情况

    public boolean isIgnoreZero() {
        return isIgnoreZero;
    }

    public void setIgnoreZero(boolean ignoreZero) {
        isIgnoreZero = ignoreZero;
    }

    protected synchronized void init(Class<T> t, SQLiteDatabase sqLiteDatabase, boolean isIgnoreZero) throws Exception {
        //数据库没有开启 初始化过都停止
        this.isIgnoreZero = isIgnoreZero;
        if (t == null || sqLiteDatabase == null || isInit || !sqLiteDatabase.isOpen()) return;
        this.sqLiteDatabase = sqLiteDatabase;
        TableName annotation = t.getAnnotation(TableName.class);
        if (annotation != null) {
            tableName = annotation.value();
        } else {
            tableName = t.getClass().getSimpleName().toLowerCase();
        }
        //获取xml中的创表语句 防止用户删除了sd卡的数据库
        String dbName=BaseDaoFactory.getInstance().getSqLiteDatabase()==sqLiteDatabase?
                BaseDaoFactory.getInstance().getCommonDbName():BaseDaoFactory.PRIVATE_DB;
        String createSql = UpdateManager.getInstance().getCreateSql(tableName, dbName);
        if (TextUtils.isEmpty(createSql)){
            throw new Exception("you no set create this table "+tableName+" sql in update.xml or current db no this table");
        }
        sqLiteDatabase.execSQL(createSql);//创建表
        fieldMap = new HashMap<>();
        initCacheMap(t);
        isInit = true;
    }

    /**
     * 维护实体与数据库列名的关系
     *
     * @param t
     */
    private void initCacheMap(Class<T> t) {
        String sql = "select * from " + this.tableName + " limit 1 , 0";
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.rawQuery(sql, null);
            String[] columnNames = cursor.getColumnNames();
            Field[] fields = t.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
            }
            for (String columnName : columnNames) {
                for (Field field : fields) {
                    RenameField annotation = field.getAnnotation(RenameField.class);
                    String value;
                    if (annotation != null) {
                        value = annotation.value();
                    } else {
                        value = field.getName();
                    }
                    if (value.equals(columnName)) {
                        fieldMap.put(columnName, field);
                        break;
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeCursor(cursor);
        }


    }



    /**
     * 更新操作
     * @param t
     * @return
     */
    @Override
    public int update(T t) {
        if (t == null) return -1;
        ContentValues contentValues = getContentValues(t);
        Field[] declaredFields = t.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
        }
        StringBuilder whereClause = new StringBuilder();
        StringBuilder whereArgs = new StringBuilder();
        boolean isFirst = true;
        try {
            for (Field declaredField : declaredFields) {
                UpdateKey annotation = declaredField.getAnnotation(UpdateKey.class);
                if (annotation != null) {
                    Object o = declaredField.get(t);
                    if (o == null) {
                        continue;
                    }
                    String value = o.toString();
                    String name = declaredField.getName();
                    if (isFirst){
                        isFirst=false;
                        whereClause.append(name+" = ?");
                        whereArgs.append(value);
                    }else {
                        whereClause.append(" and "+name+" = ?");
                        whereArgs.append("-"+value);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(whereClause) || TextUtils.isEmpty(whereClause))return -1;
        return sqLiteDatabase.update(this.tableName,contentValues,whereClause.toString(),whereArgs.toString().split("-"));
    }



    /**
     * 将映射好的values集合转化为contentValues
     *
     * @return
     */
    protected ContentValues getContentValues(T entity) {
        Map<String, String> values = getValues(entity);
        if (values == null) return null;
        ContentValues contentValues = new ContentValues();
        Iterator<String> iterator = values.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = values.get(key);
            contentValues.put(key, value);
        }
        return contentValues;
    }

    /**
     * 映射数据库的列与实体对象field的值
     *
     * @param entity 实体对象
     * @return
     */
    protected Map<String, String> getValues(T entity) {
        if (entity == null) return null;
        Map<String, String> valuesMap = new HashMap<>();
        if (fieldMap==null || fieldMap.size()==0){
            return null;
        }
        Iterator<Field> iterator = fieldMap.values().iterator();
        try {
            while (iterator.hasNext()) {
                Field field = iterator.next();
                Field[] fields = entity.getClass().getDeclaredFields();
                for (Field entityField : fields) {
                    if (field.getName().equals(entityField.getName())) {
                        RenameField annotation = field.getAnnotation(RenameField.class);
                        String key;
                        if (annotation == null) {
                            key = field.getName();
                        } else {
                            key = annotation.value();
                        }
                        Object value = field.get(entity);
                        if (value == null || TextUtils.isEmpty(value.toString())) {
                            break;
                        }
                        if (!(isIgnoreZero && isEqualsZero(value))) {
                            valuesMap.put(key, value.toString());
                        }
                        break;
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return valuesMap;
    }

    /**
     * 暂时更改isIgnoreZero的值
     * 主要用于bean对象查询更新时存在int float等数字默认值为0的时候而数据库相对应的值可能为空的情况
     * @param activity
     * @param runnable
     * @param isIgnoreZero 是否忽略对象等于0的情况
     */
    public void runTempChangeIsIgnoreZero(Activity activity, Runnable runnable, boolean isIgnoreZero) {
        boolean temp = this.isIgnoreZero;
        this.isIgnoreZero = isIgnoreZero;
        activity.runOnUiThread(runnable);
        this.isIgnoreZero = temp;
    }

    private boolean isEqualsZero(Object value) {
        return value.toString().equals("0") || value.toString().equals("0.0");
    }

    @Override
    public long insert(T t) {
        ContentValues contentValues = getContentValues(t);
        if (contentValues == null || contentValues.size()==0) return 0;
        return sqLiteDatabase.insert(this.tableName, null, contentValues);
    }

    @Override
    public long insert(List<T> datas) {
        if (datas == null) return 0;
        long total = 0;
        for (T data : datas) {
            if (data == null) continue;
            total = insert(data);
        }
        return total;
    }

    /**
     * 查询一个
     * @param t
     * @return
     */
    @Override
    public T query(T t) {
        if (t==null)return null;
        ExecParams execParams = null;
        try {
            execParams = new ExecParams(t).invoke();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Cursor query = sqLiteDatabase.query(this.tableName, null, execParams.getWhereClause(), execParams.getWhereArgs(), null, null, null, "1");
        T entity=null;
        try {
            if (query.moveToNext()){
                entity= getEntity(t,query);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }finally {
            closeCursor(query);
        }

        return entity;
    }

    /**
     * 关闭流
     * @param query
     */
    protected void closeCursor(Cursor query) {
        if (query!=null){
            query.close();
        }
    }

    @Override
    public List<T> queryAll(T t) {
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
        List<T> datas = new ArrayList<>();
        try {
            while (query.moveToNext()) {
                T data = getEntity(t, query);
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
     * 获取实体
     * @param t
     * @param query
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    protected T getEntity(T t, Cursor query) throws InstantiationException, IllegalAccessException {
        T data = (T) t.getClass().newInstance();
        Iterator<String> iterator = fieldMap.keySet().iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            String value = query.getString(query.getColumnIndex(next));
            if (TextUtils.isEmpty(value)) {
                continue;
            }
            Field field = fieldMap.get(next);
            String type = field.getGenericType().toString();
            Object temp = null;
            switch (type) {
                case "class java.lang.String":
                    temp=value;
                    break;
                case "int":
                    temp = Integer.valueOf(value);
                    break;
                case "double":
                    temp = Double.valueOf(value);
                    break;
                case "float":
                    temp = Float.valueOf(value);
                    break;
                case "short":
                    temp = Float.valueOf(value);
                    break;
                case "byte":
                    temp = Byte.valueOf(value);
                    break;
                case "char":
                    temp = value.toCharArray()[0];
                    break;
                case "long":
                    temp = Long.valueOf(value);
                    break;
                default:
                    temp = null;
                    break;
            }
            if (temp != null) {
                field.set(data, temp);
            }
        }
        return data;
    }


    @Override
    public int delete(T t) {
        ExecParams execParams = null;
        try {
            execParams = new ExecParams(t).invoke();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        String whereClause = execParams.getWhereClause();
        String[] whereArgs = execParams.getWhereArgs();
        return sqLiteDatabase.delete(this.tableName, whereClause, whereArgs);
    }

    /**
     * 查询删除条件类
     */
    protected class ExecParams {
        private T t;
        private StringBuilder whereClause;
        private String[] whereArgs;

        public ExecParams(T t) {
            this.t = t;
        }

        public String getWhereClause() {
            return whereClause.toString();
        }

        public String[] getWhereArgs() {
            return whereArgs;
        }

        public ExecParams invoke() throws Exception {
            Map<String, String> values =  getValues(t);
            if (values==null || values.size()==0){
                throw new Exception("无法映射出bean 对象与表的关系");
            }
            Iterator<String> iterator = values.keySet().iterator();
            whereClause = new StringBuilder();
            whereArgs = new String[values.size()];
            boolean isFirst = true;
            int index = 0;
            while (iterator.hasNext()) {
                String next = iterator.next();
                if (isFirst) {
                    isFirst = false;
                    whereClause.append(next + " = ?");
                } else {
                    whereClause.append(" and " + next + " = ?");
                }

                String value = values.get(next);
                whereArgs[index++] = value;
            }
            return this;
        }
    }

    /**
     * 判断是否需要更新
     * @param queryObj 用于查询的条件
     * @param object 需要更新的object
     */
   public void updateOrInsert(T queryObj,T object){
       if (object==null)return;
       T query = query(queryObj);
       if (query==null){
           insert(object);
       }else {
           update(object);//直接更新，反射判断更浪费时间
       }
   }

    @Override
    public void exec(String sql) throws Exception{
         sqLiteDatabase.execSQL(sql);
    }
}
