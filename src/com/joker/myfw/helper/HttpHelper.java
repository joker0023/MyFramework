package com.joker.myfw.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.joker.myfw.data.Data;

public class HttpHelper {

	public static final String PREFIX_WINDOWNS = "win";

	/**
	 * 发起HTTP请求并得到响应
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public static String getHttpResponse(String url, String params)
			throws IOException {
		URL urlObj = new URL(url);
		URLConnection connection = urlObj.openConnection();
		connection.setDoOutput(true);

		// 传参
		if (StringUtils.isNotBlank(params)) {
			OutputStreamWriter out = new OutputStreamWriter(
					connection.getOutputStream(), Data.ENCODE);
			out.write(params);
			out.flush();
			out.close();
		}

		// 响应处理
		String currentLine = "";
		String responseStr = "";
		InputStream inputStream = connection.getInputStream();

		BufferedReader bufReader = new BufferedReader(new InputStreamReader(
				inputStream, Data.ENCODE));
		while ((currentLine = bufReader.readLine()) != null) {
			responseStr += currentLine + "\r\n";
		}

		return responseStr;
	}

	/**
	 * 获取访问者真实IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getRealIpAddress(HttpServletRequest request) {
		if (null == request) {
			return null;
		}
		String ipAddress = null;
		// ipAddress = request.getRemoteAddr();
		ipAddress = request.getHeader("x-forwarded-for");
		if (null == ipAddress || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (null == ipAddress || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (null == ipAddress || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					return null;
				}
				ipAddress = inet.getHostAddress();
			}
		}

		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) {
			// "***.***.***.***".length() = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}
	
	/**
	 * 根据ip获取mac地址
	 * @param ip
	 * @return
	 * @throws Exception 
	 */
	public static String getMacAddress(String ip) throws Exception{
		String mac = "";
		if(isWindowsOS()){
			mac = getMacAddressIPInWin(ip);
			System.out.println("windows -- ip : "+ip+"  mac : "+mac);
		}else{
			mac = getMacAddressIPInLinux(ip);
			System.out.println("linux -- ip : "+ip+"  mac : "+mac);
		}
		return mac;
	}
	
	/**
	 * 根据ip获取mac地址(windows)
	 * @param remotePcIP
	 * @return
	 * @throws Exception 
	 */
	public static String getMacAddressIPInWin(String remotePcIP) throws Exception {
		String str = "";
		String macAddress = null;
		Process pp = Runtime.getRuntime().exec("nbtstat -A " + remotePcIP);
		InputStreamReader ir = new InputStreamReader(pp.getInputStream());
		LineNumberReader input = new LineNumberReader(ir);
		for (int i = 1; i < 100; i++) {
			str = input.readLine();
			if (str != null) {
				if (str.indexOf("MAC Address") > 1) {
					macAddress = str.substring(
							str.indexOf("MAC Address") + 14, str.length());
					break;
				}
			}
		}
		return macAddress;
	}
	
	/**
	 * 根据ip获取mac地址(linux)
	 * @param remotePcIP
	 * @return
	 */
	public static String getMacAddressIPInLinux(String remotePcIP) throws Exception{
		String str = "";
		String macAddress = null;
		Process pp = Runtime.getRuntime().exec("arp -a " + remotePcIP);
		InputStreamReader ir = new InputStreamReader(pp.getInputStream());
		LineNumberReader input = new LineNumberReader(ir);
		for (int i = 1; i < 100; i++) {
			str = input.readLine();
			if (str != null) {
				String mac = getMac(str);
				if(null!=mac){
					macAddress = mac;
					break;
				}
			}
		}
		return macAddress;
	}
	
	private static String getMac(String str){
		Pattern p = Pattern.compile("(?<= )([0-9A-F]{2}:){5}[0-9A-F]{2}(?= )");
		Matcher m = p.matcher(str);
		if(m.find()){
			return m.group();
		}
		return null;
	}
	
	public static boolean isWindowsOS() {
		String os = System.getProperty("os.name");
		if (os.toLowerCase().startsWith(PREFIX_WINDOWNS)) {
			return true;
		}
		return false;
	}
	

}
