package com.czy.dao;

import com.czy.bean.User;

public interface UserDao {

	public boolean addUser(User user) throws Exception;

	public boolean editEmail(int uid, String email)
			throws Exception;

	public User queryByuid(int uid) throws Exception;
	
	public boolean updateAppright(int uid, int number) throws Exception;
	
	public boolean queryUpdatemoney(int uid, float money) throws Exception;
	
	public boolean editPasswd(int uid, String passwd) throws Exception;

	public boolean editLoginTime(int uid) throws Exception;

	public boolean deleteUser(int uid) throws Exception;

	public User queryByName(String uname) throws Exception;

	public User queryByEmail(String email) throws Exception;

}
