package simpledao.cityoff.com.easydao.update;


import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyang on 2017/2/17.
 */

public class UpdateStep {
    private String versionFrom;
    private String versionTo;
    private List<UpdateDb> updateDbs=new ArrayList<>();
    public UpdateStep(Element element){
         this.versionFrom=element.getAttribute("versionFrom");
         this.versionTo=element.getAttribute("versionTo");
        NodeList updateDbs = element.getElementsByTagName("updateDb");
        for (int i=0;i<updateDbs.getLength();i++){
            Element updateItem = (Element) updateDbs.item(i);
            UpdateDb updateDb = new UpdateDb(updateItem);
            this.updateDbs.add(updateDb);
        }
    }

    public String getVersionFrom() {
        return versionFrom;
    }

    public void setVersionFrom(String versionFrom) {
        this.versionFrom = versionFrom;
    }

    public String getVersionTo() {
        return versionTo;
    }

    public void setVersionTo(String versionTo) {
        this.versionTo = versionTo;
    }

    public List<UpdateDb> getUpdateDbs() {
        return updateDbs;
    }

    public void setUpdateDbs(List<UpdateDb> updateDbs) {
        this.updateDbs = updateDbs;
    }
}
