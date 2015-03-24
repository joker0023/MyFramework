package com.joker.myfw.data;

import java.util.HashMap;
import java.util.Map;

import com.joker.myfw.util.Utils;
import com.joker.myfw.vo.console.ConsoleUser;
import com.joker.myfw.vo.web.WebUser;
import com.joker.myfw.vo.web.WebUserInfo;

public class Data {

	public static final String alias = "myfw";
	
	public static final String dbFileName = "druid.properties";
	
	public static final String logWroot = Utils.getClassPath() + "log";
	
	public static final String distributionFileName = "distribution.properties";
	
	public static final String mailConfFile = "mail.properties";
	
	public static final String ENCODE = "UTF-8"; // 编码
	
	public static final String SHOTDOMAIN = "jokerstation.com";	//短域名
	
	public static final String DOMAIN = "www.jokerstation.com"; // 域名
	
	public static final int PORT_HTTP = 80; // HTTP 80 端口
	
	public static final String defAvatar = "/assets/img/avatar-def.jpg";
	
	public static final String avatarFolder = "/upload/avatar/";
	
	public static Map<Integer, String> webUserStateMap = new HashMap<Integer, String>();
	public static Map<Integer, String> webUserGradeMap = new HashMap<Integer, String>();
	public static Map<Integer, String> consoleUserStateMap = new HashMap<Integer, String>();
	static {
		webUserStateMap.put(WebUser.STATE_UNACTIVE, "未激活");
		webUserStateMap.put(WebUser.STATE_NORMAL, "正常");
		webUserStateMap.put(WebUser.STATE_DISABLE, "停用");
		
		webUserGradeMap.put(WebUserInfo.NORMAL, "普通会员");
		webUserGradeMap.put(WebUserInfo.VIP, "VIP");
		
		consoleUserStateMap.put(ConsoleUser.NORMAL, "正常");
		consoleUserStateMap.put(ConsoleUser.DISABLED, "停用");
	}
	
}
