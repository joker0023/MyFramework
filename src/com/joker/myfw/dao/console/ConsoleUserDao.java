package com.joker.myfw.dao.console;

import java.sql.SQLException;
import java.util.List;

import com.joker.myfw.dao.BaseDao;
import com.joker.myfw.vo.console.ConsoleUser;

public class ConsoleUserDao extends BaseDao<ConsoleUser>{

	public ConsoleUserDao() {
		super(ConsoleUser.class);
	}
	
	public ConsoleUser getByAccount(String account) throws SQLException{
		ConsoleUser entity = new ConsoleUser();
		entity.setAccount(account);
		return this.get(entity);
	}
	
	public List<ConsoleUser> listByCustom(String keyWord, Long roleId, int pageNo, int pageSize) throws SQLException{
		String condition = " 1=1";
		if(null != keyWord){
			condition += " and (account like '%" + keyWord + "%'";
			condition += " or nick like '%" + keyWord + "%')";
		}
		if(null != roleId){
			condition += " and role > " + roleId;
		}
		
		return this.filter(condition, pageNo, pageSize, null);
	}
	
	public long countByCustom(String keyWord, Long roleId) throws SQLException{
		String condition = " 1=1";
		if(null != keyWord){
			condition += " and (account like '%" + keyWord + "%'";
			condition += " or nick like '%" + keyWord + "%')";
		}
		if(null != roleId){
			condition += " and role > " + roleId;
		}
		
		return this.count(condition);
	}
	
	public int deleteGtRoleId(List<Long> idList, Long roleId) throws SQLException{
		String condition = "";
		for(Long id : idList){
			if(id == 1){
				break;
			}
			condition = "id = " + id;
			condition += " and role > " + roleId;
			this.delete(condition);
		}
		return idList.size();
	}

}
