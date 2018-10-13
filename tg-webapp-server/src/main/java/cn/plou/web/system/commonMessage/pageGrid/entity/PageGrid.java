package cn.plou.web.system.commonMessage.pageGrid.entity;

public class PageGrid extends PageGridKey {
    private String id;

    private String display;

    private String colid;

    private String align;

    private String width;

    private String issort;

    private String hide;

    private Integer colsort;

    private String format;

    private String frozen;

    private String siteType;

    private String others;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display == null ? null : display.trim();
    }

    public String getColid() {
        return colid;
    }

    public void setColid(String colid) {
        this.colid = colid == null ? null : colid.trim();
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align == null ? null : align.trim();
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width == null ? null : width.trim();
    }

    public String getIssort() {
        return issort;
    }

    public void setIssort(String issort) {
        this.issort = issort == null ? null : issort.trim();
    }

    public String getHide() {
        return hide;
    }

    public void setHide(String hide) {
        this.hide = hide == null ? null : hide.trim();
    }

    public Integer getColsort() {
        return colsort;
    }

    public void setColsort(Integer colsort) {
        this.colsort = colsort;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format == null ? null : format.trim();
    }

    public String getFrozen() {
        return frozen;
    }

    public void setFrozen(String frozen) {
        this.frozen = frozen == null ? null : frozen.trim();
    }

    public String getSiteType() {
        return siteType;
    }

    public void setSiteType(String siteType) {
        this.siteType = siteType == null ? null : siteType.trim();
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others == null ? null : others.trim();
    }
}