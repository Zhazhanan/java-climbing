package tansun.creditx.bts.config;

public class SwaggerConfigInfo {

    /* Nacos Data Id */
    private String dataId;

    /* Nacos group.*/
    private String group;

    /* swagger.enable*/
    private boolean enable;

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "SwaggerConfigInfo{" +
                "dataId='" + dataId + '\'' +
                ", group='" + group + '\'' +
                ", enable=" + enable +
                '}';
    }
}
