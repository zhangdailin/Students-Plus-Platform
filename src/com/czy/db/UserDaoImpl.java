package com.czy.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.mindrot.jbcrypt.BCrypt;

import com.czy.bean.User;
import com.czy.dao.UserDao;

public class UserDaoImpl implements UserDao {

	private Connection conn = null;

	private PreparedStatement pstmt = null;

	public UserDaoImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean addUser(User user) throws Exception {
		pstmt = null;
		String sql = "insert into users(uname,passwd,email,money,appright,lastLogin)value(?,?,?,?,?,sysdate());";
		int result = 0;
		
		pstmt = this.conn.prepareStatement(sql);
		ResultSet rset = pstmt.executeQuery("select count(uname) from users;"); 
		rset.next();
		
		pstmt.setString(1, user.getUname());
		String hash = BCrypt.hashpw(user.getPasswd(), BCrypt.gensalt(12));
		pstmt.setString(2, hash);
		pstmt.setString(3, user.getEmail());
		pstmt.setInt(4, 200);
		pstmt.setInt(5, 20);
		result = pstmt.executeUpdate();
		
		pstmt.close();
		if (result == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean editEmail(int uid, String email) throws Exception {
		User user = queryByEmail(email);
		if (user != null && user.getUid() != uid) {
			return false;
		}
		pstmt = null;
		String sql = "update users set email=? where uid=?;";
		int result = 0;
		pstmt = this.conn.prepareStatement(sql);
		pstmt.setString(1, email);
		pstmt.setInt(2, uid);
		result = pstmt.executeUpdate();
		pstmt.close();
		if (result == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean editLoginTime(int uid) throws Exception {
		pstmt = null;
		String sql = "update users set lastLogin=sysdate() where uid=?;";
		int result = 0;
		pstmt = this.conn.prepareStatement(sql);
		pstmt.setInt(1, uid);
		result = pstmt.executeUpdate();
		pstmt.close();
		if (result == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean editPasswd(int uid, String passwd) throws Exception {
		String sql = "update users set passwd=? where uid=" + uid + ";";
		int result = 0;
		pstmt = this.conn.prepareStatement(sql);
		String hash = BCrypt.hashpw(passwd, BCrypt.gensalt(12));
		pstmt.setString(1, hash);
		result = pstmt.executeUpdate();
		pstmt.close();
		if (result == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteUser(int uid) throws Exception {
		String sql = "delete from Users where uid=?;";
		int result = 0;
		pstmt = this.conn.prepareStatement(sql);
		pstmt.setInt(1, uid);
		result = pstmt.executeUpdate();
		pstmt.close();
		if (result == 1) {
			return true;
		}
		return false;
	}

	@Override
	public User queryByName(String uname) throws Exception {
		User user = null;
		ResultSet rs = null;
		String sql = "select * from users where uname =?;";
		pstmt = this.conn.prepareStatement(sql);
		pstmt.setString(1, uname);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			user = new User();
			int uid = rs.getInt("uid");
			String passwd = rs.getString("passwd");
			String email = rs.getString("email");
			float money = rs.getFloat("money");
			int appright = rs.getInt("appright");
			user.setUid(uid);
			user.setUname(uname);
			user.setPasswd(passwd);
			user.setEmail(email);
			user.setAppright(appright);
			String date = rs.getDate("lastlogin").toString();
			String time = rs.getTime("lastlogin").toString();
			user.setLastLogin(date + "¡¡" + time);
			user.setMoney(money);
		}
		pstmt.close();
		rs.close();
		return user;
	}

	@Override
	public User queryByEmail(String email) throws Exception {
		User user = null;
		ResultSet rs = null;
		String sql = "select * from users where email=?;";
		pstmt = this.conn.prepareStatement(sql);
		pstmt.setString(1, email);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			user = new User();
			int uid = rs.getInt("uid");
			String uname = rs.getString("uname");
			String passwd = rs.getString("passwd");
			float money = rs.getFloat("money");
			int appright = rs.getInt("appright");
			user.setUid(uid);
			user.setUname(uname);
			user.setPasswd(passwd);
			user.setEmail(email);
			user.setAppright(appright);
			String date = rs.getDate("lastlogin").toString();
			String time = rs.getTime("lastlogin").toString();
			user.setLastLogin(date + "¡¡" + time);
			user.setMoney(money);
		}
		pstmt.close();
		rs.close();
		return user;
	}

	@Override
	public User queryByuid(int uid) throws Exception {
		User user = null;
		ResultSet rs = null;
		String sql = "select * from users where uid=?;";
		pstmt = this.conn.prepareStatement(sql);
		pstmt.setInt(1, uid);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			user = new User();
			float money = rs.getFloat("money");
			int appright = rs.getInt("appright");
			user.setMoney(money);
			user.setAppright(appright);
		}
		pstmt.close();
		rs.close();
		return user;
	}

	@Override
	public boolean queryUpdatemoney(int uid, float money) throws Exception {
		String sql = "update users set money=? where uid=" + uid +";";
		int result = 0;
		pstmt = this.conn.prepareStatement(sql);
		pstmt.setFloat(1, money);
		result = pstmt.executeUpdate();
		pstmt.close();
		if (result == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updateAppright(int uid, int number) throws Exception {
		String sql = "update users set appright=? where uid=" + uid + ";";
		int result = 0;
		pstmt = this.conn.prepareStatement(sql);
		pstmt.setFloat(1, number);
		result = pstmt.executeUpdate();
		pstmt.close();
		if (result == 1) {
			return true;
		}
		return false;
	}
}
