package com.joker.myfw.action.console;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.joker.myfw.action.BaseAction;
import com.joker.myfw.dao.comm.ActionRecordDao;
import com.joker.myfw.util.DateUtil;
import com.joker.myfw.vo.comm.ActionRecord;

public class ActionRecordAction extends BaseAction{

	private static final long serialVersionUID = -4553503357773517457L;
	
	private static final Logger logger = Logger.getLogger(ActionRecordAction.class);
	
	private static final String LIST = "list";
	
	public String list(){
		try{
			String start = request.getParameter("start");
			String end = request.getParameter("end");
			String account = request.getParameter("account");
			String positionStr = request.getParameter("position");
			
			Date startTime = null;
			Date endTime = null;
			int position = ActionRecord.CONSOLE;
			if(null != start && start.trim().length() > 0){
				startTime = DateUtil.getDateByString(start);
			}
			if(null != end && end.trim().length() > 0){
				endTime = DateUtil.getDateByString(end);
			}
			if(null != positionStr && positionStr.trim().length() > 0){
				position = Integer.valueOf(positionStr);
			}
			if(null == account || account.trim().length() == 0){
				account = null;
			}
			
			ActionRecordDao actionRecordDao = new ActionRecordDao();
			int totalRows = (int)actionRecordDao.countByCustom(position, account, startTime, endTime);
			pager.setPageSize(20);
			pager.setTotalRowsAmount(totalRows);
			List<ActionRecord> actionRecordList = actionRecordDao.listByCustom(position, account, startTime, endTime, pager.getCurrentPage(), pager.getPageSize());
			
			setAttribute("actionRecordList", actionRecordList);
			setAttribute("start", start);
			setAttribute("end", end);
			setAttribute("account", account);
			setAttribute("position", positionStr);
		}catch (Exception e) {
			logger.error("查询操作日记出错", e);
		}
		return LIST;
	}

}
