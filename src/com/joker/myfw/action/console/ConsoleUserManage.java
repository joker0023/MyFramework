package com.joker.myfw.action.console;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.joker.myfw.action.BaseAction;
import com.joker.myfw.dao.comm.ActionRecordDao;
import com.joker.myfw.dao.console.ConsoleRoleDao;
import com.joker.myfw.dao.console.ConsoleUserDao;
import com.joker.myfw.data.Data;
import com.joker.myfw.util.PasswordUtil;
import com.joker.myfw.util.ValidationUtil;
import com.joker.myfw.vo.comm.ActionRecord;
import com.joker.myfw.vo.console.ConsoleRole;
import com.joker.myfw.vo.console.ConsoleUser;

public class ConsoleUserManage extends BaseAction{
	
	private static final long serialVersionUID = 7889575885524146623L;

	private final static Logger logger = Logger.getLogger(ConsoleUserManage.class);
	
	private final static String LIST = "list";
	private final static String EDIT = "edit";

	/**
	 * 后台用户列表
	 * @return
	 */
	public String list(){
		try{
			ConsoleUser loginUser = (ConsoleUser)request.getSession().getAttribute("console_user");
			
			ConsoleRoleDao consoleRoleDao = new ConsoleRoleDao();
			ConsoleUserDao consoleUserDao = new ConsoleUserDao();
			
			String searchVal = request.getParameter("searchVal");
			if(null != searchVal && ValidationUtil.isSQLInjection(searchVal)){
				searchVal = null;
			}
			
			List<ConsoleRole> roleList = consoleRoleDao.listAll();
			int totalRows = (int)consoleUserDao.countByCustom(searchVal, loginUser.getRole());
			pager.setTotalRowsAmount(totalRows);
			List<ConsoleUser> userList = consoleUserDao.listByCustom(searchVal, loginUser.getRole(), pager.getCurrentPage(), pager.getPageSize());
			
			Map<Long, String> roleMap = new HashMap<Long, String>();
			for(int i = roleList.size() - 1; i >=0; i--){
				ConsoleRole role = roleList.get(i);
				roleMap.put(role.getId(), role.getName());
				if(loginUser.getRole() >= role.getId()){
					roleList.remove(i);
				}
			}
			for(ConsoleUser user : userList){
				user.setRoleName(roleMap.get(user.getRole()));
			}
			
			setAttribute("roleList", roleList);
			setAttribute("userList", userList);
			setAttribute("searchVal", searchVal);
			setAttribute("consoleUserStateMap", Data.consoleUserStateMap);
		}catch (Exception e) {
			logger.error("查询用户列表出错", e);
		}
		
		return LIST;
	}
	
	/**
	 * 新增后台用户
	 */
	public void add(){
		try {
			ConsoleUser loginUser = (ConsoleUser)request.getSession().getAttribute("console_user");
			String account = request.getParameter("account");
			String password = request.getParameter("password");
			String role = request.getParameter("role");
			String state = request.getParameter("state");
			
			if(null == account || null == password || null == role || null == state){
				setJSONAttribute("isSuccess", false);
				setJSONAttribute("errMsg", "参数错误");
			}else if(account.trim() == "" || password.trim() == "" || role.trim() == "" || state.trim() == ""){
				setJSONAttribute("isSuccess", false);
				setJSONAttribute("errMsg", "参数错误");
			}else if(loginUser.getRole() >= Long.valueOf(role)){
				setJSONAttribute("isSuccess", false);
				setJSONAttribute("errMsg", "权限不足");
			}else{
				ConsoleUserDao consoleUserDao = new ConsoleUserDao();
				ConsoleUser consoleUser = consoleUserDao.getByAccount(account);
				if(null != consoleUser){
					setJSONAttribute("isSuccess", false);
					setJSONAttribute("errMsg", "用户名不能重复");
				}else{
					consoleUser = new ConsoleUser();
					consoleUser.setAccount(account);
					consoleUser.setPassword(PasswordUtil.MD5(password));
					consoleUser.setNick(account);
					consoleUser.setAvatar(Data.defAvatar);
					consoleUser.setRole(Long.valueOf(role));
					consoleUser.setState(Integer.valueOf(state));
					consoleUser.setLastLoginIp(getIpAddr());
					consoleUser.setCreateTime(new Date());
					consoleUser.setLastLoginTime(new Date());
					
					consoleUserDao.save(consoleUser);
					
					setJSONAttribute("isSuccess", true);
					
					new ActionRecordDao().record(ActionRecord.CONSOLE, loginUser.getAccount(), "新增后台用户", "用户名: " + account);
				}
			}
		} catch (Exception e) {
			logger.error("添加新用户出错", e);
			setJSONAttribute("isSuccess", false);
			setJSONAttribute("errMsg", e.getMessage());
		}
		
		flushJSONData();
	}
	
