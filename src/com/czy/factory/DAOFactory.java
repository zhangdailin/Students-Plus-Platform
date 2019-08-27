package com.czy.factory;

import com.czy.dao.AlreadyBuyDao;
import com.czy.dao.AppsDao;
import com.czy.dao.BankDao;
import com.czy.dao.ShoppingCartDao;
import com.czy.dao.UserDao;
import com.czy.service.AlreadyBuyService;
import com.czy.service.AppService;
import com.czy.service.BankService;
import com.czy.service.ShoppingCartService;
import com.czy.service.UserService;

public class DAOFactory {

	public static UserDao getUserServiceInstance() throws Exception {
		return new UserService();
	}

	public static ShoppingCartDao getShoppingCartServiceInstance() throws Exception {
		return new ShoppingCartService();
	}

	public static AlreadyBuyDao getAlreadyBuyServiceInstance() throws Exception {
		return new AlreadyBuyService();
	}
	
	public static AppsDao getAppsServiceInstance() throws Exception {
		return new AppService();
	}
	
	public static BankDao getBankServiceInstance() throws Exception {
		return new BankService();
	}
	
}
