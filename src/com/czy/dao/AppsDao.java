package com.czy.dao;

import java.util.List;

import com.czy.bean.Apps;

public interface AppsDao {
	
	// Subscribe the app
	public boolean addApps(Apps apps) throws Exception;
	
	// Return all apps
	public List<Apps> getAllApps() throws Exception;
	
	// Edit items information
	public boolean editInfo(Apps apps) throws Exception;

	public List<Apps> getMyApps(int uid) throws Exception;
	
	// delete items
	public boolean deleteApps(int aid) throws Exception;

	// Find items with id
	public Apps queryById(int aid) throws Exception;

	public boolean insertAppright(Apps apps) throws Exception;

	public Apps getAuthorizenumber(int uid, int aid) throws Exception;
	
	public String getAppusername(String authorizenumber) throws Exception;
	
	public String getAppurl(String authorizenumber) throws Exception;
}
