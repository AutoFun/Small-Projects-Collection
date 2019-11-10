package com.spider.util;

import java.util.HashMap;
import java.util.Map;

public class ConstantUtil {
	
	public final static String NO_INIT = "NO100001";
	public final static String NO_PRE = "NO";

	/**
	 * 账号管理
	 */
	public final static String LOG_ACCOUNT_MANAGE = "0";// 账号管理
	/**
	 * 登录登出
	 */
	public final static String LOG_LOGIN_OUT = "1";// 登录登出
	/**
	 * 业务管理
	 */
	public final static String LOG_BUSINESS_MANAGE = "2";// 业务管理

	public static Map<String, String> TOURISM_API = new HashMap<>();// 甘肃省智慧旅游大数据平台数据共享接口
	// 初始话
	static {
		//信令模型
		TOURISM_API.put("v_xl_00001","今日入省人次统计");
		TOURISM_API.put("v_xl_00002","今日客流统计（人）");
		TOURISM_API.put("v_xl_00003","今日客流与历史客流对");
		TOURISM_API.put("v_xl_00004","今日入省客源地游客分布");
		TOURISM_API.put("v_xl_00005","近30日入省客流趋势(人次）");
		TOURISM_API.put("v_xl_00006","近30日游客来源TOP10(省）");
		TOURISM_API.put("v_xl_00007","近30日游客来源TOP10(市）");
		TOURISM_API.put("v_xl_00008","近30日游客来源增幅TOP5(省）");
		TOURISM_API.put("v_xl_00009","近30日游客来源降幅TOP5(省）");
		TOURISM_API.put("v_xl_00010","近30日游客来源增幅TOP5(市）");
		TOURISM_API.put("v_xl_00011","近30日游客来源降幅TOP5(市）");
		TOURISM_API.put("v_xl_00012","近30日高铁游客主要来源地TOP10");
		TOURISM_API.put("v_xl_00013","近30日驻留时间统计");
		TOURISM_API.put("v_xl_00014","近30日各市州排名及占比");
		TOURISM_API.put("v_xl_00015","近30日各市州客流排名增幅TOP5");
		TOURISM_API.put("v_xl_00016","近30日各市州客流排名降幅TOP5");
		TOURISM_API.put("v_xl_00017","近30日入省游客目的地统计(人次）");
		TOURISM_API.put("v_xl_00018","近7日各市州客流变化（单位：人次）");
		TOURISM_API.put("v_xl_00019","近7日客流流向分析");
		TOURISM_API.put("v_xl_00020","本月与上月入省游客客源地对比(人次）");
		TOURISM_API.put("v_xl_00021","本月与上月入省游客目的地占比(人次）");
		TOURISM_API.put("v_xl_00022","2018年入省客源地游客分布及占比");
		TOURISM_API.put("v_xl_00023","2018年入省目的地游客分布及占比");
		TOURISM_API.put("v_xl_00024","2018年国内累积入省游客分布");
		TOURISM_API.put("v_xl_00025","2018年游客驻留时间占比");
		//景区模型
		TOURISM_API.put("v_jq_00001","入园实时客流（人次）");
		TOURISM_API.put("v_jq_00002","主要景区客流总览");
		TOURISM_API.put("v_jq_00003","今日主要景区客流统计");
		TOURISM_API.put("v_jq_00004","今日客流与历史客流对比");
		TOURISM_API.put("v_jq_00005","今日主要景区客流TOP5(单位：人次）");
		TOURISM_API.put("v_jq_00006","今日主要景区客流分布(单位：人次）");
		TOURISM_API.put("v_jq_00007","近7日主要景区客流情况(单位：人次）");
		TOURISM_API.put("v_jq_00008","近7日各主要景区客流分布");
		TOURISM_API.put("v_jq_00009","近30日主要景区客流");
		TOURISM_API.put("v_jq_00010","近30日5A主要景区客流情况(单位：人次）");
		TOURISM_API.put("v_jq_00011","本月与上月主要景区客流对比(单位：人次）");
		TOURISM_API.put("v_jq_00012","全年主要景区客流TOP5(单位：人次）");
		//微信模型
		TOURISM_API.put("v_wx_00001","微信粉丝昨日关键指标");
		TOURISM_API.put("v_wx_00002","微信粉丝性别和语言分布");
		TOURISM_API.put("v_wx_00003","微信粉丝省份分布");
		TOURISM_API.put("v_wx_00004","微信粉丝城市分布TOP5");
		TOURISM_API.put("v_wx_00005","微信图文消息昨日关键指标");
		TOURISM_API.put("v_wx_00006","近30天微信粉丝趋势");
		TOURISM_API.put("v_wx_00007","近30天微信图文消息趋势");
		TOURISM_API.put("v_wx_00008","公众号接入情况分析及占比");
		TOURISM_API.put("v_wx_00009","近30日GCI指数排行");
		TOURISM_API.put("v_wx_00010","粉丝分析TOP5");
		TOURISM_API.put("v_wx_00011","近7日图文最热关键词");
		TOURISM_API.put("v_wx_00012","近7日微信粉丝及阅读量分析");
		TOURISM_API.put("v_wx_00013","近7日发文及阅读量Top5");
		//网络模型
		TOURISM_API.put("v_wl_00001","近一月网站流量各指标统计");
		TOURISM_API.put("v_wl_00002","指标");
		TOURISM_API.put("v_wl_00003","流量占比分析");
		TOURISM_API.put("v_wl_00004","各推广平台数据统计");
		TOURISM_API.put("v_wl_00005","资讯网访问用户年龄分布占比");
		TOURISM_API.put("v_wl_00006","网络宣传PC端访问量Top10");
		TOURISM_API.put("v_wl_00007","网络宣传手机端访问量Top10");
		TOURISM_API.put("v_wl_00008","资讯网专题访问量占比Top10");
		TOURISM_API.put("v_wl_00009","资讯网各省访问量占比Top10");
		TOURISM_API.put("v_wl_00010","近一月蚂蜂窝游记浏览量Top10");
		TOURISM_API.put("v_wl_00011","近30日网络宣传文章TOP10");
		//交通模型
		TOURISM_API.put("v_jt_00001","今日入运营车辆统计");
		TOURISM_API.put("v_jt_00002","今日预计旅客人数统计");
		TOURISM_API.put("v_jt_00003","今日省内发车数量Top5(单位：辆）");
		TOURISM_API.put("v_jt_00004","近7日省内发车数量Top5(单位：辆）");
		TOURISM_API.put("v_jt_00005","今日在线车辆占比 ");
		TOURISM_API.put("v_jt_00006","车辆燃料类型占比");
		TOURISM_API.put("v_jt_00007","车辆总里程数占比 ");
		TOURISM_API.put("v_jt_00008","车辆载荷占比");
		TOURISM_API.put("v_jt_00009","今日省内发车数量统计(单位：辆）");
		TOURISM_API.put("v_jt_00010","近7日发车数量趋势变化(单位：辆）");
	}
}
