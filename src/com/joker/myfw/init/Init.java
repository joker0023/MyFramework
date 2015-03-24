package com.joker.myfw.init;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.LogManager;

import com.joker.myfw.data.Data;
import com.joker.myfw.util.MailUtil;
import com.joker.myfw.util.Utils;
import com.joker23.orm.connection.ConnectionFactory;

public class Init implements ServletContextListener{

	private static String logRoot = "logRoot";
	
	public static MailUtil mail;
	
	static {
		// 初始化系统，配置log4j的log文件输出位置
		String logWroot = Data.logWroot;
		System.setProperty(logRoot, logWroot);
		System.out.println(": success to set the system property. key=" + logRoot + ", value=" + logWroot);
	}
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		//加载数据库配置文件
		try{
			ConnectionFactory.init(Data.alias, Data.dbFileName);
			
			// 读取配置文件,获得默认值
			getDistProperties();
			
			// 初始化邮件配置
			initMail();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.print(Utils.getCurrentLineMessage(new Exception()));
		System.out.println(":clean SystemValue Completely,key=" + logRoot + ", value=" + System.getProperty(logRoot));
		System.clearProperty(logRoot);
		LogManager.shutdown();
	}

	
	private static void getDistProperties() {
		try{
			PropertyLoader pl = new PropertyLoader();
			pl.initialize(Data.distributionFileName);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void initMail(){
		PropertyLoader p = new PropertyLoader();
		p.initialize(Data.mailConfFile);
		mail = new MailUtil(p.getParameter("mailServer"),
				p.getParameter("from"),
				p.getParameter("username"),
				p.getParameter("password"),
				p.getParameter("nick"));
	}
	
	/**
	 * 內部類，讀取property
	 */
	static class PropertyLoader {

		private Properties property = new Properties();

		public void initialize(String initFile) {
			try {
				InputStream in = PropertyLoader.class.getClassLoader().getResourceAsStream(initFile);
				property.load(in);
				System.out.println("%====success to init the properties file =====%");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("%====fail to init the properties file=====%");
			}
		}

		public String getParameter(String param) {
			return property.getProperty(param);
		}
	}

}
