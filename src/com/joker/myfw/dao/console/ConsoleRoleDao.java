package com.joker.myfw.dao.console;

import java.sql.SQLException;
import java.util.List;

import com.joker.myfw.dao.BaseDao;
import com.joker.myfw.vo.console.ConsoleRole;

public class ConsoleRoleDao extends BaseDao<ConsoleRole>{

	public ConsoleRoleDao() {
		super(ConsoleRole.class);
	}
	
	public List<ConsoleRole> listGtId(Long id) throws SQLException{
		String condition = " id > " + id;
		return this.filter(condition, 0, 0, null);
	}
}