	/**
	 * 获取后台编辑用户信息
	 * @return
	 */
	public String edit(){
		try {
			ConsoleUser loginUser = (ConsoleUser)request.getSession().getAttribute("console_user");
			String id = request.getParameter("id");
			if(null != id){
				ConsoleUser consoleUser = new ConsoleUserDao().get(Long.valueOf(id));
				if(loginUser.getRole() < consoleUser.getRole()){
					setAttribute("consoleUser", consoleUser);
				}
				
				List<ConsoleRole> roleList = new ConsoleRoleDao().listGtId(loginUser.getRole());
				setAttribute("roleList", roleList);
				setAttribute("consoleUserStateMap", Data.consoleUserStateMap);
			}
		} catch (Exception e) {
			logger.error("查找用户出错", e);
		}
		
		return EDIT;
	}

	/**
	 * 编辑保存后台用户信息
	 */
	public void update(){
		try {
			ConsoleUser loginUser = (ConsoleUser)request.getSession().getAttribute("console_user");
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String role = request.getParameter("role");
			String state = request.getParameter("state");
			
			
			if(null != id && null != role && null != state){
				ConsoleUserDao consoleUserDao = new ConsoleUserDao();
				ConsoleUser consoleUser = consoleUserDao.get(Long.valueOf(id));
				if(null != consoleUser && loginUser.getRole() < consoleUser.getRole() && loginUser.getRole() < Long.valueOf(role)){
					if(null != password && password.trim().length() > 0){
						consoleUser.setPassword(PasswordUtil.MD5(password));
					}
					consoleUser.setRole(Long.valueOf(role));
					consoleUser.setState(Integer.valueOf(state));
					consoleUserDao.update(consoleUser);
					setJSONAttribute("isSuccess", true);
					
					try {
						String description = "用户名:" + consoleUser.getAccount();
						description += " (role: " + role + ") (";
						description += Data.consoleUserStateMap.get(Integer.valueOf(state)) + " state: " + state + ") ";
						new ActionRecordDao().record(ActionRecord.CONSOLE, loginUser.getAccount(), "编辑后台用户信息", description);
					} catch (Exception e) {
						
					}
				}else{
					setJSONAttribute("isSuccess", false);
					setJSONAttribute("errMsg", "权限不足");
				}
			}
		} catch (Exception e) {
			logger.error("更新用户信息出错", e);
			setJSONAttribute("isSuccess", false);
			setJSONAttribute("errMsg", e.getMessage());
		}
		
		flushJSONData();
	}
	
	/**
	 * 删除后台用户
	 */
	public void del(){
		try {
			ConsoleUser loginUser = (ConsoleUser)request.getSession().getAttribute("console_user");
			String ids = request.getParameter("ids");
			
			if(null != ids){
				String[] idArr = ids.split(",");
				List<Long> idList = new ArrayList<Long>();
				for(String str : idArr){
					if(null != str && str.trim().length() > 0){
						idList.add(Long.valueOf(str));
					}
				}
				
				if(idList.size() > 0){
					List<ConsoleUser> oldUserList = new ConsoleUserDao().list(idList, "account");
					int count = new ConsoleUserDao().deleteGtRoleId(idList, loginUser.getRole());
					
					String description = "(count: " + count + " )";
					if(count > 0){
						description += " -- 用户:  ";
						for(ConsoleUser oldUser : oldUserList){
							description += oldUser.getAccount() + ",";
						}
					}
					new ActionRecordDao().record(ActionRecord.CONSOLE, loginUser.getAccount(), "删除后台用户", description);
				}
				setJSONAttribute("isSuccess", true);
			}else{
				setJSONAttribute("isSuccess", false);
				setJSONAttribute("errMsg", "参数错误");
			}
		} catch (Exception e) {
			logger.error("删除用户出错", e);
			setJSONAttribute("isSuccess", false);
			setJSONAttribute("errMsg", e.getMessage());
		}
		flushJSONData();
	}
	
}
