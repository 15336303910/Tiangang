package cn.plou.web.system.permission.menu.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Menu implements Serializable {
    private String mId;

    private String fMId;

    private String mName;

    private String mUrl;

    private String pageName;

    private String icon;

    private Integer pagesort;

    private Integer ismenu;

    private String perms;

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId == null ? null : mId.trim();
    }

    public String getfMId() {
        return fMId;
    }

    public void setfMId(String fMId) {
        this.fMId = fMId == null ? null : fMId.trim();
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName == null ? null : mName.trim();
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl == null ? null : mUrl.trim();
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName == null ? null : pageName.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Integer getPagesort() {
        return pagesort;
    }

    public void setPagesort(Integer pagesort) {
        this.pagesort = pagesort;
    }

    public Integer getIsmenu() {
        return ismenu;
    }

    public void setIsmenu(Integer ismenu) {
        this.ismenu = ismenu;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms == null ? null : perms.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(mId, menu.mId) &&
                Objects.equals(fMId, menu.fMId) &&
                Objects.equals(mName, menu.mName) &&
                Objects.equals(mUrl, menu.mUrl) &&
                Objects.equals(pageName, menu.pageName) &&
                Objects.equals(icon, menu.icon) &&
                Objects.equals(pagesort, menu.pagesort) &&
                Objects.equals(ismenu, menu.ismenu) &&
                Objects.equals(perms, menu.perms);
    }

    @Override
    public int hashCode() {

        return Objects.hash(mId, fMId, mName, mUrl, pageName, icon, pagesort, ismenu, perms);
    }

    @Override
    public String toString() {
        return "Menu{" +
                "mId='" + mId + '\'' +
                ", fMId='" + fMId + '\'' +
                ", mName='" + mName + '\'' +
                ", mUrl='" + mUrl + '\'' +
                ", pageName='" + pageName + '\'' +
                ", icon='" + icon + '\'' +
                ", pagesort=" + pagesort +
                ", ismenu=" + ismenu +
                ", perms='" + perms + '\'' +
                '}';
    }
}