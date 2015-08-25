package org.sunney.college.designer;

public class Singleton {
	/**
	 * volatile关键词确保：当uniqueInstance变量被初始化成Singleton实例时，多个线程正确地处理uniqueInstance变量
	 */
	private volatile static Singleton uniqueInstance;

	private Singleton() {
	}

	public static Singleton getInstance() {
		if (uniqueInstance == null) {//检查实例，如果不存在就进入同步区块
			synchronized (Singleton.class) {//注意，只有第一次才彻底执行这里的代码
				if (uniqueInstance == null) {
					uniqueInstance = new Singleton();
				}
			}
		}
		return uniqueInstance;
	}
}
