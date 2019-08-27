package com.czy.service;

import java.sql.SQLException;
import java.util.List;

import com.czy.bean.Bank;
import com.czy.dao.BankDao;
import com.czy.db.BankDaoImpl;
import com.czy.db.DBConnection;

public class BankService implements BankDao{

	private DBConnection dbconn = null;

	private BankDao dao = null;

	public BankService() throws SQLException {
		this.dbconn = new DBConnection();
		this.dao = new BankDaoImpl(this.dbconn.getConnection());
	}
	
	public boolean addBankChange(int uid, String aname, float change, String type) throws Exception
	{
		if(isInt(uid) && aname!=null && type!=null){
			return this.dao.addBankChange(uid, aname, change, type);
		}
		return false;
	}

	public List<Bank> getBankChange(int uid) throws Exception
	{
		if(isInt(uid)){
			return this.dao.getBankChange(uid);
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
}
