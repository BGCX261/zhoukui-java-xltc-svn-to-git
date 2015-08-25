package org.sunney.college.rmi;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class StartService {
	private static final String IP = "127.0.0.1";
	private static final int PORT = 9999;
	private static final String REMOTE_NAME = "userDao";
	private static final String REMOTE_URL = "rmi://" + IP + ":" + PORT + "/" + REMOTE_NAME;

	public static void main(String[] args) {
		try {
			UserDao userDao = new UserDaoImpl(); // 实例化对象
			LocateRegistry.createRegistry(PORT); // 注册端口
			Naming.bind(REMOTE_URL, userDao); // 绑定远程服务对象
			System.out.println("远程" + REMOTE_NAME + "启动成功....");
		} catch (RemoteException e) {
			System.err.println("远程对象出错");
			e.printStackTrace();
		} catch (MalformedURLException e) {
			System.err.println("URL出错了");
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			System.err.println("绑定的对象已经存在了");
			e.printStackTrace();
		}
	}
}

/**
 * 远程接口 必须继承与Remote对象
 */
interface UserDao extends Remote {
	/**
	 * 简单的测试方法
	 * 
	 * @param name
	 */
	public void sayName(String name) throws RemoteException;
}

/**
 * 
 * 接口的实现类 必须继承UnicastRemoteObject(单一远程对象) 实现UserDao自己的接口
 */
class UserDaoImpl extends UnicastRemoteObject implements UserDao {

	public UserDaoImpl() throws RemoteException {
	}

	@Override
	public void sayName(String name) {
		if (name != null && !name.equals("")) {
			System.out.println("我的名字是：" + name);
		} else {
			System.err.println("名字不为空....");
		}
	}

}
