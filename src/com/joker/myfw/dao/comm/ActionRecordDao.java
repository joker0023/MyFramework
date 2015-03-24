package com.joker.myfw.dao.comm;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.joker.myfw.dao.BaseDao;
import com.joker.myfw.util.DateUtil;
import com.joker.myfw.vo.comm.ActionRecord;

public class ActionRecordDao extends BaseDao<ActionRecord>{

	public ActionRecordDao(){
		super(ActionRecord.class);
	}
	
	public long record(int position, String account, String type, String description) throws SQLException{
		ActionRecord entity = new ActionRecord();
		entity.setAccount(account);
		entity.setDescription(description);
		entity.setPosition(position);
		entity.setType(type);
		entity.setTime(new Date());
		return this.save(entity);
	}
	
	public List<ActionRecord> listByCustom(int position, String account, Date startTime, Date endTime, int page, int size) throws SQLException{
		String condition = "1 = 1";
		ActionRecord entity = new ActionRecord();
		entity.setPosition(position);
		if(null != account){
			entity.setAccount(account);
		}
		if(null != startTime){
			condition += " and '" + DateUtil.getStringByDateTime(startTime) + "' <= time";
		}
		if(null != endTime){
			condition += " and time <= '" + DateUtil.getStringByDateTime(endTime) + "'";
		}
		return this.filter(entity, condition, page, size, "id desc");
	}
	
	public long countByCustom(int position, String account, Date startTime, Date endTime) throws SQLException{
		String condition = "1 = 1";
		ActionRecord entity = new ActionRecord();
		entity.setPosition(position);
		if(null != account){
			entity.setAccount(account);
		}
		if(null != startTime){
			condition += " and '" + DateUtil.getStringByDateTime(startTime) + "' <= time";
		}
		if(null != endTime){
			condition += " and time < '" + DateUtil.getStringByDateTime(endTime) + "'";
		}
		return this.count(entity, condition);
	}
}
