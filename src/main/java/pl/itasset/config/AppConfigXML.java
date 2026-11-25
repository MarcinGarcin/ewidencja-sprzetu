package pl.itasset.config;

import java.io.Serializable;

public class AppConfigXML implements Serializable {

    private static final long serialVersionUID = 1L;

    private String authorName;
    private String groupName;
    private String systemName;

    public AppConfigXML() {
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
}
