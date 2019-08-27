package com.czy.bean;

/*
 * goods has been purchased
 */
public class AlreadyBuy {

	// User id
	private int uid;
	// App id
	private int aid;
	// number of goods
	private int number;
	// Purchase time
	private String buyTime;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(String buyTime) {
		this.buyTime = buyTime;
	}

}
