package com.spider.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class PageRequestHelper {
	public static PageRequest buildPageRequest(Integer pageNumber, Integer pagzSize, Sort sort) {
		if (pageNumber == null || pageNumber < 1) {
			pageNumber = 1;
		}
		if (pagzSize == null || pagzSize < 1) {
			pagzSize = 20;
		}
		if (sort==null) {
			return new PageRequest(pageNumber - 1, pagzSize);
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}
	public static PageRequest buildPageRequest(HttpServletRequest request,Sort sort) {
		Integer pageNumber = 0;
		Integer pagzSize = 10;
		if(StringUtil.isNotBlank(request.getParameter("pageNumber")) 
				&& StringUtil.isNotBlank(request.getParameter("pagzSize"))
				&& Integer.parseInt(request.getParameter("pageNumber"))>0) {
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"))-1;
			pagzSize = Integer.parseInt(request.getParameter("pagzSize"));
		}
		return new PageRequest(pageNumber,pagzSize,sort);
	}
	public static PageRequest buildPageRequest(HttpServletRequest request,Direction direction, String... properties) {
		Integer pageNumber = 0;
		Integer pagzSize = 10;
		if(StringUtil.isNotBlank(request.getParameter("pageNumber")) 
				&& StringUtil.isNotBlank(request.getParameter("pagzSize"))
				&& Integer.parseInt(request.getParameter("pageNumber"))>0) {
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"))-1;
			pagzSize = Integer.parseInt(request.getParameter("pagzSize"));
		}
		return new PageRequest(pageNumber, pagzSize, direction, properties);
	}
}
