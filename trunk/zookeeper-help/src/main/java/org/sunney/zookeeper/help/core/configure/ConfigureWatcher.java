package org.sunney.zookeeper.help.core.configure;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sunney.zookeeper.help.annotation.WatcherDescription;
import org.sunney.zookeeper.help.util.ProjectUtil;

public class ConfigureWatcher implements Watcher {
	private Logger logger = LoggerFactory.getLogger(ConfigureWatcher.class);
	private ZookeeperWriteAndReadHelper store;
	private String topNodeGroup = "/config";
	private String parentNodePath;
	private Object watcherObject; // 监听对象
	private String hosts;

	public ConfigureWatcher(Object watcherObject, String hosts) throws IOException, InterruptedException {
		this(watcherObject, hosts, null, null);
	}

	public ConfigureWatcher(Object watcherObject, String hosts, String topNodeGroup, String parentNodePath) throws IOException, InterruptedException {
		this.hosts = hosts;
		this.watcherObject = watcherObject;
		logger.info("Zookeeper监听对象：{}", this.watcherObject);
		if (!StringUtils.isEmpty(topNodeGroup)) {
			this.topNodeGroup = parentNodePath;
			logger.info("此监听对象的顶级Node组别名称：{}", this.topNodeGroup);
		}
		if (StringUtils.isEmpty(parentNodePath)) {
			this.parentNodePath = "/" + ProjectUtil.getProjectName() + "/" + ProjectUtil.getClassPackage(this.watcherObject) + "/" + ProjectUtil.getClassShortName(this.watcherObject);
		} else {
			this.parentNodePath = parentNodePath;
		}
		this.parentNodePath = this.topNodeGroup + this.parentNodePath;
		logger.info("此监听对象的中段Node组别名称：{}", this.parentNodePath);

		store = new ZookeeperWriteAndReadHelper();
		store.connect(hosts);
	}

	@Override
	public void process(WatchedEvent event) {
		if (event.getType() == EventType.NodeDataChanged) {
			config();
		}
	}
	
	public void init(){
		try {
			logger.info("初始化[{}]监控对象",this.watcherObject);
			new InitializationConfig(watcherObject, hosts).init();
		} catch (Exception e) {
			logger.error("初始化失败",e);
		}
		config();
	}
	
	public void config() {
		WatcherDescription watcherDescription = watcherObject.getClass().getAnnotation(WatcherDescription.class);
		String[] fields = watcherDescription.watcher();
		for (String fieldName : fields) {
			String nodepath = this.parentNodePath + "/" + fieldName;

			String fieldValue = null;
			int maxRetry = 10;
			for (int i = 0; i < maxRetry && fieldValue == null; i++) {
				try {
					logger.info("第[{}]次获取节点[{}]值", i, nodepath);
					fieldValue = store.read(nodepath, this);
				} catch (KeeperException e) {
					logger.info("第[{}]次写节点失败,将重试!", i, e);
				} catch (InterruptedException e) {
					logger.info("第[{}]次写节点失败,将重试!", i, e);
				}
			}
			if (StringUtils.isNotEmpty(fieldValue)) {
				logger.info("为对象[{}]字段[{}]赋值{}", new Object[] { this.watcherObject, fieldName, fieldValue });
				try {
					BeanUtils.setProperty(this.watcherObject, fieldName, fieldValue);
				} catch (IllegalAccessException e) {
					logger.info("对象赋值失败!", e);
				} catch (InvocationTargetException e) {
					logger.info("对象赋值失败!", e);
				}
			}
		}
	}
	
}
