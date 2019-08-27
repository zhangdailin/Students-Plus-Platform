package com.czy.bean;

public class Bank {

	private String aname;
	private int uid;
	private float change;
	private String date;
	private String type;

	public Bank() {

	}

	public Bank(String aname, int uid, float change, String date, String type) {
		this.aname = aname;
		this.uid = uid;
		this.change = change;
		this.date = date;
		this.type = type;
	}

	public String getAname() {
		return aname;
	}

	public void setAname(String aname) {
		this.aname = aname;
	}
	
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}
	
	public float getChange(){
		return change;
	}
	
	public void setChange(float change){
		this.change = change;
	}
	
	public String getDate(){
		return date;
	}
	
	public void setDate(String date){
		this.date = date;
	}
	
	public String getType(){
		return type;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
}
