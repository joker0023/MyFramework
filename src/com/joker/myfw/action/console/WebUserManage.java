package com.joker.myfw.action.console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.joker.myfw.action.BaseAction;
import com.joker.myfw.dao.comm.ActionRecordDao;
import com.joker.myfw.dao.web.WebUserDao;
import com.joker.myfw.dao.web.WebUserInfoDao;
import com.joker.myfw.data.Data;
import com.joker.myfw.util.ValidationUtil;
import com.joker.myfw.vo.comm.ActionRecord;
import com.joker.myfw.vo.console.ConsoleUser;
import com.joker.myfw.vo.web.WebUser;
import com.joker.myfw.vo.web.WebUserInfo;
import com.joker23.orm.persistence.POJO;

public class WebUserManage extends BaseAction{

	private static final long serialVersionUID = 222745278595551842L;
	
	private static final Logger logger = Logger.getLogger(WebUserManage.class);

	private static final String LIST = "list";
	private static final String DETAIL = "detail";
	
	/**
	 * 前台用户列表
	 * @return
	 */
	public String list(){
		try {
			WebUserDao webUserDao = new WebUserDao();
			WebUserInfoDao webUserInfoDao = new WebUserInfoDao();
			
			String account = request.getParameter("account");
			String userState = request.getParameter("userState");
			if(null != account && ValidationUtil.isSQLInjection(account)){
				account = null;
			}
			if(null == userState || ValidationUtil.isSQLInjection(userState) || "-1".equals(userState)){
				userState = null;
			}
			
			List<Map<String, POJO>> userList = new ArrayList<Map<String,POJO>>();
			if(null != account && account.trim().length() > 0){
				setAttribute("account", account);
				WebUser webUser = webUserDao.getWebUser(account);
				if(null != webUser){
					pager.setTotalRowsAmount(1);
					WebUserInfo webUserInfo = webUserInfoDao.getByUid(webUser.getId());
					
					Map<String, POJO> map = new HashMap<String, POJO>();
					map.put("webUser", webUser);
					map.put("webUserInfo", webUserInfo);
					
					userList.add(map);
				}else{
					pager.setTotalRowsAmount(0);
				}
			}else{
				setAttribute("userState", userState);
				Integer state = null;
				if(null != userState){
					state = Integer.valueOf(userState);
				}
				int totalRows = (int)webUserDao.countByCustom(state);
				pager.setTotalRowsAmount(totalRows);
				List<WebUser> webUserList = webUserDao.listByCustom(state, pager.getCurrentPage(), pager.getPageSize());
				for(WebUser webUser : webUserList){
					Map<String, POJO> map = new HashMap<String, POJO>();
					WebUserInfo webUserInfo = webUserInfoDao.getByUid(webUser.getId());
					map.put("webUser", webUser);
					map.put("webUserInfo", webUserInfo);
					
					userList.add(map);
				}
			}
			
			setAttribute("userList", userList);
			setAttribute("webUserStateMap", Data.webUserStateMap);
			setAttribute("webUserGradeMap", Data.webUserGradeMap);
		} catch (Exception e) {
			logger.error("查询用户列表出错", e);
		}
		
		return LIST;
	}
	
	/**
	 * 前台用户详情
	 * @return
	 */
	public String detail(){
		try {
			String id = request.getParameter("id");
			if(null != id){
				WebUser webUser = new WebUserDao().get(Long.valueOf(id));
				WebUserInfo webUserInfo = new WebUserInfoDao().getByUid(webUser.getId());
				
				setAttribute("webUser", webUser);
				setAttribute("webUserInfo", webUserInfo);
				setAttribute("webUserStateMap", Data.webUserStateMap);
				setAttribute("webUserGradeMap", Data.webUserGradeMap);
			}
		} catch (Exception e) {
			logger.error("查询用户详细信息出错", e);
		}
		
		
		return DETAIL;
	}
	
	/**
	 * 修改前台用户信息
	 */
	public void update(){
		ConsoleUser loginUser = (ConsoleUser)request.getSession().getAttribute("console_user");
		
		try {
			String id = request.getParameter("id");
			String state = request.getParameter("state");
			String grade = request.getParameter("grade");
			String integral = request.getParameter("integral");
			
			if(null != id){
				WebUserDao webUserDao = new WebUserDao();
				WebUserInfoDao webUserInfoDao = new WebUserInfoDao();
				WebUser webUser = webUserDao.get(Long.valueOf(id));
				WebUserInfo webUserInfo = webUserInfoDao.getByUid(webUser.getId());
				
				if(null != state){
					int stateVal = Integer.valueOf(state);
					if(stateVal == WebUser.STATE_UNACTIVE || stateVal == WebUser.STATE_NORMAL ||stateVal == WebUser.STATE_DISABLE){
						webUser.setState(stateVal);
						webUserDao.update(webUser);
					}
				}
				
				if(null != grade){
					int gradeval = Integer.valueOf(grade);
					if(gradeval == WebUserInfo.NORMAL || gradeval == WebUserInfo.VIP){
						webUserInfo.setGrade(gradeval);
					}
				}
				if(null != integral){
					int integralVal = Integer.valueOf(integral);
					webUserInfo.setIntegral(integralVal);
				}
				if(null != grade || null != integral){
					webUserInfoDao.update(webUserInfo);
				}
				
				setJSONAttribute("isSuccess", true);
				
				String description = "";
				try{
					description = "(" + Data.webUserStateMap.get(Integer.valueOf(state)) + " state: " + state + ") (";
					description += Data.webUserGradeMap.get(Integer.valueOf(grade)) + " grade: " + grade + ") ";
					description += "(积分 integral: " + integral + ")";
				}catch (Exception e) {
					description = "(state: " + state + ") (grade: " + grade + ") (integral: " + integral + ")";
				}
				new ActionRecordDao().record(ActionRecord.CONSOLE, loginUser.getAccount(), "修改前台用户信息", description);
			}else{
				setJSONAttribute("isSuccess", false);
				setJSONAttribute("errMsg", "非法操作");
			}
		} catch (Exception e) {
			logger.error("更新用户信息出错", e);
			setJSONAttribute("isSuccess", false);
			setJSONAttribute("errMsg", e.getMessage());
		}
		
		flushJSONData();
	}
}
