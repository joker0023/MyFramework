package com.joker.myfw.dao.web;

import java.sql.SQLException;

import com.joker.myfw.dao.BaseDao;
import com.joker.myfw.vo.web.WebUserInfo;

public class WebUserInfoDao extends BaseDao<WebUserInfo>{

	public WebUserInfoDao(){
		super(WebUserInfo.class);
	}
	
	public WebUserInfo getByUid(Long uid) throws SQLException{
		WebUserInfo entity = new WebUserInfo();
		entity.setUid(uid);
		return this.get(entity);
	}
}
