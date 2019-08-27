package com.czy.dao;

import java.util.List;

import com.czy.bean.AlreadyBuy;

public interface AlreadyBuyDao {

	// Add to my favorite
	public boolean addBuyGoods(int uid, int gid, int number) throws Exception;

	// Get all the items in "my favorite"(cart) of a specified user
	public List<AlreadyBuy> getAllBuyGoods(int uid) throws Exception;
	
	// Determine if user has purchased
	public String getDesignateGoodsMs(int uid, int aid) throws Exception;

	public boolean checkifbuy(int uid, int aid) throws Exception;
}
