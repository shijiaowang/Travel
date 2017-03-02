package simpledao.cityoff.com.easydao;

/**
 * Created by wangyang on 2017/2/13.
 * 数据库配置
 */

public class EasyConfig {
    private String dbName;
    private String updateXmlName;


    public String getUpdateXmlName() {
        return updateXmlName;
    }

    public void setUpdateXmlName(String updateXmlName) {
        this.updateXmlName = updateXmlName;
    }
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
    public String getDbName() {
        return dbName;
    }



    public static class Builder {
        private String dbName;
        private String updateXmlName;
        public Builder setDbName(String dbName) {
            this.dbName=dbName;
            return this;
        }
        public Builder setUpdateXmlName(String xmlName){
            this.updateXmlName=xmlName;
            return this;
        }
        public EasyConfig build(){
            EasyConfig easyConfig=new EasyConfig();
            easyConfig.dbName=this.dbName;
            easyConfig.updateXmlName=this.updateXmlName;

            return easyConfig;
        }
    }
}
