package com.joker.myfw.action.console;

import java.util.List;

import org.apache.log4j.Logger;

import com.joker.myfw.action.BaseAction;
import com.joker.myfw.dao.comm.ActionRecordDao;
import com.joker.myfw.dao.console.ConsoleRoleDao;
import com.joker.myfw.util.ValidationUtil;
import com.joker.myfw.vo.comm.ActionRecord;
import com.joker.myfw.vo.console.ConsoleRole;
import com.joker.myfw.vo.console.ConsoleUser;

public class PermissionAction extends BaseAction{

	private static final long serialVersionUID = 184616857716244947L;
	
	private static final Logger logger = Logger.getLogger(PermissionAction.class);
	
	private static final String LIST = "list";
	
	/**
	 * 角色列表
	 * @return
	 */
	public String list(){
		try {
			ConsoleRoleDao consoleRoleDao = new ConsoleRoleDao();
			
			int rows = (int)consoleRoleDao.count(new ConsoleRole());
			pager.setTotalRowsAmount(rows);
			List<ConsoleRole> consoleRoleList = consoleRoleDao.list(new ConsoleRole(), pager.getCurrentPage(), pager.getPageSize());
			
			setAttribute("consoleRoleList", consoleRoleList);
		} catch (Exception e) {
			logger.error("查询角色列表出错", e);
		}
		
		return LIST;
	}
	
	/**
	 * 新增角色
	 */
	public void add(){
		ConsoleUser loginUser = (ConsoleUser)request.getSession().getAttribute("console_user");
		
		try {
			String name = request.getParameter("name");
			String description = request.getParameter("description");
			String namespace = request.getParameter("namespace");
			
			if(null != name && null != description && null != namespace &&
					!ValidationUtil.isSQLInjection(name) && !ValidationUtil.isSQLInjection(description) && !ValidationUtil.isSQLInjection(namespace)){
				ConsoleRole consoleRole = new ConsoleRole();
				consoleRole.setName(name);
				consoleRole.setDescription(description);
				consoleRole.setNamespace(namespace);
				
				new ConsoleRoleDao().save(consoleRole);
				
				setJSONAttribute("isSuccess", true);
				
				String recordDescription = "(name: " + name + ") (description: " + description + ") (namespace: " + namespace + ")";
				new ActionRecordDao().record(ActionRecord.CONSOLE, loginUser.getAccount(), "新增角色", recordDescription);
			}else{
				setJSONAttribute("isSuccess", false);
				setJSONAttribute("errMsg", "参数非法");
			}
		} catch (Exception e) {
			logger.error("新增角色出错", e);
			setJSONAttribute("isSuccess", false);
			setJSONAttribute("errMsg", e.getMessage());
		}
		
		flushJSONData();
	}
	
	/**
	 * 修改角色
	 */
	public void update(){
		ConsoleUser loginUser = (ConsoleUser)request.getSession().getAttribute("console_user");
		
		try {
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String description = request.getParameter("description");
			String namespace = request.getParameter("namespace");
			
			if("1".equals(id)){
				return;
			}
			
			if(null != name && null != description && null != namespace &&
					!ValidationUtil.isSQLInjection(name) && !ValidationUtil.isSQLInjection(description) && !ValidationUtil.isSQLInjection(namespace)){
				ConsoleRole consoleRole = new ConsoleRoleDao().get(Long.valueOf(id));
				if(null != consoleRole){
					consoleRole.setName(name);
					consoleRole.setDescription(description);
					consoleRole.setNamespace(namespace);
					
					new ConsoleRoleDao().update(consoleRole);
					setJSONAttribute("isSuccess", true);
					
					String recordDescription = "(id: " + id +  ") (name: " + name + ") (description: " + description + ") (namespace: " + namespace + ")";
					new ActionRecordDao().record(ActionRecord.CONSOLE, loginUser.getAccount(), "修改角色信息", recordDescription);
				}
			}else{
				setJSONAttribute("isSuccess", false);
				setJSONAttribute("errMsg", "参数非法");
			}
		} catch (Exception e) {
			logger.error("修改角色权限出错", e);
			setJSONAttribute("isSuccess", false);
			setJSONAttribute("errMsg", e.getMessage());
		}
		
		flushJSONData();
	}
	
	/**
	 * 删除角色
	 */
	public void delete(){
		ConsoleUser loginUser = (ConsoleUser)request.getSession().getAttribute("console_user");
		
		try {
			String id = request.getParameter("id");
			
			ConsoleRole consoleRole = new ConsoleRoleDao().get(Long.valueOf(id));
			
			new ConsoleRoleDao().delete(Long.valueOf(id));
			setJSONAttribute("isSuccess", true);
			
			if(null != consoleRole){
				String recordDescription = "(id: " + id +  ") (name: " + consoleRole.getName() + ") (description: " + consoleRole.getDescription() + ") (namespace: " + consoleRole.getNamespace() + ")";
				new ActionRecordDao().record(ActionRecord.CONSOLE, loginUser.getAccount(), "删除角色", recordDescription);
			}
		} catch (Exception e) {
			logger.error("删除角色出错", e);
		}
		
		flushJSONData();
	}

}
