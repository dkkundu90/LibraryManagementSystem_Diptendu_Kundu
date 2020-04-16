package com.handler.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.data.bean.AdminBean;
import com.gen.exception.ServiceException;
import com.gen.util.AppLogger;
import com.gen.util.LoadProperties;
import com.logic.service.LogInService;
import com.logic.service.Implementation.LogInServiceImpl;

public class LogInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = null;
	private LoadProperties properties;
	
	private LogInService logInService;
	private AdminBean adminBean;
	
    public LogInServlet() {
        super();
    }

    public void init() {
    	logger = AppLogger.getLogger();
    	properties = new LoadProperties();
	}
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info(properties.getPropertyForValue("servletEntry") + LogInServlet.class);
		adminBean = new AdminBean();
		logInService = new LogInServiceImpl();
		String adminName = request.getParameter("adminName");
		String password = request.getParameter("password");
		
		if (adminName != "" && password != "") {
			adminBean.setAdminName(adminName);
			adminBean.setPassword(password);
			
			try {
				adminBean = logInService.readLogInInfo(adminBean);
				request.setAttribute("basicUserInfo", adminBean);
				if (adminBean.getAdminId()!= null) {
					HttpSession session = request.getSession();
					session.setAttribute("loggedOnUser", adminBean);
					session.setMaxInactiveInterval(Integer.parseInt(properties.getPropertyForValue("sessionTimeout")));
				}
			} catch (ServiceException serviceException) {
				logger.error((serviceException.toString() + "\n" + serviceException.getMessage()));
			}
		} else {
			request.getSession(false).invalidate();
		}
		
		request.setAttribute("page", LogInServlet.class);
		request.getRequestDispatcher(properties.getPropertyForValue("handelRequest")).forward(request, response);
		logger.info(properties.getPropertyForValue("servletExit") + LogInServlet.class);
	}
}