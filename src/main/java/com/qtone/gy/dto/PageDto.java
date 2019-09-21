package com.qtone.gy.dto;

import java.io.Serializable;

/**
 * Created by dan on 2017/4/17.
 */
public class PageDto implements Serializable {

    /**   
	 * serialVersionUID : TODO   
	 */  
	private static final long serialVersionUID = 1L;

	private int currentPage; //当前页

    private int pageSize; //每页显示条数

    public PageDto() {
        this.currentPage = 1;
        this.pageSize = 10;
    }

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
    
}
