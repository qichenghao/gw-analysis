/*
 * Beijing happy Information Technology Co,Ltd.
 * All rights reserved.
 * 
 * <p>DataGridModel.java</p>
 */
package com.qtone.gy.dto;

import java.util.ArrayList;
import java.util.List;


public class DataGridDto {

    private Integer pages; // 当前页

    private Integer pageSize; // 每页显示条数

    private String sort; // 排序的字段名

    private String order; // 排序类型 desc , asc

    private Long total; // 总数

    private List rows = new ArrayList(); // 数据

    public DataGridDto() {
        this.pages = 1;
        this.pageSize = 10;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
