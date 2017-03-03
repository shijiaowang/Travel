package simpledao.cityoff.com.easydao.update;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;


import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import simpledao.cityoff.com.easydao.BaseDaoFactory;
import simpledao.cityoff.com.easydao.PrivatePathEnums;
import simpledao.cityoff.com.easydao.utils.FileUtil;
import simpledao.cityoff.com.easydao.utils.IOUtils;

/**
 * Created by wangyang on 2017/2/17.
 * 更新数据库管理
 */

public class UpdateManager {
    private static final String TAG = "UpdateManager";
    public volatile static UpdateManager updateManager = null;
    private final BaseDaoFactory baseDaoFactory;
    private final String UPDATE_NAME = "update.txt";
    private File parentFile = null;
    private Context context;
    private File backFile = null;//备份数据库的文件
    private String preVersion = "0";
    private Map<String,String> recoveryMaps=new HashMap<>();

    private UpdateManager(BaseDaoFactory baseDaoFactory, Context context) {
        this.baseDaoFactory = baseDaoFactory;
        this.context = context;
        parentFile = new File(baseDaoFactory.getDbPath());
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        backFile = new File(parentFile, "backUp");
        if (!backFile.exists()) {
            backFile.mkdirs();
        }
    }

    /**
     * 获取单例模式
     *
     * @param context
     * @return
     */
    public static UpdateManager getInstance(BaseDaoFactory baseDaoFactory, Context context) {
        if (updateManager == null) {
            synchronized (UpdateManager.class) {
                if (updateManager == null) {
                    updateManager = new UpdateManager(baseDaoFactory, context);
                }
            }
        }
        return updateManager;
    }

    public static UpdateManager getInstance() {
        return updateManager;
    }

