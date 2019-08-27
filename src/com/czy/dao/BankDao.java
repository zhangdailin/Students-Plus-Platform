package com.czy.dao;

import java.util.List;

import com.czy.bean.Bank;

public interface BankDao {

	// Add to my favorite
	public boolean addBankChange(int uid, String aname, float change, String type) throws Exception;

	// Get all the items in the bank of a specified user
	public List<Bank> getBankChange(int uid) throws Exception;
	
}
