package com.joker.myfw.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.joker.myfw.data.Data;

public class CommFilter implements Filter{

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		
		String serverName = request.getServerName();
		if(Data.SHOTDOMAIN.equalsIgnoreCase(serverName)){
			serverName = Data.DOMAIN;
			String portStr = (request.getServerPort() == Data.PORT_HTTP) ? "" : ":" + request.getServerPort();
			String query = null == request.getQueryString() ? "" : "?" + request.getQueryString();
			String redirectUrl = request.getScheme() + "://" + serverName + portStr
					+ request.getRequestURI() + query;
			
			response.sendRedirect(redirectUrl);
			return;
		}
		
		chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

}
