package simpledao.cityoff.com.easydao.update;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyang on 2017/2/17.
 */

public class UpdateDb {
    private String name;
    private List<String> updateBefores=new ArrayList<>();
    private List<String> updateAfters=new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getUpdateBefores() {
        return updateBefores;
    }

    public void setUpdateBefores(List<String> updateBefores) {
        this.updateBefores = updateBefores;
    }

    public List<String> getUpdateAfters() {
        return updateAfters;
    }

    public void setUpdateAfters(List<String> updateAfters) {
        this.updateAfters = updateAfters;
    }
    public UpdateDb(Element element){
        this.name=element.getAttribute("name");
        NodeList sqlBefores = element.getElementsByTagName("sql_before");
        NodeList sqlAfters = element.getElementsByTagName("sql_after");
        for (int i=0;i<sqlBefores.getLength();i++){
            this.updateBefores.add(sqlBefores.item(i).getTextContent());
        }
        for (int i=0;i<sqlAfters.getLength();i++){
            this.updateAfters.add(sqlAfters.item(i).getTextContent());
        }
    }
}
