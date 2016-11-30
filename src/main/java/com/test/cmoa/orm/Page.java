package com.test.cmoa.orm;

import java.util.List;

public class Page<T> {
	private int pageNo;
	private int pageSize = 3;

	private int totalElements;
	private List<T> content;
	public static final Integer PAGE_SIZE_SMALL = 5;
	public static final Integer PAGE_SIZE_MIDDEL = 10;
	public static final Integer PAGE_SIZE_BIG = 20;
	public void setPageNo(int pageNo) {
		if (pageNo < 1) {
			pageNo = 1;
		}
		this.pageNo = pageNo;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;

		// 校验 pageNo 的合法性
		if (this.pageNo > getTotalPages()) {
			this.pageNo = getTotalPages();
		}
	}

	public int getTotalElements() {
		return totalElements;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public List<T> getContent() {
		return content;
	}

	// 获取总的页数
	public int getTotalPages() {
		// return (this.totalElements + this.pageSize - 1) / this.pageSize;
		int totalPages = 0;

		totalPages = this.totalElements / this.pageSize;
		if (this.totalElements % this.pageSize != 0) {
			totalPages++;
		}

		return totalPages;
	}

	public boolean isHasNextPage() {
		return this.pageNo < getTotalPages();
	}

	public int getNextPage() {
		if (isHasNextPage()) {
			return this.pageNo + 1;
		}

		return this.pageNo;
	}

	public boolean isHasPrevPage() {
		return this.pageNo > 1;
	}

	public int getPrevPage() {
		if (isHasPrevPage()) {
			return this.pageNo - 1;
		}

		return this.pageNo;
	}
}
