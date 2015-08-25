package org.sunney.zookeeper.test;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.BeforeClass;
import org.junit.Test;

public class ZookeeperTest {

	private static ZooKeeper zookeeper = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		zookeeper = new ZooKeeper("192.168.1.48:2181", 30000, null);
	}

	public void setData(String key, String data) throws KeeperException, InterruptedException {
		Stat stat = zookeeper.exists(key, false);
		if (stat != null) {
			stat = zookeeper.setData(key, data.getBytes(), -1);
		}
		System.err.println("更新状态：" + stat);
	}

	public void read(String key) throws KeeperException, InterruptedException {
		byte[] bytes = zookeeper.getData(key, false, null);
		System.out.println("值：" + new String(bytes));
	}

	@Test
	public void testMain() {
		try {
			String key = "/config/zookeeper-help/com.test/FieldWatcher/age";
			setData(key, "789");
			read(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