    /**
     * 检查是否需要进行升级
     *
     * @param ids 多用户升级的id集合
     */
    public void checkAndCreateVersion(String[] ids) {
        String currentVersion = getCurrentVersion(context);
        if (TextUtils.isEmpty(currentVersion)) return;

        getLocalVersion();
        //已经是最新版本
        if (preVersion.trim().equalsIgnoreCase(currentVersion.trim())) {
            return;
        }
        InputStream updateStream = null;
        try {
            updateStream = context.getResources().getAssets().open("update.xml");
            UpdateDbXml updateDbXml = getUpdateDbXml(updateStream);
            //如果是新装用户，直接创建相应数据库表
            if (preVersion.equals("0")) {
                Log.e(TAG,"新用户，开始创建当前版本");
                CreateVersion createVersion = analysisCreateXml(updateDbXml, currentVersion);
                createSuitVersion(createVersion, ids);
                saveLocalVersion(currentVersion);
            } else {//老用户直接进行升级
                Log.e(TAG,"老用户，开始升级数据库");
                startUpdateDb(ids, updateDbXml, currentVersion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(updateStream);
        }
    }

    @NonNull
    private UpdateDbXml getUpdateDbXml(InputStream updateStream) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document parse = documentBuilder.parse(updateStream);
        return new UpdateDbXml(parse);
    }

    /**
     * 保存当前版本
     *
     * @param currentVersion
     */
    private boolean saveLocalVersion(String currentVersion) {
        File file = new File(parentFile, UPDATE_NAME);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file, false);
            fileWriter.write(currentVersion);
            fileWriter.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(fileWriter);
        }
        return false;
    }

    /**
     * 传入表名  获取相应的sql语句
     *
     * @param tableName
     * @return
     */
    public String getCreateSql(String tableName, String dbName){
        String sql=null;
        InputStream updateStream=null;
        try {
            updateStream = context.getResources().getAssets().open("update.xml");
            UpdateDbXml updateDbXml = getUpdateDbXml(updateStream);
            String currentVersion = getCurrentVersion(context);
            CreateVersion createVersion = analysisCreateXml(updateDbXml, currentVersion);
            List<CreateDb> createDbs = createVersion.getCreateDbs();
            if (createDbs!=null && createDbs.size()!=0){
                for (CreateDb createDb : createDbs) {
                    String name = createDb.getName();
                    name+=name.endsWith(".db")?"":".db";
                    if (!name.equals(dbName)){
                        continue;
                    }
                    List<String> createSqls = createDb.getCreateSqls();
                    if (createSqls==null || createSqls.size()==0){
                        continue;
                    }
                    for (String createSql : createSqls) {
                        if (createSql.contains(" "+tableName+"(")) {
                            sql = getTrim(createSql);//获取到创建的语句
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(updateStream);
        }
        return sql;
    }
    /**
     * 创建对应版本的数据库
     *
     * @param createVersion 创建数据库的实体类
     * @param ids           用户id集合
     */
    private void createSuitVersion(@NonNull CreateVersion createVersion, @NonNull String[] ids) throws Exception {
        if (createVersion.getCreateDbs() == null) {
            throw new Exception("createVersion is null");
        }
        List<CreateDb> createDbs = createVersion.getCreateDbs();
        for (CreateDb createDb : createDbs) {
            if (createDb == null) {
                throw new Exception("createDb is null");
            }
            String dbName = createDb.getName();
            if (TextUtils.isEmpty(dbName)) {
                throw new Exception("you need set dbName for sql in update.xml");
            }
            dbName += dbName.endsWith(".db") ? "" : ".db";
            List<String> createSqls = createDb.getCreateSqls();
            if (createSqls == null || createDbs.size() == 0) {
                continue;
            }
            //执行sql
            execSql(createSqls, ids, dbName);
        }

    }

    /**
     * 开始升级数据库
     */
    private void startUpdateDb(@NonNull String[] ids, UpdateDbXml updateDbXml, String currentVersion) {
        try {
            UpdateStep updateStep = analysisUpdateStep(updateDbXml, currentVersion);
            CreateVersion createVersion = analysisCreateXml(updateDbXml, currentVersion);
            if (updateStep != null) {
                updateDb(updateStep, createVersion, ids);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateDb(UpdateStep updateStep, CreateVersion createVersion, String[] ids) throws Exception {
        if (updateStep == null || updateStep.getUpdateDbs() == null) {
            throw new Exception("updateStep is empty");
        }
        List<UpdateDb> updateDbs = updateStep.getUpdateDbs();
        try {
            for (UpdateDb updateDb : updateDbs) {
                updateAndBackup(updateDb, createVersion,ids);
            }
            saveLocalVersion(getCurrentVersion(context));//升级完成，保存版本号
        }catch (Exception e){//升级失败的处理
            e.printStackTrace();
            Log.e(TAG,"数据库更新出现问题了,开始恢复");
            Iterator<String> iterator = recoveryMaps.keySet().iterator();
            while (iterator.hasNext()){
                //1删除更新失败的数据库
                String next = iterator.next();
                File file=new File(next);
                if (file.exists()){
                    file.delete();
                }
                //2拷贝备份数据库过来
                FileUtil.CopySingleFile(recoveryMaps.get(next),next);
                //备份数据库最后会删除
                Log.e(TAG,"数据库恢复完成");
            }


        }

        //删除备份
        String back = backFile.getAbsolutePath() + "/common_back.db";
        File file = new File(back);
        if (file.exists()) {
            file.delete();
        }
        for (String id : ids) {
            if (TextUtils.isEmpty(id)) continue;
            System.out.println("备份删除中");
            File privateBack = new File(backFile.getAbsolutePath() + "/update/" + id + "/back_personal.db");
            if (privateBack.exists()) {
                privateBack.delete();
            }
        }
    }

    /**
     * 开始升级  备份
     *
     * @param updateDb
     * @param createVersion
     * @param ids
     */
    private void updateAndBackup(UpdateDb updateDb, CreateVersion createVersion,@NonNull String[] ids) throws Exception {
       String dbName= updateDb.getName();
         dbName +=dbName.endsWith(".db") ? "" : ".db";
        if (dbName.equalsIgnoreCase(baseDaoFactory.getCommonDbName())) {
            startUpdateAndBackupDb(updateDb, createVersion, dbName, ids,null);
        } else {
            if (ids.length == 0) {
                return;
            }
            for (String id : ids) {
                startUpdateAndBackupDb(updateDb, createVersion, dbName, ids, id);
            }
        }

    }

    /**
     * 开始更新
     * @param updateDb
     * @param createVersion
     * @param dbName
     * @param ids
     * @param id
     * @throws Exception
     */
    private void startUpdateAndBackupDb(UpdateDb updateDb, CreateVersion createVersion, String dbName, @NonNull String[] ids, String id) throws Exception {
        SQLiteDatabase sqliteDateBase = getSqliteDateBase(dbName, id);
        if (sqliteDateBase == null) return;
        String backPath;
        String srcPath;
        //备份数据库
        if (dbName.equalsIgnoreCase(baseDaoFactory.getCommonDbName())) {
            backPath= backFile.getAbsolutePath() + "/common_back.db";
            srcPath=baseDaoFactory.getDefaultDataPath();
            FileUtil.CopySingleFile(srcPath, backPath);
        } else {
            if (TextUtils.isEmpty(id)) return;
            backPath = backFile.getAbsolutePath() + "/update/" + id + "/back_personal.db";
            srcPath=PrivatePathEnums.DATABASE.getPath(id);
            FileUtil.CopySingleFile(srcPath,backPath);
        }
        recoveryMaps.put(srcPath,backPath);//记录升级过的数据库
          Log.e(TAG,dbName+"-"+id);
            //在进行更新之前的一些数据库操作，包括更换表的名称之类的
            List<String> updateBefores = updateDb.getUpdateBefores();
            if (updateBefores != null && updateBefores.size() != 0) {
                for (String updateBefore : updateBefores) {
                    execSqlReplaceWrap(sqliteDateBase, updateBefore);
                }
            }
            //创建新表
            createSuitVersion(createVersion, ids);
            //删除旧表，迁移数据之类的操作
            List<String> updateAfters = updateDb.getUpdateAfters();
            if (updateAfters != null) {
                for (String updateAfter : updateAfters) {
                    execSqlReplaceWrap(sqliteDateBase, updateAfter);
                }
            }
        }



    /**
     * 分析合适的升级步骤
     *
     * @param updateDbXml
     * @param currentVersion
     */
    private UpdateStep analysisUpdateStep(UpdateDbXml updateDbXml, String currentVersion) throws Exception {
        if (updateDbXml == null || updateDbXml.getUpdateSteps() == null) {
            throw new Exception("no update message in update.xml");
        }
        List<UpdateStep> updateSteps = updateDbXml.getUpdateSteps();
        for (UpdateStep updateStep : updateSteps) {
            if (updateStep == null) {
                throw new Exception("no update step message in update.xml");
            }
            String versionFrom = updateStep.getVersionFrom();
            String versionTo = updateStep.getVersionTo();
            if (TextUtils.isEmpty(versionFrom) || TextUtils.isEmpty(versionTo)) {
                throw new Exception("no versionFrom or versionTo  message in update.xml");
            }
            for (String from : versionFrom.split(",")) {
                if (from.equalsIgnoreCase(preVersion) && versionTo.equalsIgnoreCase(currentVersion)) {
                    return updateStep;
                }
            }

        }
        return null;
    }

    /**
     * 创建相应的表
     *
     * @param createSqls
     * @param ids        多用户的id
     * @param dbName
     * @throws Exception
     */
    private void execSql(List<String> createSqls, String[] ids, String dbName) throws Exception {
        if (dbName.equalsIgnoreCase(baseDaoFactory.getCommonDbName())) {
            execSqlWithTransaction(createSqls, dbName, null);
        } else {
            if (ids == null || ids.length == 0) return;
            for (String id : ids) {
                execSqlWithTransaction(createSqls, dbName, id);
            }
        }
    }

    /**
     * 执行创建表的sql语句
     *
     * @param createSqls
     * @param dbName
     * @param id
     * @throws Exception
     */
    private void execSqlWithTransaction(List<String> createSqls, String dbName, String id) throws Exception {
        SQLiteDatabase sqliteDateBase = getSqliteDateBase(dbName, id);
        if (sqliteDateBase==null)return;
        sqliteDateBase.beginTransaction();
        for (String createSql : createSqls) {
            execSqlReplaceWrap(sqliteDateBase, createSql);
        }
        sqliteDateBase.setTransactionSuccessful();
        sqliteDateBase.endTransaction();
    }

    /**
     * 执行sql语句
     *
     * @param sqliteDateBase
     * @param createSql
     */
    private void execSqlReplaceWrap(SQLiteDatabase sqliteDateBase, @NonNull String createSql) throws Exception {
        if (TextUtils.isEmpty(createSql)) {
            return;
        }
        //替换换行等符号
        String newSql = getTrim(createSql);
        if (TextUtils.isEmpty(newSql)) {
            return;
        }
        try {
            Log.e(TAG,"开始执行"+newSql);
            sqliteDateBase.execSQL(newSql);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(TAG,"sql语句出错啦"+newSql);
            throw new Exception("sql语句出错啦"+newSql);
        }
    }

    @NonNull
    private String getTrim(@NonNull String createSql) {
        return createSql.replaceAll("\n\r", "").replaceAll("\n", "").trim();
    }

    private SQLiteDatabase getSqliteDateBase(String dbName, String id) throws Exception {
        if (baseDaoFactory == null || baseDaoFactory.getEasyConfig() == null) {
            throw new Exception("you need call BaseDaoFactory.init at application");
        }
        //存放公共数据的数据库
        if (dbName.equalsIgnoreCase(baseDaoFactory.getCommonDbName())) {
            return baseDaoFactory.getSqLiteDatabase();
        } else if (dbName.equalsIgnoreCase("personal.db")) {
            return SQLiteDatabase.openOrCreateDatabase(PrivatePathEnums.DATABASE.getPath(id), null);
        } else {
            Log.e(TAG, "no named" + dbName + " db");
            return null;
        }


    }

    /**
     * 分析当前所需要的版本
     *
     * @param updateDbXml
     * @param currentVersion
     * @return
     */
    private CreateVersion analysisCreateXml(UpdateDbXml updateDbXml, String currentVersion) {
        if (updateDbXml == null || currentVersion == null) return null;
        List<CreateVersion> createVersions = updateDbXml.getCreateVersions();
        for (CreateVersion createVersion : createVersions) {
            String version = createVersion.getVersion();
            //相同的版本可用逗号隔开
            String[] splitVersion = version.split(",");
            for (String childVersion : splitVersion) {
                if (childVersion.equalsIgnoreCase(currentVersion)) {
                    return createVersion;
                }
            }
        }
        return null;

    }
    public static boolean TEST=false;
    public String getCurrentVersion(Context context) {
        if (TEST){//测试
            return "V003";
        }
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前记录的版本
     *
     * @return
     */
    private boolean getLocalVersion() {
        File file = new File(parentFile, "update.txt");
        boolean isLocal = false;
        if (!file.exists()){
            return isLocal;
        }
        FileReader fileReader;
        try {
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            preVersion = bufferedReader.readLine();
            preVersion = getTrim(preVersion);
            if (!TextUtils.isEmpty(preVersion)) {
                isLocal = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            preVersion="0";
        }
        return isLocal;
    }

}
