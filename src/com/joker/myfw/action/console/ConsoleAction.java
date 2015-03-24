package com.joker.myfw.action.console;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;

import com.joker.myfw.action.BaseAction;
import com.joker.myfw.dao.comm.ActionRecordDao;
import com.joker.myfw.dao.console.ConsoleRoleDao;
import com.joker.myfw.dao.console.ConsoleUserDao;
import com.joker.myfw.data.Data;
import com.joker.myfw.util.PasswordUtil;
import com.joker.myfw.vo.comm.ActionRecord;
import com.joker.myfw.vo.console.ConsoleRole;
import com.joker.myfw.vo.console.ConsoleUser;

public class ConsoleAction extends BaseAction{

	private static final long serialVersionUID = -1659896292602373247L;
	
	private static final Logger logger = Logger.getLogger(ConsoleAction.class);
	
	private static final String INDEX = "index";
	private static final String HOME = "home";
	private static final String LOGOUT = "logout";
	
	/**
	 * 后台首页
	 * @return
	 */
	public String index(){
		return INDEX;
	}
	
	/**
	 * 后台个人首页
	 * @return
	 */
	public String home(){
		try {
			ConsoleUser loginUser = (ConsoleUser)request.getSession().getAttribute("console_user");
			ConsoleRole loginRole = new ConsoleRoleDao().get(loginUser.getRole());
			
			setAttribute("loginUser", loginUser);
			setAttribute("loginRole", loginRole);
		} catch (Exception e) {
			logger.error("",e);
		}
		
		return HOME;
	}
	
	/**
	 * 后台退出
	 * @return
	 */
	public String logout(){
		request.getSession().setAttribute("console_user", null);
		return LOGOUT;
	}
	
	/**
	 * 后台用户更改昵称和密码
	 */
	public void updateBySelf(){
		try{
			ConsoleUser loginUser = (ConsoleUser)request.getSession().getAttribute("console_user");
			
			String password = request.getParameter("password");
			String nick = request.getParameter("nick");
			
			ConsoleUserDao consoleUserDao = new ConsoleUserDao();
			ConsoleUser consoleUser = consoleUserDao.get(loginUser.getId());
			if(null != consoleUser){
				if(null != nick){
					consoleUser.setNick(nick);
				}
				if(null != password){
					consoleUser.setPassword(PasswordUtil.MD5(password));
				}
				
				consoleUserDao.update(consoleUser);
				request.getSession().setAttribute("console_user", consoleUser);
				setJSONAttribute("isSuccess", true);
				
				String description = "(昵称: " + nick;
				description += ") (password is null? " + (null == password) + ")";
				new ActionRecordDao().record(ActionRecord.CONSOLE, loginUser.getAccount(), "更改自身信息(后台)", description);
			}else{
				setJSONAttribute("isSuccess", false);
			}
		}catch (Exception e) {
			logger.error("更新用户信息出错", e);
			setJSONAttribute("isSuccess", false);
			setJSONAttribute("errMsg", e.getMessage());
		}
		flushJSONData();
	}
	
	/**
	 * 后台用户上传头像
	 */
	public void uploadAvatar() {
		try{
			ConsoleUser loginUser = (ConsoleUser)request.getSession().getAttribute("console_user");
			
			String imgData = request.getParameter("imgData");
			if(null == imgData){
				setJSONAttribute("isSuccess", false);
				setJSONAttribute("errMsg", "上传数据为空");
			}else{
				imgData = imgData.replace("data:image/png;base64,", "");
				
				String avatar = Data.avatarFolder + loginUser.getId() + "_" + System.currentTimeMillis() + ".png";
				String file = getRootRealPath() + avatar;
				String oldAvatar = loginUser.getAvatar();
				
				BASE64Decoder decoder = new BASE64Decoder();
				byte[] data = decoder.decodeBuffer(imgData);
				
				for (int i = 0; i < data.length; ++i) {  
					if (data[i] < 0) {// 调整异常数据  
						data[i] += 256;  
					}  
				}  
				
				OutputStream out = new FileOutputStream(file);  
				out.write(data);  
				out.flush();  
				out.close();
				
				ConsoleUserDao consoleUserDao = new ConsoleUserDao();
				loginUser = consoleUserDao.get(loginUser.getId());
				loginUser.setAvatar(avatar);
				consoleUserDao.update(loginUser);
				request.getSession().setAttribute("console_user", loginUser);
				
				//删除旧头像图片文件
				try{
					if(oldAvatar.startsWith(Data.avatarFolder)){
						File oldFile = new File(getRootRealPath() + oldAvatar);
						if(null != oldFile && oldFile.exists()){
							oldFile.delete();
						}
					}
				}catch (Exception e) {
					logger.error("删除旧头像出错!", e);
				}
				
				setJSONAttribute("isSuccess", true);
				
				new ActionRecordDao().record(ActionRecord.CONSOLE, loginUser.getAccount(), "上传头像(后台)", "url: " + avatar);
			}
		}catch (Exception e) {
			logger.error("上传头像出错", e);
			setJSONAttribute("isSuccess", false);
			setJSONAttribute("errMsg", e.getMessage());
		}
		flushJSONData();
	}
}
