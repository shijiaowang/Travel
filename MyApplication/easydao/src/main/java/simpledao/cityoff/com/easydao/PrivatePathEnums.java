package simpledao.cityoff.com.easydao;

import java.io.File;

/**
 * Created by wangyang on 2017/2/17.
 * 管理分库后个人数据库路径
 */

public enum PrivatePathEnums {
    DATABASE(BaseDaoFactory.getInstance().getDbPath());
    private String path;


    PrivatePathEnums(String path) {
        this.path=path;
    }

    /**
     * 创建用户h私有目录，
     * @param userId 用户唯一的数据标识，如id 等等
     * @return
     */
    public String getPath(String userId) {
        File file=new File(this.path,userId);
        if (!file.exists()){
            file.mkdirs();//创建私有目录
        }
        return file.getAbsolutePath()+"/"+BaseDaoFactory.PRIVATE_DB;
    }
}
