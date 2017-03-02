package simpledao.cityoff.com.easydao;
import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import simpledao.cityoff.com.easydao.exception.EasyConfigErrorException;
import simpledao.cityoff.com.easydao.update.UpdateManager;

/**
 * Created by wangyang on 2017/2/13.
 * 数据库工厂
 */

public class BaseDaoFactory {
    public static final String PRIVATE_DB="personal.db";
    public static final String DB_SECURITY="cityoff";//数据库加密字段
    private static Context context=null;
    private  static EasyConfig easyConfig;
    private SQLiteDatabase sqLiteDatabase;//这个用户存放公用数据
    private  SQLiteDatabase userDatabase;//用户实现多用户分库,这个数据用户存放私人数据
    private static String defaultDataPath;
    private volatile static   BaseDaoFactory baseDaoFactory;
    private Map<String,BaseEasyDao> daoMaps= Collections.synchronizedMap(new HashMap<String, BaseEasyDao>());//管理dao集合
    private static String dbPath;
    private static String dbName;

    public static String getDefaultDataPath() {
        return defaultDataPath;
    }

    public SQLiteDatabase getUserDatabase() {
        return userDatabase;
    }

    public SQLiteDatabase getSqLiteDatabase() {
        return sqLiteDatabase;
    }

    public static EasyConfig getEasyConfig() {
        return easyConfig;
    }

    public static Context getContext() {
        return context;
    }

    private BaseDaoFactory() {
        openDatabase();
    }

    /**
     * 打开或者创建数据库
     */
    private void openDatabase() {
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(defaultDataPath,DB_SECURITY,null);
    }
    public  <T extends BaseEasyDao<M>,M> T getDaoHelper(Class<T> daoClass,Class<M> beanClass){
        return getDaoHelper(daoClass,beanClass,true);
    }
    /**
     * 获取dao对象，公共数据使用此方法
     * @param daoClass dao class
     * @param beanClass bean class
     * @param isIgnoreZero 增删改查的时候是否忽略为0的情况
     * @param <T>
     * @param <M>
     * @return
     */
    public  <T extends BaseEasyDao<M>,M> T getDaoHelper(Class<T> daoClass,Class<M> beanClass,boolean isIgnoreZero){
        return getHelper(daoClass, beanClass, isIgnoreZero,sqLiteDatabase);
    }

    @Nullable
    private <T extends BaseEasyDao<M>, M> T getHelper(Class<T> daoClass, Class<M> beanClass, boolean isIgnoreZero, @NonNull SQLiteDatabase  sqLiteDatabase) {
        BaseEasyDao baseEasyDao=daoMaps.get(beanClass.getSimpleName());
        if (baseEasyDao==null){
            try {
                baseEasyDao = daoClass.newInstance();
                baseEasyDao.init(beanClass, sqLiteDatabase,isIgnoreZero);
                daoMaps.put(daoClass.getSimpleName(),baseEasyDao);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            baseEasyDao.setIgnoreZero(isIgnoreZero);
        }
        return (T) baseEasyDao;
    }

    /**
     * 多用户管理的数据库 ，使用此方法获得的数据库将管理用户的私有数据，
     * 其他用户登录将无法读取该数据库中的数据
     * @param daoClass
     * @param beanClass
     * @param isIgnoreZero
     * @param currentUserId
     * @param <T>
     * @param <M>
     * @return
     */
    public  <T extends BaseEasyDao<M>,M> T getUserDao(Class<T> daoClass,Class<M> beanClass,boolean isIgnoreZero,String currentUserId){
        userDatabase=SQLiteDatabase.openOrCreateDatabase(PrivatePathEnums.DATABASE.getPath(currentUserId),DB_SECURITY,null);
        return getHelper(daoClass,beanClass,isIgnoreZero,userDatabase);
    }

    /**
     * 初始化配置
     * @param easyConfig
     */
    public static void init(EasyConfig easyConfig, Context context) throws EasyConfigErrorException {
        if (easyConfig == null) throw new EasyConfigErrorException("database is not set");
        BaseDaoFactory.context=context;
        BaseDaoFactory.easyConfig = easyConfig;
        SQLiteDatabase.loadLibs(context);//初始化加密数据库
        dbPath = Environment.isExternalStorageEmulated()?Environment.getExternalStorageDirectory().getAbsolutePath()
                :context.getFilesDir().getAbsolutePath();
        dbName = easyConfig.getDbName();
        File file=new File(dbPath,"update");
        if (!file.exists()){
           file.mkdirs();
        }
        dbPath=file.getAbsolutePath();
        dbName =TextUtils.isEmpty(dbName)?context.getString(R.string.app_name).toLowerCase()+".db": dbName;
        dbName = dbName.endsWith(".db")? dbName : dbName +".db";
        defaultDataPath= dbPath +"/"+ dbName;
        getInstance();
    }

    /**
     * 获取公共的DBname
     * @return
     */
    public String getCommonDbName(){
        return dbName;
    }
    public String getDbPath(){
        return this.dbPath;
    }
    /**
     * 获取baseDao单例
     * @return
     */
    public  static BaseDaoFactory getInstance() {
        if (baseDaoFactory==null){
            synchronized (BaseDaoFactory.class){
                if (baseDaoFactory==null){
                    baseDaoFactory=new BaseDaoFactory();
                    UpdateManager.getInstance(baseDaoFactory,context);
                }
            }
        }
        return baseDaoFactory;
    }
}
