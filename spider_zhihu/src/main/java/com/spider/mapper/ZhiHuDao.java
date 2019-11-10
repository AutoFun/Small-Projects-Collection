package com.spider.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.spider.zhihu.ZhihuUser;

@Mapper
public interface ZhiHuDao {
	/**
	 * 获取话题信息
	 * @return
	 */
	List<Map<String, Object>> getHuaTi();
	Map<String, Object> getHuaTiById(@Param("id")Integer id);

	void updateTotals(@Param("id")Integer id, @Param("totals")Integer totals);
	
	ZhihuUser getZhihuUser(@Param("id")String id);
	
	List<ZhihuUser> getZhihuUserList();
	
	Integer countHuatiUser(@Param("huati_id")Integer huati_id,@Param("user_id")String user_id);
	
	void insertHuatiUser(@Param("params") Map<String, Object>params);
	
	void updateHuatiUser(@Param("params") Map<String, Object>params);
	
	void insertUser(@Param("params") ZhihuUser params);
	
	void updateUser(@Param("params") ZhihuUser params);
	
	List<Map<String, Object>> getHuaTiUserPgeList(@Param("huatiId")Integer huatiId,@Param("start") int start,@Param("end") int end);
	
	Integer countHuaTiUserById(@Param("huatiId")Integer huatiId);
}
