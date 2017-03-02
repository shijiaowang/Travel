package simpledao.cityoff.com.easydao.update;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyang on 2017/2/17.
 *
 * 创建db
 */

public class CreateDb {
    private List<String> createSqls=new ArrayList<>();
    private String name;

    public List<String> getCreateSqls() {
        return createSqls;
    }

    public void setCreateSqls(List<String> createSqls) {
        this.createSqls = createSqls;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public CreateDb(Element element){
        this.name=element.getAttribute("name");
        //获取执行sql语句集合
        NodeList sqlCreateTable = element.getElementsByTagName("sql_createTable");
        for (int i=0;i<sqlCreateTable.getLength();i++){
            this.createSqls.add(sqlCreateTable.item(i).getTextContent());
        }
    }



}
