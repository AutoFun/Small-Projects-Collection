package com.spider.vo;

import java.util.List;

import lombok.Data;

@Data
@SuppressWarnings("hiding")
public class PageCus<Object> {

	private List<Object> list;
	private int count = 0;	//总条数
	private int pageSize = 10;	//每页行数
	private int totalPages;	//总共多少页
	private int pageNo;	//当前页号
	private int number;//当前页号
	
	public PageCus(){
		
	}
	
}
