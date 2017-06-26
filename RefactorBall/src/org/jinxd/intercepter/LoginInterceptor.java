package org.jinxd.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor{

	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		if(arg0.getSession().getAttribute("user")==null){
			//arg0.setCharacterEncoding("utf-8");
			arg0.getSession().setAttribute("msg", "请先登录");
			//转发
			//arg0.getRequestDispatcher(arg0.getContextPath()).forward(arg0, arg1);
			//或者重定向
			arg1.sendRedirect(arg0.getContextPath());
			return false;
		}
		return true;
	}

}
