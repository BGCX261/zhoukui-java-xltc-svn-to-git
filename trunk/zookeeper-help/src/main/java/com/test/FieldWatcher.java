package com.test;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sunney.zookeeper.help.annotation.FieldValue;
import org.sunney.zookeeper.help.annotation.WatcherDescription;

@WatcherDescription(watcher = { "name", "age" })
public class FieldWatcher {
	private Logger logger = LoggerFactory.getLogger(FieldWatcher.class);

	@FieldValue(initValue = "zhoukui")
	private String name;
	@FieldValue(initValue = "25")
	private String age;

	public void init() {
		logger.info("测试类启动...");
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					try {
						TimeUnit.SECONDS.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					logger.info("FieldWatcher [logger=" + logger + ", name=" + name + ", age=" + age + "]");
				}
			}
		}).start();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String toString() {
		return "FieldWatcher [logger=" + logger + ", name=" + name + ", age=" + age + "]";
	}

}
