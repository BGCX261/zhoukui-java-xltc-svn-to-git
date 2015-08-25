package org.sunney.zookeeper.help.core.configure;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionWatcher implements Watcher {
	private Logger logger = LoggerFactory.getLogger(ConnectionWatcher.class);
	private static final int SESSION_TIMEOUT = 5000;
	protected ZooKeeper zookeeper;
	protected String hosts;
	private CountDownLatch connectionLatch = new CountDownLatch(1);

	public void connect(String hosts) throws IOException, InterruptedException {
		this.hosts = hosts;
		logger.info("连接Zookeeper服务[{}].", hosts);
		zookeeper = new ZooKeeper(hosts, SESSION_TIMEOUT, this);
		connectionLatch.await();
	}

	@Override
	public void process(WatchedEvent event) {
		if (event.getState() == KeeperState.SyncConnected) {
			logger.info("成功连接Zookeeper服务[{}].", hosts);
			connectionLatch.countDown();
		}
	}

	public void close() throws InterruptedException {
		zookeeper.close();
	}

	public String create(String groupPath) throws KeeperException, InterruptedException {
		try {
			groupPath = zookeeper.create(groupPath, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		} catch (KeeperException e) {
			String[] slices = groupPath.split("/");
			if (slices.length > 1) {
				logger.info("组节点路径父节点不存在[{}]！", groupPath);
				String tempGroupPath = "";
				for (String slice : slices) { //此处可改进 从后面尝试
					if (StringUtils.isNotEmpty(slice)) {
						tempGroupPath += "/" + slice;
						if (zookeeper.exists(tempGroupPath, false) == null) {
							groupPath = zookeeper.create(tempGroupPath, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
							logger.info("创建组别：{}", groupPath);
						}
					}
				}
			} else {
				logger.error("ZooKeeper服务器错误!", e);
			}
		}
		return groupPath;
	}

}
