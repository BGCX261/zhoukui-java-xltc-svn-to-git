package org.sunney.college.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class TestRemote {
	public static void main(String[] args) {
		try {
			// 在rmi服务中查询userdao的对象
			UserDao userDao = (UserDao) Naming.lookup("rmi://127.0.0.1:9999/userDao");
			// 调用远程服务的方法
			userDao.sayName("spring sky");
		} catch (MalformedURLException e) {
			System.err.println("URL出错");
			e.printStackTrace();
		} catch (RemoteException e) {
			System.err.println("远程对象出错");
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.err.println("没有找到绑定的对象");
			e.printStackTrace();
		}
	}
}
