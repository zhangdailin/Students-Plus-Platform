package com.czy.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.czy.bean.Apps;
import com.czy.bean.ShoppingCart;
import com.czy.bean.User;
import com.czy.dao.AlreadyBuyDao;
import com.czy.dao.AppsDao;
import com.czy.dao.BankDao;
import com.czy.dao.ShoppingCartDao;
import com.czy.dao.UserDao;
import com.czy.factory.DAOFactory;


public class ShoppingCartDaoImpl implements ShoppingCartDao {

	private Connection conn = null;

	private PreparedStatement pstmt = null;

	public ShoppingCartDaoImpl(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public boolean addGoods(int uid, int aid, int number) throws Exception {
		pstmt = null;
		int result = 0;
		String message = this.getDesignateGoodsMs(uid, aid);
		if (!"".equals(message)) {
			int Uid = Integer.valueOf(message.split("&")[0]);
			int goodsCount = Integer.valueOf(message.split("&")[1]);
			String sql = "update shoppingcart set number=?,sdate=now() where Uid=?;";
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setInt(1, goodsCount + number);
			pstmt.setInt(2, Uid);
		} else {
			String sql = "insert into shoppingcart(uid,aid,number,sdate)value(?,?,?,now());";
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setInt(1, uid);
			pstmt.setInt(2, aid);
			pstmt.setInt(3, number);
		}
		result = pstmt.executeUpdate();
		pstmt.close();
		if (result == 1) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean deleteGoods(int uid, int aid, int number) throws Exception {
		String message = this.getDesignateGoodsMs(uid, aid);
		int result = 0;
		if (!"".equals(message)) {
			int Uid = Integer.valueOf(message.split("&")[0]);
			int goodsCount = Integer.valueOf(message.split("&")[1]);
			if (goodsCount < number) {
				return false;
			} else if (goodsCount == number) {
				String sql = "delete from shoppingcart where Uid =? and aid =?;";
				pstmt = this.conn.prepareStatement(sql);
				pstmt.setInt(1, Uid);
				pstmt.setInt(2, aid);
			} else {
				String sql = "update shoppingcart set number=? where Uid=?;";
				pstmt = this.conn.prepareStatement(sql);
				pstmt.setInt(1, goodsCount - number);
				pstmt.setInt(2, Uid);
			}
			result = pstmt.executeUpdate();
			pstmt.close();
		}
		if (result == 1) {
			return true;
		}
		return false;
	}
	
	@Override
	public List<ShoppingCart> getAllGoods(int uid) throws Exception {
		pstmt = null;
		ResultSet rs = null;
		List<ShoppingCart> scList = null;
		String sql = "select * from shoppingcart where Uid=?;";
		pstmt = this.conn.prepareStatement(sql);
		pstmt.setInt(1, uid);
		rs = pstmt.executeQuery();
		ShoppingCart sc;
		scList = new ArrayList<ShoppingCart>();
		while (rs.next()) {
			sc = new ShoppingCart();
			sc.setUid(rs.getInt("Uid"));
			sc.setAid(rs.getInt("Aid"));
			sc.setNumber(rs.getInt("Number"));
			String date = rs.getDate("Sdate").toString();
			String time = rs.getTime("Sdate").toString();
			sc.setSdate(date + " " + time);
			scList.add(sc);
		}
		pstmt.close();
		rs.close();
		return scList;
	}
	
	@Override
	public String getDesignateGoodsMs(int uid, int aid) throws Exception {
		ResultSet rs = null;
		String sql = "select * from shoppingcart where Uid =? and aid=?;";
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

	@Override
	public boolean payGoods(int uid, int aid, int number) throws Exception {
		boolean flag = false;
	    AppsDao dao = DAOFactory.getAppsServiceInstance();
		conn.setAutoCommit(false);
		if (this.deleteGoods(uid, aid, number)) {
			Apps apps = dao.queryById(aid);
			apps.setUid(uid);
			String aname = apps.getAname();
			String type = "Payment cost";
			float change = (float) 5.0;
			AlreadyBuyDao ab = DAOFactory.getAlreadyBuyServiceInstance();
			BankDao ba = DAOFactory.getBankServiceInstance();
			flag = (ab.addBuyGoods(uid, aid, number) & dao.insertAppright(apps) & ba.addBankChange(uid, aname, change, type));
		}

		conn.commit();
		conn.setAutoCommit(true);
		return flag;
	}

	@Override
	public boolean payAllGoods(int uid, float allTotalPrice) throws Exception {
		UserDao dao = DAOFactory.getUserServiceInstance();
		User user= dao.queryByuid(uid);
		float money = user.getMoney();
		int appright = user.getAppright();
		//System.out.println(money+"+"+allTotalPrice);
		if (allTotalPrice <= money) {
			money = money - allTotalPrice;
			List<ShoppingCart> scList = this.getAllGoods(uid);
			if (scList != null & scList.size() > 0 & dao.queryUpdatemoney(uid, money)) {
				int aid;
				int number;
				int totalnumber = 0;
				ShoppingCart sc;
				for (int i = 0; i < scList.size(); i++) {
					sc = scList.get(i);
					aid = sc.getAid();
					number = sc.getNumber();
					totalnumber+=number;
					this.payGoods(uid, aid, number);
				}
				
				dao.updateAppright(uid, totalnumber+appright);
				return true;
			}
		}
		return false;
	}
}
