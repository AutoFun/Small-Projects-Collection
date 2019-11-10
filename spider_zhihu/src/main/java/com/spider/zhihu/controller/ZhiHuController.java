package com.spider.zhihu.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spider.mapper.ZhiHuDao;
import com.spider.util.PageRequestHelper;
import com.spider.vo.BaseMessage;
import com.spider.vo.MessageHandler;
import com.spider.vo.PageCus;


@Controller
@RequestMapping("/zhihu/")
@SuppressWarnings({"rawtypes","unchecked"})
public class ZhiHuController {
	@Autowired
	private ZhiHuDao zhiHuDao;
	
	@RequestMapping("index")
	public String index(Integer id,ModelMap map,HttpServletRequest request) {
		Pageable pageable =PageRequestHelper.buildPageRequest(request,null);
		Map<String,Object> huati = zhiHuDao.getHuaTiById(id);
		map.put("huati", huati);
		//分页
		PageCus pageList = new PageCus();
		pageList.setNumber(pageable.getPageNumber());
		pageList.setPageSize(pageable.getPageSize());
		Integer count = zhiHuDao.countHuaTiUserById(id);
		pageList.setCount(count);
		if(count%pageable.getPageSize()==0) {
			pageList.setTotalPages(count/pageable.getPageSize());
		}else {
			pageList.setTotalPages(count/pageable.getPageSize()+1);
		}
		List<Map<String,Object>> listMap = zhiHuDao.getHuaTiUserPgeList(id,pageable.getPageNumber()*pageable.getPageSize(),pageable.getPageSize());
		pageList.setList(listMap);
		map.put("pageList", pageList);
		map.put("id", id);
		return "zhihu_list";
	}
	
	@ResponseBody
	@RequestMapping("count")
	public BaseMessage<?> count(Integer id) {
		if(id==null||id==0)return MessageHandler.createSuccessVo("ID不能为空","操作成功");
		Map<String,Object> map = zhiHuDao.getHuaTiById(id);
		return MessageHandler.createSuccessVo(map,"操作成功");
	}
	@ResponseBody
	@RequestMapping("pageList")
	public BaseMessage<?> pageList(Integer id,Integer pageNo,Integer pageSize) {
		if(id==null||id==0)return MessageHandler.createSuccessVo("ID不能为空","操作成功");
		if(pageNo==null||pageNo==0)pageNo=0;
		if(pageSize==null||pageSize==0)pageSize=10;
		Map<String,Object> map = zhiHuDao.getHuaTiById(id);
		return MessageHandler.createSuccessVo(map,"操作成功");
	}
}
