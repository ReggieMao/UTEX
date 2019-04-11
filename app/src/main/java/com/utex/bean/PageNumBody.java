package com.utex.bean;

/**
 * Created by Demon on 2017/6/22.
 */

public class PageNumBody {
    private int page;
    private int pageSize;

    private String pageCompareValue;

    public PageNumBody(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public PageNumBody(int page, int pageSize, String pageCompareValue) {
        this.page = page;
        this.pageSize = pageSize;
        this.pageCompareValue = pageCompareValue;
    }

    public String getPageCompareValue() {
        return pageCompareValue;
    }

    public void setPageCompareValue(String pageCompareValue) {
        this.pageCompareValue = pageCompareValue;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


}
