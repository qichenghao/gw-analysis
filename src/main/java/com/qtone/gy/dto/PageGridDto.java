/*
 * Beijing happy Information Technology Co,Ltd.
 * All rights reserved.
 * 
 * <p>DataGridModel.java</p>
 */
package com.qtone.gy.dto;



public class PageGridDto {

    private Integer currentPage; // 当前页

    private Integer pageSize; // 每页显示条数

    private Long total; // 总数


    public PageGridDto() {
        this.currentPage = 1;
        this.pageSize = 10;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
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
}
