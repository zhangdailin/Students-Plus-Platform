package com.czy.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.czy.dao.UserDao;
import com.czy.factory.DAOFactory;
import com.czy.service.CookieUtils;

public class EditInfoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public EditInfoServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		String uid = String.valueOf(session.getAttribute("uid"));

		String email = request.getParameter("Email");
		String passwd = request.getParameter("Password");

		
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

		Cookie cookie = CookieUtils.findCookie(httpServletRequest
              .getCookies(), "appverify");
      if (cookie != null) {
          String username = cookie.getValue().split("\\.")[0];
          username = URLDecoder.decode(username, "utf-8");
          System.out.print(username+"**************");
      }
	
		UserDao userDao;
		try {  
			userDao = DAOFactory.getUserServiceInstance();
			if (userDao.editEmail(Integer.parseInt(uid), email)
					&& userDao.editPasswd(Integer.parseInt(uid), passwd)) {
				response.sendRedirect(request.getContextPath() + "/"
						+ "jsp/userinformation.jsp");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n" + "</HEAD>");
		out.println("  <BODY>");
		out.println("Failed to change personal information, Email address is already in use");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}
}
