package com.spider.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.context.annotation.Configuration;

import lombok.extern.java.Log;

@Configuration
@WebListener
@Log
public class ContextListener implements ServletContextListener,ServletContextAttributeListener{
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		try {
			context.setAttribute("base", "/".equals(context.getContextPath())?"":context.getContextPath());
		} catch (Exception e) {
			e.printStackTrace();
			log.info("启动出错：" + e.toString());
		}
	}
	@Override
	public void attributeAdded(ServletContextAttributeEvent scae) {
		
	}
	@Override
	public void attributeRemoved(ServletContextAttributeEvent scae) {
		
	}
	@Override
	public void attributeReplaced(ServletContextAttributeEvent scae) {
		
	}
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
}