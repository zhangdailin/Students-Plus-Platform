package com.czy.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.czy.bean.Apps;
import com.czy.bean.User;
import com.czy.dao.BankDao;
import com.czy.dao.UserDao;
import com.czy.factory.DAOFactory;
import com.czy.service.Extractor;;

public class UploadServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public UploadServlet() {
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

		HttpSession session = request.getSession();
		String suid = String.valueOf(session.getAttribute("uid"));
		
		String aname = null;
		String aphoto = null;
		String described = null;
		String adate = null;
		String jarpath = null;
		String destdir = null;
		
		String path = "jsp/upload.jsp";
		String message = "Upload failed, app's name has been used";

		UserDao dao;
		try {
			dao = DAOFactory.getUserServiceInstance();
			int uid = Integer.parseInt(suid);
			User user = dao.queryByuid(uid);
			if (user.getAppright() > 0) {
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);

				// Set the maximum size allowed for a single file: 30M
				upload.setFileSizeMax(30*1024*1024);

				// Set the total size allowed for the file upload form: 80M
				upload.setSizeMax(80*1024*1024);
				upload.setHeaderEncoding("UTF-8");

				if (ServletFileUpload.isMultipartContent(request)){

					@SuppressWarnings("unchecked")
					List<FileItem> list = upload.parseRequest(request);
	    		
					// Traversing data
					for (FileItem item : list)
					{
						if (item.isFormField()){ //True for normal text data
							String fieldName = item.getFieldName();
							String content = item.getString();
							//item.getString("UTF-8"); 
							//System.out.println(fieldName + " " + content);
							if(fieldName.indexOf("name")!=-1){
								aname = content;
							}
							else{
								described = content;
							}
						} 
						else { 
							String SAVE_DIR = "app/"+suid;
							String appPath = request.getServletContext().getRealPath("");
							String name = item.getName(); 

							String id = UUID.randomUUID().toString();
							String savePath = appPath + SAVE_DIR;
							name = suid + id + name; 
							if(name.indexOf(".jpg")!=-1){
								aphoto = name;
							}

							File file = new File(savePath,name);
							File fileParent = file.getParentFile();
							if(!fileParent.exists()){
								fileParent.mkdirs();
							}
							item.write(file);
							if(name.indexOf(".war")!=-1){
								jarpath = appPath + "app/"+ suid +"/"+name;
								destdir = "/var/lib/tomcat8/webapps/"+ name;
								Extractor.ExtractorWar(jarpath, destdir.replace(".war", ""));
							}
							item.delete();
						}
					}
	            
					UserDao userDao;
					try {
						if (uploadapp(uid, aname, aphoto, described, adate, jarpath, destdir)) {
							userDao = DAOFactory.getUserServiceInstance();
							User userdata= userDao.queryByuid(uid);
							int number = userdata.getAppright()-1;
							userDao.updateAppright(uid, number);
							float money = userdata.getMoney();
							BankDao ba = DAOFactory.getBankServiceInstance();
							float change = 10;
							String type = "Upload app earnings";
							if(ba.addBankChange(uid, aname, change, type)){
								money = money + 10;
								if(userDao.queryUpdatemoney(uid, money)){
									message = "Upload success!";
									path = "jsp/myapp.jsp";
								}
							}
							
						}
					} catch (Exception e) {
						e.printStackTrace();
					}   
				} 
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    
		String truePath = request.getContextPath() + "/" + path;
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n" + 
				"<TITLE>Upload</TITLE>");
		out.println("<meta http-equiv=\"refresh\" content=\"5;url=" + truePath+ "\">");
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

	public boolean uploadapp(int uid, String aname, String aphoto, String described, String adate, String jarpath, String destdir)
			throws Exception {
		Apps apps = new Apps();
		
		apps.setUid(uid);
		apps.setAname(aname);
		apps.setAphoto(aphoto);
		apps.setDescribed(described);
		apps.setAdate(adate);
		apps.setJarpath(jarpath);
		apps.setDestdir(destdir);
		return DAOFactory.getAppsServiceInstance().addApps(apps);
	}
}