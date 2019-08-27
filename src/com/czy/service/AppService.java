package com.czy.service;

import java.sql.SQLException;
import java.util.List;

import com.czy.bean.Apps;
import com.czy.dao.AppsDao;
import com.czy.db.DBConnection;
import com.czy.db.AppsDaoImpl;

public class AppService implements AppsDao{

	private DBConnection dbconn = null;

	private AppsDao dao = null;

	public AppService() throws SQLException {
		this.dbconn = new DBConnection();
		this.dao = new AppsDaoImpl(this.dbconn.getConnection());
	}
	
	@Override
	public boolean addApps(Apps apps) throws Exception {
		if (apps != null) {
			return this.dao.addApps(apps);
		}
		return false;
	}
	
	@Override
	public List<Apps> getAllApps() throws Exception {
		return this.dao.getAllApps();
	}
	
	@Override
	public boolean editInfo(Apps apps) throws Exception {
		if (apps != null) {
			return this.dao.editInfo(apps);
		}
		return false;
	}

	@Override
	public boolean deleteApps(int aid) throws Exception {
		if (this.dao.queryById(aid) != null & isInt(aid)) {
			return this.dao.deleteApps(aid);
		}
		return false;
	}
	
	@Override
	public Apps queryById(int aid) throws Exception {
		if (isInt(aid)) {
			return this.dao.queryById(aid);
		}
		return null;
	}
	
	public boolean isInt(int index) {
		if (index == 0) {
			return false;
		}
		String str = String.valueOf(index);
		return str.matches("[0-9]+$");
	}

	@Override
	public List<Apps> getMyApps(int uid) throws Exception {
		if (isInt(uid)) {
			return this.dao.getMyApps(uid);
		}
		return null;
	}

	@Override
	public boolean insertAppright(Apps apps) throws Exception {
		if (apps != null) {
			return this.dao.insertAppright(apps);
		}
		return false;
	}

	@Override
	public Apps getAuthorizenumber(int uid, int aid) throws Exception {
		if (isInt(uid)&&isInt(aid)) {
			return this.dao.getAuthorizenumber(uid,aid);
		}
		return null;
	}

	@Override
	public String getAppusername(String authorizenumber) throws Exception {
		if (authorizenumber!=null) {
			return this.dao.getAppusername(authorizenumber);
		}
		return null;
	}

	@Override
	public String getAppurl(String authorizenumber) throws Exception {
		if (authorizenumber!=null) {
			return this.dao.getAppurl(authorizenumber);
		}
		return null;
	}
}
