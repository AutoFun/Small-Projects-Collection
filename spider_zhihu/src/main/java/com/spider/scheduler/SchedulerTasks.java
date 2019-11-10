package com.spider.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.spider.zhihu.service.ZhiHuServiceImpl;

/**
 * 定时任务 
 * @author
 */
@Configuration
@EnableScheduling
@EnableAsync
public class SchedulerTasks {
	public static Logger logger = Logger.getLogger(SchedulerTasks.class);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private ZhiHuServiceImpl zhiHuServiceImpl;
	
	@Async
	@Scheduled(fixedDelay = (60 * 60000*4))//4小时
	public void getSpriderData() {
		logger.info("开始爬取数据{"+sdf.format(new Date())+"}");
		try {
			//爬取知乎数据
			zhiHuServiceImpl.getHuaTi();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		logger.info("结束爬取数据{"+sdf.format(new Date())+"}");
	}
	
}