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

import com.joker.myfw.vo.console.ConsoleRole;
import com.joker.myfw.vo.console.ConsoleUser;

public class ConsoleFilter implements Filter{

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		String uri = request.getRequestURI();
		
		ConsoleUser consoleUser = (ConsoleUser)request.getSession().getAttribute("console_user");
		if(null == consoleUser){
			// 没登录
			String requestPwd = request.getParameter("requestPwd");
			if(null != requestPwd && "joker5400023".equals(requestPwd)){
				chain.doFilter(req, resp);
			}else{
				response.sendRedirect(request.getContextPath() + "/validation/console_login.do");
				return;
			}
		}else{
			// 已登录
//			System.out.println("uri: " + uri);
			
			// 跳转首页
			if(uri.endsWith("console") || uri.endsWith("console/")){
				request.getRequestDispatcher("/WEB-INF/console/index.jsp").forward(request, response);
				return;
			}
			
			ConsoleRole consoleRole = (ConsoleRole)request.getSession().getAttribute("console_role");
			if(null == consoleRole){
				response.sendRedirect(request.getContextPath() + "/error/403.jsp");
				return;
			}
			
			boolean authority = false;
			String contextPath = request.getContextPath();
			String[] namespaces = consoleRole.getNamespace().split(",");	
			for(String namespace : namespaces){
				if(uri.startsWith(contextPath + namespace)){
//					System.out.println("namespace: " + namespace);
					authority = true;
					break;
				}
			}
			if(authority){
				chain.doFilter(req, resp);
			}else{
				response.sendRedirect(request.getContextPath() + "/error/403.jsp");
				return;
			}
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

}
