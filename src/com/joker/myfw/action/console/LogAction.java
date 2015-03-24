package com.joker.myfw.action.console;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.URL;

import org.apache.log4j.Logger;

import com.joker.myfw.action.BaseAction;
import com.joker.myfw.data.Data;

public class LogAction extends BaseAction{

	private static final long serialVersionUID = 4407182902051934408L;
	
	private static final Logger logger = Logger.getLogger(LogAction.class);

	private static final String INFO = "info";
	
	private static final String[] logurls = {
		"http://localhost:81",		//tomcat1
		"http://localhost:82",		//tomcat2
		"http://localhost:83",		//tomcat3
		"http://localhost:84"		//tomcat4
	};
	
	/**
	 * 获取部分log,用于页面显示
	 * 对页面开放接口
	 * @return
	 */
	public String info(){
		String type = request.getParameter("type");
		String tomcat = request.getParameter("tomcat");
		try{
			String logurl = logurls[Integer.valueOf(tomcat)-1] + request.getContextPath() + "/console/log/getInfoLog.do?type=" + type;
//			String logurl = "http://localhost" + request.getContextPath() + "/console/log/getInfoLog.do?type=" + type;
			
			String log = getUrlRtnInfo(logurl);
			
			setAttribute("log", log);
			setAttribute("tomcat", tomcat);
			setAttribute("type", type);
		}catch (Exception e) {
			logger.error("获取log文件info出错", e);
		}
		
		return INFO;
	}
	
	/**
	 * 下载log
	 * 对页面开放接口
	 */
	public void download(){
		String type = request.getParameter("type");
		String tomcat = request.getParameter("tomcat");
		try{
			response.setHeader("Content-Disposition" , "attachment;fileName=" + type + ".log" );
			
			String logurl = logurls[Integer.valueOf(tomcat)-1] + request.getContextPath() + "/console/log/getDownloadLog.do?type=" + type;
//			String logurl = "http://localhost" + request.getContextPath() + "/console/log/getDownloadLog.do?type=" + type;
			
			String log = getUrlRtnInfo(logurl);
			
			getHtmlPrintWriter(response).write(log.toCharArray());
		}catch (Exception e) {
			logger.error("下载log文件出错", e);
		}
	}
	
	/**
	 * 获取部分log信息,对内网开放接口
	 * @throws IOException
	 */
	public void getInfoLog() throws IOException{
		String type = request.getParameter("type");
		
		RandomAccessFile rf = null;
		try{
			String filePath = Data.logWroot + "/" + type + ".log";
			
			String logStr = "";
			rf = new RandomAccessFile(filePath,"r");
			
			long len = rf.length();				//文件长度
			long start = rf.getFilePointer();	//开始位
			long nextend = start + len - 1;		//文件指针定位
			String line = null;					//存储一行的信息
			
			if(nextend >= 0){
				rf.seek(nextend);					//定位
				int c = -1;							//读取的一个字符
				int i = 0;							//读取的行数
				while(nextend > start){
					if(i > 400){
						break;
					}
					c = rf.read();
					if(c=='\n'||c=='\r'){
						i++;
						line = rf.readLine();
						if(null != line && line.length() > 0){
							line = new String(line.getBytes("ISO-8859-1"), "UTF-8");
							logStr = line + "<br>" + logStr;
						}
					}
					nextend--;
					rf.seek(nextend);
					if(nextend==0){//当文件指针退至文件开始处，跳出
						line = rf.readLine();
						if(null != line && line.length() > 0){
							line = new String(line.getBytes("ISO-8859-1"), "UTF-8");
							logStr = line + "<br>" + logStr;
						}
						break;
					}
				}
			}
			
			getHtmlPrintWriter(response).write(logStr);
		}catch (Exception e) {
			logger.error("获取log文件出错", e);
		}finally{
			if(null != rf){
				rf.close();
			}
		}
	}
	
	/**
	 * 获取下载的log信息,对内网开放接口
	 * @throws IOException
	 */
	public void getDownloadLog() throws IOException{
		String type = request.getParameter("type");
		
		FileReader reader = null;
		try{
			String filePath = Data.logWroot + "/" + type + ".log";
			
			reader = new FileReader(new File(filePath));
			char[] tmp = new char[1024];
			StringBuffer strbuf = new StringBuffer();
			int size = -1;
			while((size = reader.read(tmp)) != -1){
				strbuf.append(tmp, 0, size);
			}
			getHtmlPrintWriter(response).write(strbuf.toString());
		}catch (Exception e) {
			logger.error("获取log下载文件出错", e);
		}finally{
			if(null != reader){
				reader.close();
			}
		}
	}
	
	/**
	 * 根据url请求,得到响应数据
	 * @param url
	 * @return
	 * @throws Exception
	 */
	private String getUrlRtnInfo(String url) throws Exception{
		URL conn = new URL(url + "&requestPwd=joker5400023");
		conn.openConnection();
		InputStream in = conn.openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
		
		String line = null;
		StringBuilder logBuf = new StringBuilder();
		
		while(null != (line = br.readLine())){
			logBuf.append(line);
			logBuf.append("\n");
		}
		
		br.close();
		in.close();
		
		return logBuf.toString();
	}
}
