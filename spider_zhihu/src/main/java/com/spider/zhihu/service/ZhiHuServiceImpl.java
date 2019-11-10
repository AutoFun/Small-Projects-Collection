package com.spider.zhihu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.spider.mapper.ZhiHuDao;
import com.spider.util.HttpUtil;
import com.spider.util.StringUtil;
import com.spider.zhihu.ZhihuUser;

@Service
public class ZhiHuServiceImpl {
	@Autowired
	private ZhiHuDao zhiHuDao;
	/**
	 * 获取话题
	 * @throws Exception
	 */
	public void getHuaTi() throws Exception {
		List<Map<String, Object>> list =  zhiHuDao.getHuaTi();
		if(list!=null&&list.size()>0) {
			for(Map<String, Object> map:list) {
				Integer id = (Integer) map.get("id");
				String purl = (String) map.get("p_url");
				getAnswers(id,purl);
			}
		}
		//getAnswers(100,"https://www.zhihu.com/api/v4/questions/331936013/answers?include=comment_count,voteup_count&limit=20&offset=0&platform=desktop&sort_by=default");
		//getZhihuUser();
	}
	/**
	 * 获取回答信息
	 * @throws Exception
	 */
	public void getAnswers(Integer huatiId,String url){
		try {
			String string = HttpUtil.httpRequest(url);
			JSONObject jsonObject = JSONObject.parseObject(string);
			JSONObject paging = jsonObject.getJSONObject("paging");
			//总评论数
			Integer totals = (Integer) paging.get("totals");
			//更新回答数
			zhiHuDao.updateTotals(huatiId,totals);
			//分页是否结束
			boolean is_end = (boolean) paging.get("is_end");
			//下一页
			String nextUrl = (String) paging.get("next");
			//评论数据
			JSONArray data = jsonObject.getJSONArray("data");
			for(int i=0;i<data.size();i++) {
				JSONObject json = data.getJSONObject(i);
				JSONObject author = json.getJSONObject("author");
				//comment_count 评论数
				//voteup_count 点赞数
				Integer comment_count = (Integer) json.get("comment_count");
				Integer voteup_count = (Integer) json.get("voteup_count");
				
				String url_token = author.getString("url_token");
				String avatar_url = author.getString("avatar_url");
				String id = author.getString("id");//主键
				String name = author.getString("name");
				String gender = author.getString("gender");
				String type = author.getString("type");
				String headline = author.getString("headline");
				if("0".equals(gender)) {
					gender ="女";
				}else if("1".equals(gender)) {
					gender ="男";
				}else {
					gender ="未知";
				}
				//保存回答人员信息
				ZhihuUser zhihuUser = zhiHuDao.getZhihuUser(id);
				boolean isInsert = false;
				if(zhihuUser==null) {
					zhihuUser = new ZhihuUser();
					isInsert = true;
				}
				zhihuUser.setId(id);
				zhihuUser.setAvatar_url(avatar_url);
				zhihuUser.setGender(gender);
				zhihuUser.setName(name);
				zhihuUser.setUrl_token(url_token);
				zhihuUser.setType(type);
				zhihuUser.setHeadline(headline);
				if(isInsert) zhiHuDao.insertUser(zhihuUser);
				else zhiHuDao.updateUser(zhihuUser);
				//处理中间表
				Integer count = zhiHuDao.countHuatiUser(huatiId, id);
				Map<String,Object> map = new HashMap<>();
				map.put("huati_id", huatiId);
				map.put("user_id", id);
				map.put("comment_count", comment_count);
				map.put("voteup_count", voteup_count);
				if(count!=null&&count!=0) zhiHuDao.updateHuatiUser(map);
				else zhiHuDao.insertHuatiUser(map);
			}
			if(!is_end) {
				int rom = (int)(1+Math.random()*(10-1+1));
				Thread.sleep(rom*1000);
				getAnswers(huatiId,nextUrl);
			}else {
				getZhihuUser();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取知乎的个人信息
	 * @throws Exception
	 */
	public void getZhihuUser() throws Exception {
		List<ZhihuUser> list = zhiHuDao.getZhihuUserList();
		if(list!=null&&list.size()>0) {
			for(ZhihuUser zu:list) {
				if(StringUtil.isNotBlank(zu.getUrl_token())&&
						(StringUtil.isBlank(zu.getBusiness())||StringUtil.isBlank(zu.getLocations()))) {
					String url = "https://www.zhihu.com/people/"+zu.getUrl_token();
					//获取编辑推荐页
			        Document document=Jsoup.connect(url)
			                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36")
			                .get();
			        //js-initialData
			        Element initElement = document.getElementById("js-initialData");
			        String initData = initElement.html();
			        JSONObject jsonObject = JSONObject.parseObject(initData);
			        JSONObject json2 = jsonObject.getJSONObject("initialState").getJSONObject("entities").getJSONObject("users").getJSONObject(zu.getUrl_token());
			        if(json2!=null) {
			        	//description
				        String description = (String) json2.get("description");
				        //business
				        String business = (String) json2.getJSONObject("business").get("name");
				        //locations
				        JSONArray locationsA =  json2.getJSONArray("locations");
				        String locations = "";
				        if(locationsA!=null&&locationsA.size()>0) {
				        	locations = (String)locationsA.getJSONObject(0).get("name");
				        }
				        zu.setDescription(description);
				        zu.setBusiness(business);
				        zu.setLocations(locations);
				        zhiHuDao.updateUser(zu);
			        }
				}
				int rom = (int)(1+Math.random()*(10-1+1));
				Thread.sleep(rom*1000);
			}
		}
	}
}
