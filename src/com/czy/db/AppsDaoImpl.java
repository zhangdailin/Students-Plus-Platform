package com.czy.db;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;

import com.czy.bean.Apps;
import com.czy.dao.AppsDao;
import java.sql.Statement;

public class AppsDaoImpl implements AppsDao {

	private Connection conn = null;

	private PreparedStatement pstmt = null;

	public AppsDaoImpl(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public boolean addApps(Apps apps) throws Exception {
		pstmt = null;
		String sql = "insert into apps(uid,aname,aphoto,adescription,jarpath,destdir,adate)value(?,?,?,?,?,?,now());";
		int result = 0;
		pstmt = this.conn.prepareStatement(sql);
		
		ResultSet rset = pstmt.executeQuery("select count(aname) from apps;"); 
		rset.next();
		
		pstmt.setInt(1, apps.getUid());
		pstmt.setString(2, apps.getAname());
		pstmt.setString(3, apps.getAphoto());
		pstmt.setString(4, apps.getDescribed());
		pstmt.setString(5, apps.getJarpath());
		pstmt.setString(6, apps.getDestdir());

		result = pstmt.executeUpdate();
		pstmt.close();
		if (result == 1) {
			return true;
		}
		return false;
	}
	
	@Override
	public List<Apps> getAllApps() throws Exception {
		String sql = "select * from apps order by aid desc;";
		Statement st = null;
		ResultSet rs = null;
		st = (Statement) conn.createStatement();
		rs = st.executeQuery(sql);
		List<Apps> AppsList = new ArrayList<Apps>();
		Apps apps;
		while (rs.next()) {
			int aid = rs.getInt("aid");
			int uid = rs.getInt("uid");
			String aname = rs.getString("aname");
			String aphoto = rs.getString("aphoto");
			String described = rs.getString("adescription");
			String adate = rs.getDate("adate").toString();
			String jarpath = rs.getString("jarpath");
			String destdir = rs.getString("destdir");
			apps = new Apps(aid,uid,aname,aphoto,described,adate,jarpath,destdir);
			apps.setAid(aid);
			AppsList.add(apps);
		}
		rs.close();
		st.close();
		return AppsList;
	}
	
	@Override
	public boolean editInfo(Apps apps) throws Exception {
		pstmt = null;
		String sql = "update apps set uid=?,aname=?,aphoto=?,adescription=?,adate=? where aid=;";
		int result = 0;
		pstmt = this.conn.prepareStatement(sql);
		pstmt.setInt(1, apps.getUid());
		pstmt.setString(2, apps.getAname());
		pstmt.setString(3, apps.getAphoto());
		pstmt.setString(4, apps.getAdate());
		pstmt.setString(5, apps.getDescribed());
		result = pstmt.executeUpdate();
		pstmt.close();
		if (result == 1) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean insertAppright(Apps apps) throws Exception {
		pstmt = null;
		String sql = "insert into appuser (aid,uid,aname,destdir,authorizenumber)value(?,?,?,?,?);";
		int result = 0;
		String id = UUID.randomUUID().toString();
		pstmt = this.conn.prepareStatement(sql);
		pstmt.setInt(1, apps.getAid());
		pstmt.setInt(2, apps.getUid());
		pstmt.setString(3, apps.getAname());
		pstmt.setString(4, apps.getDestdir());
		pstmt.setString(5, id);
		result = pstmt.executeUpdate();
		pstmt.close();
		if (result == 1) {
			return true;
		}
		return false;
	}
	
	@Override
	public Apps getAuthorizenumber(int uid, int aid) throws Exception {
		pstmt = null;
		Apps apps = null;
		ResultSet rs = null;
		String sql = "select * from appuser where uid = ? and aid =?;";
		pstmt = this.conn.prepareStatement(sql);
		pstmt.setInt(1, uid);
		pstmt.setInt(2, aid);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			apps = new Apps();
			apps.setAuthorizenumber(rs.getString("authorizenumber"));
		}
		pstmt.close();
		rs.close();
		return apps;
	}
	
	public String getAppusername(String authorizenumber) throws Exception {
		pstmt = null;
		String user = null;
		ResultSet rs = null;
		String sql = "select uname from users where uid = (select uid from appuser where authorizenumber = ?);";
		
		pstmt = this.conn.prepareStatement(sql);
		pstmt.setString(1, authorizenumber);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			user = rs.getString("uname");
		}
		pstmt.close();
		rs.close();
		return user;
	}
	
	public String getAppurl(String authorizenumber) throws Exception {
		pstmt = null;
		String destdir = null;
		ResultSet rs = null;
		String sql = "select destdir from appuser where authorizenumber = ?;";
		
		pstmt = this.conn.prepareStatement(sql);
		pstmt.setString(1, authorizenumber);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			destdir = rs.getString("destdir");
		}
		pstmt.close();
		rs.close();
		return destdir;
	}
	
	@Override
	public boolean deleteApps(int aid) throws Exception {
		pstmt = null;
		String sql = "delete from apps where aid=?;";
		int result = 0;
		String jarpath = null;
		String destdir = null;
		Apps dapp = this.queryById(aid);
		jarpath = dapp.getJarpath();
		destdir = dapp.getDestdir();
		pstmt = this.conn.prepareStatement(sql);
		pstmt.setInt(1, aid);
		result = pstmt.executeUpdate();
		pstmt.close();
		if (result == 1) {
			FileUtils.forceDelete(new File(jarpath));
			FileUtils.deleteDirectory(new File(destdir.replace(".war", "")));
			return true;
		}
		return false;
	}
	
	@Override
	public Apps queryById(int aid) throws Exception {
		pstmt = null;
		Apps apps = null;
		ResultSet rs = null;
		String sql = "select * from apps where aid =?;";
		pstmt = this.conn.prepareStatement(sql);
		pstmt.setInt(1, aid);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			apps = new Apps();
			apps.setAid(rs.getInt("aid"));
			apps.setUid(rs.getInt("uid"));
			apps.setAname(rs.getString("aname"));
			apps.setAphoto(rs.getString("aphoto"));
			apps.setDescribed(rs.getString("adescription"));
			apps.setAdate(rs.getString("adate"));
			apps.setJarpath(rs.getString("jarpath"));
			apps.setDestdir(rs.getString("destdir"));
		}
		pstmt.close();
		rs.close();
		return apps;
	}

	@Override
	public List<Apps> getMyApps(int uid) throws Exception {
		pstmt = null;
		ResultSet rs = null;
		List<Apps> abList = null;
		String sql = "select * from apps where uid=? order by adate desc;";
		pstmt = this.conn.prepareStatement(sql);
		pstmt.setInt(1, uid);
		rs = pstmt.executeQuery();
		Apps ab;
		abList = new ArrayList<Apps>();
		while (rs.next()) {
			ab = new Apps();
			ab.setUid(uid);
			ab.setAid(rs.getInt("aid"));
			ab.setAname(rs.getString("aname"));
			ab.setAphoto(rs.getString("aphoto"));
			ab.setDescribed(rs.getString("adescription"));
			ab.setAdate(rs.getString("adate"));
			ab.setJarpath(rs.getString("jarpath"));
			ab.setDestdir(rs.getString("destdir"));
			abList.add(ab);
		}
		pstmt.close();
		rs.close();
		return abList;
	}

}
