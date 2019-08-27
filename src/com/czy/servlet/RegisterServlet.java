package com.czy.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.czy.bean.User;
import com.czy.dao.BankDao;
import com.czy.dao.UserDao;
import com.czy.factory.DAOFactory;

public class RegisterServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;

	public RegisterServlet() {
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
		String uname = request.getParameter("uname");
		String passwd = request.getParameter("passwd");
		String email = request.getParameter("Email");
		String path = "jsp/register.jsp";
		String message = "Registration failed, username or Email address has been used";
		try {
			if (uname!=null&&passwd!=null&&email!=null&&resister(uname, passwd, email)) {
				UserDao quid = DAOFactory.getUserServiceInstance();
				User iuser = quid.queryByName(uname);
				int uid = iuser.getUid();
				BankDao ba = DAOFactory.getBankServiceInstance();
				String aname = "Student+";
				float change = 200;
				String type = "New user registration earnings";
				if(ba.addBankChange(uid, aname, change, type)){
					message = "Registration success!";
					path = "jsp/login.jsp";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String truePath = request.getContextPath() + "/" + path;
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>Register</TITLE>");
		out.println("<meta http-equiv=\"refresh\" content=\"5;url=" + truePath
				+ "\">");
		out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n");
		out.println("</HEAD>");
		out.println("  <BODY>");
		out.print("<div align=\"center\">");
		out.print(message);
		out.print("<br/>");
		out.print("Automatically jumping to the corresponding page.");
		out.print("<br/>");
		out.print("Or click here: ");
		out.print("<a href=\"" + truePath + "\"" + ">Return" + "</a>");
		out.print("</div>");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	public void init() throws ServletException {
		// Put your code here
	}

	public boolean resister(String uname, String passwd, String email)
			throws Exception {
		User user = new User();
		user.setUname(uname);
		user.setPasswd(passwd);
		user.setEmail(email);
		return DAOFactory.getUserServiceInstance().addUser(user);
	}

}
