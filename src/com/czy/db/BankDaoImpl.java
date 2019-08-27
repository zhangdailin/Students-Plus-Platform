package com.czy.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.czy.bean.Bank;
import com.czy.dao.BankDao;

public class BankDaoImpl implements BankDao{
	private Connection conn = null;

	private PreparedStatement pstmt = null;

	public BankDaoImpl(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public boolean addBankChange(int uid, String aname, float change, String type) throws Exception
	{
		pstmt = null;
		String sql = "insert into bank(uid,aname,bachange,coursetype,chtime)value(?,?,?,?,now());";
		int result = 0;
		pstmt = this.conn.prepareStatement(sql);
		pstmt.setInt(1, uid);
		pstmt.setString(2, aname);
		pstmt.setFloat(3, change);
		pstmt.setString(4, type);
		result = pstmt.executeUpdate();
		pstmt.close();
		if (result == 1) {
			return true;
		}
		return false;
	}
	
	@Override
	public List<Bank> getBankChange(int uid) throws Exception
	{
		pstmt = null;
		ResultSet rs = null;
		List<Bank> abList = null;
		String sql = "select * from bank where uid=? order by chtime desc;";
		pstmt = this.conn.prepareStatement(sql);
		pstmt.setInt(1, uid);
		rs = pstmt.executeQuery();
		Bank ab;
		abList = new ArrayList<Bank>();
		while (rs.next()) {
			ab = new Bank();
			ab.setUid(uid);
			ab.setAname(rs.getString("aname"));
			ab.setChange(rs.getFloat("bachange"));
			ab.setType(rs.getString("coursetype"));
			String date = rs.getDate("chtime").toString();
			String time = rs.getTime("chtime").toString();
			ab.setDate(date + " " + time);
			abList.add(ab);
		}
		pstmt.close();
		rs.close();
		return abList;
	}

}
