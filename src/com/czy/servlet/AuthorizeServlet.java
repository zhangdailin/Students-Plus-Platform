package com.czy.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.czy.dao.AppsDao;
import com.czy.factory.DAOFactory;

public class AuthorizeServlet extends HttpServlet {

	public AuthorizeServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); 
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		String authorizenumber = request.getParameter("authorizenumber");
		try {
			String appUrl;
			AppsDao dao = DAOFactory.getAppsServiceInstance();
			String uname = dao.getAppusername(authorizenumber.trim());	
			String destdir = dao.getAppurl(authorizenumber);
			int j = destdir.indexOf("webapps/") + "webapps/".length();
    		appUrl = destdir.substring(j - 1, destdir.length()).replace(".war", "").trim();	
			Cookie cookie = new Cookie("appuname", URLEncoder.encode(uname, "utf-8"));
			cookie.setMaxAge(60*60);
			cookie.setPath(appUrl);
			response.addCookie(cookie);
			response.sendRedirect(appUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void init() throws ServletException {
		// Put your code here
	}

}
