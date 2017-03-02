package simpledao.cityoff.com.easydao.update;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyang on 2017/2/17.
 */

public class CreateVersion {
    private String version;
    private List<CreateDb> createDbs=new ArrayList<>();

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<CreateDb> getCreateDbs() {
        return createDbs;
    }

    public void setCreateDbs(List<CreateDb> createDbs) {
        this.createDbs = createDbs;
    }
    public  CreateVersion(Element element){
        this.version=element.getAttribute("version");
        NodeList createDbs = element.getElementsByTagName("createDb");
        for (int i=0;i<createDbs.getLength();i++){
            Element child = (Element) createDbs.item(i);
            CreateDb createDb = new CreateDb(child);
            this.createDbs.add(createDb);
        }
    }
}
