package com.czy.bean;

public class Apps {
	
		private int aid;
		private int uid;
		private String aname;
		private String aphoto;
		private String described;
		private String adate;
		private String jarpath;
		private String destdir;
		private String authorizenumber;
		public String getAuthorizenumber() {
			return authorizenumber;
		}

		public void setAuthorizenumber(String authorizenumber) {
			this.authorizenumber = authorizenumber;
		}

		public Apps() {

		}

		public Apps(int aid, int uid, String aname, String aphoto,
				String described, String adate, String jarpath, String destdir) {
			this.aid = aid;
			this.uid = uid;
			this.aname = aname;
			this.aphoto = aphoto;
			this.described = described;
			this.adate = adate;
			this.jarpath=jarpath;
			this.destdir=destdir;
		}

		public int getAid() {
			return aid;
		}

		public void setAid(int aid) {
			this.aid = aid;
		}

		public int getUid() {
			return uid;
		}

		public void setUid(int uid) {
			this.uid = uid;
		}

		public String getAname() {
			return aname;
		}

		public void setAname(String aname) {
			this.aname = aname;
		}

		public String getAphoto() {
			return aphoto;
		}

		public void setAphoto(String aphoto) {
			this.aphoto = aphoto;
		}

		public String getDescribed() {
			return described;
		}

		public void setDescribed(String described) {
			this.described = described;
		}

		public String getAdate() {
			return adate;
		}

		public void setAdate(String adate) {
			this.adate = adate;
		}
		public String getJarpath() {
			return jarpath;
		}

		public void setJarpath(String jarpath) {
			this.jarpath = jarpath;
		}

		public String getDestdir() {
			return destdir;
		}

		public void setDestdir(String destdir) {
			this.destdir = destdir;
		}

		
}
