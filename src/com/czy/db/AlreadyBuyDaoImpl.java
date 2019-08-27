package com.czy.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.czy.bean.AlreadyBuy;
import com.czy.dao.AlreadyBuyDao;

public class AlreadyBuyDaoImpl implements AlreadyBuyDao {

	private Connection conn = null;

	private PreparedStatement pstmt = null;

	public AlreadyBuyDaoImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean addBuyGoods(int uid, int aid, int number) throws Exception {
		pstmt = null;
		String sql = "insert into alreadybuy(uid,aid,number,buytime)value(?,?,?,now());";
		int result = 0;
		pstmt = this.conn.prepareStatement(sql);
		pstmt.setInt(1, uid);
		pstmt.setInt(2, aid);
		pstmt.setInt(3, number);
		result = pstmt.executeUpdate();
		pstmt.close();
		if (result == 1) {
			return true;
		}
		
		return false;
	}

	@Override
	public List<AlreadyBuy> getAllBuyGoods(int uid) throws Exception {
		pstmt = null;
		ResultSet rs = null;
		List<AlreadyBuy> abList = null;
		String sql = "select * from alreadybuy where uid=? order by buytime desc;";
		pstmt = this.conn.prepareStatement(sql);
		pstmt.setInt(1, uid);
		rs = pstmt.executeQuery();
		AlreadyBuy ab;
		abList = new ArrayList<AlreadyBuy>();
		while (rs.next()) {
			ab = new AlreadyBuy();
			ab.setUid(uid);
			ab.setAid(rs.getInt("aid"));
			ab.setNumber(rs.getInt("number"));
			String date = rs.getDate("buytime").toString();
			String time = rs.getTime("buytime").toString();
			ab.setBuyTime(date + " " + time);
			abList.add(ab);
		}
		pstmt.close();
		return abList;
	}
	
	@Override
	public String getDesignateGoodsMs(int uid, int aid) throws Exception {
		ResultSet rs = null;
		String sql = "select * from alreadybuy where Uid =? and Aid=?;";
		pstmt = this.conn.prepareStatement(sql);
		pstmt.setInt(1, uid);
		pstmt.setInt(2, aid);
		rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getString("uid")+ "&" + rs.getInt("number");
		}
		pstmt.close();
		rs.close();
		return "";
	}
	
	public boolean checkifbuy(int uid, int aid) throws Exception {
		ResultSet rs = null;
		String sql = "select * from alreadybuy where Uid =? and Aid=?;";
		pstmt = this.conn.prepareStatement(sql);
		pstmt.setInt(1, uid);
		pstmt.setInt(2, aid);
		rs = pstmt.executeQuery();
		if (rs.next()) {
			return true;
		}
		pstmt.close();
		rs.close();
		return false;
	}

}
