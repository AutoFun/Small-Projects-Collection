package com.spider.vo;

/**
 * 生成消息
 */
public class MessageHandler {
	/**
	 * 创建一个请求失败的消息类
	 */
	public static BaseMessage<String> createFailedVo(String msg) {
		return new BaseMessage<String>(false, null, msg,null);
	}

	/**
	 * 创建一个请求成功的消息类
	 */
	public static BaseMessage<String> createSuccessVo(String msg) {
		return new BaseMessage<String>(true, null, msg,null);
	}

	/**
	 * 创建一个请求成功的带数据的消息类
	 */
	public static <T> BaseMessage<T> createSuccessVo(T t, String msg) {
		return new BaseMessage<T>(true, t, msg,null);
	}
	public static <T> BaseMessage<T> createSuccessVo(T t, String msg,Integer count) {
		return new BaseMessage<T>(true, t, msg,count);
	}
}
