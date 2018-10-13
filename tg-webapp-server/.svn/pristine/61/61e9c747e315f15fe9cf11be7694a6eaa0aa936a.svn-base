package cn.plou.web.system.permission.menu.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuInfo {
    @JsonIgnore
    private String fMId;
    @JsonIgnore
    private String mId;

    //菜单名字m_name
    private String title;

    //页面名称page_name
    private String key;

    //父菜单名字
    private String superKey;

    //排列顺序pagesort
    private Integer sort;

    //菜单类型ismenu
    private Integer type;


    public String getfMId() {
        return fMId;
    }

    public void setfMId(String fMId) {
        this.fMId = fMId;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSuperKey() {
        return superKey;
    }

    public void setSuperKey(String superKey) {
        this.superKey = superKey;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuInfo menuInfo = (MenuInfo) o;
        return Objects.equals(fMId, menuInfo.fMId) &&
                Objects.equals(mId, menuInfo.mId) &&
                Objects.equals(title, menuInfo.title) &&
                Objects.equals(key, menuInfo.key) &&
                Objects.equals(superKey, menuInfo.superKey) &&
                Objects.equals(sort, menuInfo.sort) &&
                Objects.equals(type, menuInfo.type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(fMId, mId, title, key, superKey, sort, type);
    }

    @Override
    public String toString() {
        return "MenuInfo{" +
                "fMId='" + fMId + '\'' +
                ", mId='" + mId + '\'' +
                ", title='" + title + '\'' +
                ", key='" + key + '\'' +
                ", superKey='" + superKey + '\'' +
                ", sort=" + sort +
                ", type=" + type +
                '}';
    }
}
