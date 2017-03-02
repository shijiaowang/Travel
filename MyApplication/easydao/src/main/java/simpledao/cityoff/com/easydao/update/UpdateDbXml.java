package simpledao.cityoff.com.easydao.update;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyang on 2017/2/17.
 */

public class UpdateDbXml {
    /**
     * 创建的版本集合
     */
    private List<CreateVersion> createVersions=new ArrayList<>();
    /**
     * 数据库升级集合
     */
    private List<UpdateStep> updateSteps=new ArrayList<>();

    public UpdateDbXml(Document document){
        NodeList createVersions = document.getElementsByTagName("createVersion");
        for (int i=0;i<createVersions.getLength();i++){
            Element createVersionItem = (Element) createVersions.item(i);
            CreateVersion createVersion=new CreateVersion(createVersionItem);
            this.createVersions.add(createVersion);
        }
        NodeList updateSteps = document.getElementsByTagName("updateStep");
        for (int i=0;i<updateSteps.getLength();i++){
            Element updateStepItem = (Element) updateSteps.item(i);
            UpdateStep updateStep=new UpdateStep(updateStepItem);
            this.updateSteps.add(updateStep);
        }
    }

    public List<CreateVersion> getCreateVersions() {
        return createVersions;
    }

    public void setCreateVersions(List<CreateVersion> createVersions) {
        this.createVersions = createVersions;
    }

    public List<UpdateStep> getUpdateSteps() {
        return updateSteps;
    }

    public void setUpdateSteps(List<UpdateStep> updateSteps) {
        this.updateSteps = updateSteps;
    }
}
