package org.sunney.zookeeper.help.core.configure;

import java.io.IOException;
import java.lang.reflect.Field;

import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sunney.zookeeper.help.annotation.FieldValue;
import org.sunney.zookeeper.help.util.ProjectUtil;

public class InitializationConfig {
	private Logger logger = LoggerFactory.getLogger(InitializationConfig.class);
	private String topNodeGroup = "/config";
	private String upNodePath;
	private Object watcherObject; // 监听对象
	private ZookeeperWriteAndReadHelper store;

	public InitializationConfig(Object watcherObject, String hosts) throws IOException, InterruptedException {
		this(watcherObject, hosts, null, null);
	}

	public InitializationConfig(Object watcherObject, String hosts, String topNodeGroup, String upNodePath) throws IOException, InterruptedException {
		this.watcherObject = watcherObject;
		logger.info("Zookeeper监听对象：{}", this.watcherObject);
		if (!StringUtils.isEmpty(topNodeGroup)) {
			this.topNodeGroup = upNodePath;
			logger.info("此监听对象的顶级Node组别名称：{}", this.topNodeGroup);
		}
		if (StringUtils.isEmpty(upNodePath)) {
			this.upNodePath = "/" + ProjectUtil.getProjectName() + "/" + ProjectUtil.getClassPackage(this.watcherObject) + "/" + ProjectUtil.getClassShortName(this.watcherObject);
		} else {
			this.upNodePath = upNodePath;
		}
		this.upNodePath = this.topNodeGroup + this.upNodePath;
		logger.info("此监听对象的中段Node组别名称：{}", this.upNodePath);

		store = new ZookeeperWriteAndReadHelper();
		store.connect(hosts);
	}

	public void init() {
		logger.info("初始化[{}]监控对象的节点属性", this.watcherObject);
		initialize();
	}

	public void initialize() {
		Field[] fields = watcherObject.getClass().getDeclaredFields();
		for (Field field : fields) {
			FieldValue fieldValue = field.getAnnotation(FieldValue.class);
			if (fieldValue != null) {
				String initValue = fieldValue.initValue();
				String nodepath = this.upNodePath + "/" + field.getName();
				logger.info("节点:{},赋值:{}", nodepath, initValue);
				Stat writeStat = null;
				int maxRetry = 10;
				for (int i = 0; i < maxRetry && writeStat == null; i++) {
					try {
						logger.info("第[{}]次为节点[{}]重试赋值", i, nodepath);
						writeStat = store.write(nodepath, initValue, true);
					} catch (KeeperException e) {
						logger.info("第[{}]次写节点失败,将重试!", e);
					} catch (InterruptedException e) {
						logger.info("第[{}]次写节点失败,将重试!", e);
					}
				}
			}
		}
	}
}
