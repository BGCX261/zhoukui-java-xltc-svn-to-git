package org.sunney.zookeeper.help.core.configure;

import java.nio.charset.Charset;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZookeeperWriteAndReadHelper extends ConnectionWatcher {
	private Logger logger = LoggerFactory.getLogger(ZookeeperWriteAndReadHelper.class);
	private final static Charset CHARSET = Charset.forName("UTF-8");

	public Stat write(String path, String data, boolean isInit) throws KeeperException, InterruptedException {
		int version = -1;
		Stat stat = zookeeper.exists(path, false);
		if (stat == null) {
			path = create(path);
		} else {
			version = stat.getVersion();
		}
		byte[] bytes = zookeeper.getData(path, false, stat);
		if (isInit && bytes != null) {
			logger.info("节点[{}]值[{}],不需要再进行初始[{}]!", new Object[] { path, new String(bytes, CHARSET), isInit });
			return stat;
		}
		logger.info("为节点[{}]赋值[{}!]", path, data);
		return zookeeper.setData(path, data.getBytes(CHARSET), version);
	}

	public String read(String path, Watcher watcher) throws KeeperException, InterruptedException {
		byte[] data = zookeeper.getData(path, watcher, null);
		String getData = null;
		if (data != null) {
			getData = new String(data, CHARSET);
		}
		logger.info("节点[{}]取值[{}!]", path, getData);
		return getData;
	}
}
