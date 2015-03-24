package com.joker.myfw.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class ValidationUtil {

	public static boolean isEmail(String email){
		if(StringUtils.isBlank(email)){
			return true;
		}
		Pattern pattern = Pattern.compile("^([\\w\\-])+(\\.[\\w\\-]+)*@([\\w\\-]+\\.)+([a-z]){2,4}$");
		Matcher matcher = pattern.matcher(email);
		if(matcher.find()){
			return true;
		}
		return false;
	}
	
	public static boolean isNumber(String number){
		String regex = "^\\-?(\\d)+(\\.(\\d)+)?$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(number);
		if(m.matches()){
			return true;
		}
		return false;
	}
	
	/**
	 * 校验是否含有sql注入(简单校验)
	 * @param keyWord
	 * @return
	 */
	public static boolean isSQLInjection(String keyWord){
		String regex = "\'|\"|=|>|<|;";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(keyWord);
		if(m.find()){
			return true;
		}
		return false;
	}
	
	/**
	 * 校验是否为日期格式 (例如: 2013-05-10)
	 * @param dateStr
	 * @return
	 */
	public static boolean isDate(String dateStr){
		String regex = "^(\\d){1,4}-(\\d){1,2}-(\\d){1,2}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(dateStr);
		if(m.matches()){
			return true;
		}
		return false;
	}
	

	/**
	 * 返回json字符串
	 * @param jsonStr
	 * @return
	 */
	public static String getJson(String jsonStr){
		String json = "{}";
		Matcher m = Pattern.compile("\\{[\\s\\S]*\\}").matcher(jsonStr);
		if(m.find()){
			json = m.group();
		}
		return json;
	}
}
