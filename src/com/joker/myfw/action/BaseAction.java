package com.joker.myfw.action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.joker.myfw.data.Data;
import com.joker.myfw.helper.HttpHelper;
import com.joker.myfw.module.Pager;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport{

	private static final long serialVersionUID = 1559475248322620194L;

	protected static Gson gson = new Gson();
	
	protected HttpServletRequest request = (HttpServletRequest)ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
	protected HttpServletResponse response = (HttpServletResponse)ActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE);
	protected ServletContext application = request.getSession().getServletContext();
	
	protected static final String ENCODING = "UTF-8";

	protected Map<String, Object> data = new HashMap<String, Object>();			// 参数容器
	protected Map<String, Object> jsonData = new HashMap<String, Object>();		// JSON参数容器
	
	protected Pager pager = new Pager(request.getContextPath() + request.getServletPath(),1,10);	//分页	action要设置总行数

	protected void flushJSONData() {
		PrintWriter out = null;
		try {
			out = getHtmlPrintWriter(response);
			out.write(gson.toJson(jsonData));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 输出jpeg
	 * @param input
	 */
	protected void flushImgData(InputStream input) {
		byte[] data = null;
		OutputStream out = null;
		try {
			response.setContentType("image/jpeg;charset=utf-8");
			
			ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
	        byte[] buff = new byte[1024];  
	        int rc = 0;  
	        while ((rc = input.read(buff, 0, 1024)) > 0) {  
	            swapStream.write(buff, 0, rc);  
	        }  
	        data = swapStream.toByteArray();
			
			input.close();
			swapStream.close();
			out = response.getOutputStream();
			out.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 返回JSON格式数据输出对象
	 * 
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public PrintWriter getJsonPrintWriter(HttpServletResponse response)
			throws IOException {
		response.setContentType("application/x-json");
		PrintWriter out = response.getWriter();
		return out;
	}
	
	public OutputStream getOutputStream(HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		return response.getOutputStream();
	}

	/**
	 * 返回HTML文本格式数据输出对象
	 * 
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public PrintWriter getHtmlPrintWriter(HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		return out;
	}
	
	protected void setAttribute(String key, Object value) {
		this.data.put(key, value);
	}
	
	protected void setJSONAttribute(String key, Object value) {
		this.jsonData.put(key, value);
	}
	
	public Map<String, Object> getData() {
		return data;
	}

	public Pager getPager(){
		if(null == pager.getParams()){
			pager.setParams(request);
		}
		return pager;
	}
	
	public void setPager(Pager pager) {
		this.pager = pager;
	}
	
	/**
	 * 获取访问项目的根URL 例如:http://www.baidu.com:8080/
	 * 
	 * @return
	 */
	protected String getRootHostUrl() {
		// 端口处理
		String portStr = (request.getServerPort() == Data.PORT_HTTP) ? "" : ":"
				+ request.getServerPort();
		// 域名处理
		String serverName = request.getServerName();
		serverName = (serverName.equalsIgnoreCase(Data.DOMAIN)) ? Data.DOMAIN
				: serverName;
		// 拼装
		String rootUrl = request.getScheme() + "://" + serverName + portStr
				+ request.getContextPath();

		return rootUrl;
	}

	/**
	 * 获取项目根路径的真实路径
	 * 
	 * @return
	 */
	protected String getRootRealPath() {
		return getRealPath("");
	}

	/**
	 * 获取相对路径的真实路径
	 * 
	 * @param path
	 * @return
	 */
	protected String getRealPath(String path) {
		return ServletActionContext.getServletContext().getRealPath(path);
	}
	
	/**
	 * 获取客户端真实IP地址
	 * 
	 * @param request
	 * @return
	 */
	protected String getIpAddr() {
		return HttpHelper.getRealIpAddress(request);
	}
}
