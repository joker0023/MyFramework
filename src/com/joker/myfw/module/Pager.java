package com.joker.myfw.module;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 分页
 * @author Joker
 *
 */
public class Pager implements Serializable{

	private static final long serialVersionUID = 5463874478043858020L;

	// 总行数
	private int totalRowsAmount;
	
	// 每页行数
	private int pageSize = 10;
	
	// 总页数
	private int totalPages = 1;
	
	// 当前页码
	private int currentPage = 1;
	
	// 下一页
	private int nextPage;
	
	// 上一页
	private int previousPage;
	
	// 是否有下一页
	private boolean hasNext;
	
	// 是否有上一页
	private boolean hasPrevious;
	
	// 当前页开始行
	private int pageStartRow;
	
	// 当前页结束行
	private int pageEndRow;
	
	//当前页的提交路径
	private String URL;
	
	//附近几页的页数
	private int[] pageNums;
	
	//保存查询条件  key1=value1&key2=value2
	private String params;
	
	//ajax标记，返回的html dom id
	private String ajaxTag;

	
	/*
	 * **************************************************
	 * 构造函数。
	 * **************************************************
	 */

	public Pager(int totalRows, int currentPage) {
		this.setTotalRowsAmount(totalRows);
		this.setCurrentPage(currentPage);
	}

	public Pager(int totalRows, int currentPage, int pageSize) {
		this.pageSize = pageSize;
		this.setTotalRowsAmount(totalRows);
		this.setCurrentPage(currentPage);
	}
	
	public Pager(int totalRows, int currentPage, int pageSize, String URL) {
		this.pageSize = pageSize;
		this.URL = URL;
		this.setTotalRowsAmount(totalRows);
		this.setCurrentPage(currentPage);
	}
	
	/**
	 * 调用此构造函数后，记得要调用setTotalRowsAmount
	 * 创建一个新的实例 PageController.   
	 *   
	 * @param URL
	 * @param currentPage
	 * @param pageSize
	 */
	public Pager(String URL, int currentPage, int pageSize) {
		this.pageSize = pageSize;
		this.URL = URL;
		this.setCurrentPage(currentPage);
	}
	
	/*
	 * **************************************************
	 * 构造函数。
	 * **************************************************
	 */
	
	
	/**
	 * 设置总行数(其他参数也初始化)
	 * @param rows
	 */
	public void setTotalRowsAmount(int rows) {
		//总行数
		if (rows < 0) {
			totalRowsAmount = 0;
		} else {
			totalRowsAmount = rows;
		}
		
		//总页数
		if (totalRowsAmount % pageSize == 0) {
			totalPages = totalRowsAmount / pageSize;
		} else {
			totalPages = totalRowsAmount / pageSize + 1;
		}
		if(totalPages <= 0){
			totalPages = 1;
		}
		
		//当前页
		if (currentPage > totalPages) {
			this.setCurrentPage(totalPages);
		}
		
		//下一页
		if (currentPage == totalPages) {
			hasNext = false;
			nextPage = currentPage;
		} else {
			hasNext = true;
			nextPage = currentPage + 1;
		}
		
		// 计算当前页开始行和结束行(记录索引从0开始)
		pageStartRow = (currentPage - 1) * pageSize;
		if (currentPage != totalPages) {
			pageEndRow = pageStartRow + pageSize - 1;
		} else {
			pageEndRow = this.totalRowsAmount-1;
		}
		if(pageEndRow < 0){
			pageEndRow = 0;
		}

		//设置显示的几个页码
		setPageNum(5);
	}

	/**
	 * 设置当前页数。(总是大于0)
	 * @param curPage
	 */
	public void setCurrentPage(int curPage) {
		//当前页
		if (curPage <= 0) {
			currentPage = 1;
		} else {
			currentPage = curPage;
		}
		//是否有上一页
		if (currentPage == 1) {
			hasPrevious = false;
		} else {
			hasPrevious = true;
		}
		//设置显示的几个页码
		setPageNum(5);
		
		if(hasPrevious){
			previousPage = currentPage - 1;
		}else{
			previousPage = currentPage;
		}
	}

	public void setPageNum(int nums){
		if(totalPages<=0){
			int[] temp = {1};
			this.pageNums = temp;
		}else if (totalPages > nums) {
			if (currentPage > (nums-1)/2) {
				if(totalPages - currentPage > (nums/2)){
					int startNum = currentPage-((nums-1)/2);
					int[] temp = new int[nums];
					for(int i=0;i<temp.length;i++){
						temp[i] = startNum+i;
					}
					this.pageNums = temp;
				}else {
					int startNum = totalPages-nums+1;
					int[] temp = new int[nums];
					for(int i=0;i<temp.length;i++){
						temp[i] = startNum+i;
					}
					this.pageNums = temp;
				}
			} else {
				int[] temp = new int[nums];
				for (int i = 0; i < temp.length; i++) {
					temp[i] = i + 1;
				}
				this.pageNums = temp;
			}	
			
		}else {
			int[] temp = new int[totalPages];
			for (int i = 0; i < temp.length; i++) {
				temp[i] = i + 1;
			}
			this.pageNums = temp;
		}
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public boolean isHasPrevious() {
		return hasPrevious;
	}

	public int getNextPage() {
		return nextPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPreviousPage() {
		return previousPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getTotalRowsAmount() {
		return totalRowsAmount;
	}

	public int getPageStartRow() {
		return pageStartRow;
	}

	public int getPageEndRow() {
		return pageEndRow;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public void setPreviousPage(int previousPage) {
		this.previousPage = previousPage;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public void setHasPrevious(boolean hasPrevious) {
		this.hasPrevious = hasPrevious;
	}

	public void setPageStartRow(int pageStartRow) {
		this.pageStartRow = pageStartRow;
	}

	public void setPageEndRow(int pageEndRow) {
		this.pageEndRow = pageEndRow;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String url) {
		URL = url;
	}

	public int[] getPageNums() {
		return pageNums;
	}

	public void setPageNums(int[] pageNums) {
		this.pageNums = pageNums;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public void setParams(HttpServletRequest request) {

		StringBuffer sb = new StringBuffer();
		Map<String, String[]> pMap = request.getParameterMap();
		Iterator<String> it = pMap.keySet().iterator();
		while (it.hasNext()) {
			String key  = it.next();	
			String value = request.getParameter(key);
			if ("_".equals(key)) {
				continue;
			}
			if (sb.length() > 0) {
				sb.append("&");
			}
			sb.append(key+"="+value);
		}
		
		this.setParams(sb.toString());
	}

	public String getAjaxTag() {
		return ajaxTag;
	}

	public void setAjaxTag(String ajaxTag) {
		this.ajaxTag = ajaxTag;
	}

}
