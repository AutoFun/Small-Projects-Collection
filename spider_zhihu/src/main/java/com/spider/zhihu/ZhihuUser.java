package com.spider.zhihu;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class ZhihuUser implements Serializable{
	private static final long serialVersionUID = -7566826677978626435L;
	
	private String id;
	private String name;
	private String avatar_url;
	private String url_token;
	private String gender;
	private String type;
	private String headline;
	
	private String description;
	private String business;
	private String locations;
	
	private Date careate_time;
}
