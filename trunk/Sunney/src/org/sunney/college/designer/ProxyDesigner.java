package org.sunney.college.designer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyDesigner {
	public static void main(String[] args) {
		Subject subject = new RealSubject();

		InvocationHandler handler = new MyInvocationHandler(subject);
		Subject proxy = DynamicProxy.newProxyInstance(subject.getClass().getClassLoader(), subject.getClass().getInterfaces(), handler);
		proxy.action("Fuck Life!");
	}
}

interface Subject {
	void action(String action);
}

class RealSubject implements Subject {
	@Override
	public void action(String action) {
		System.out.println("Action: " + action);
	}
}

class MyInvocationHandler implements InvocationHandler {
	private Object target = null;

	public MyInvocationHandler(Object _obj) {
		this.target = _obj;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return method.invoke(this.target, args);
	}
}

class DynamicProxy<T> {
	public static <T> T newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler handler) {
		if (true) {
			(new BeforeAdvice()).exec();
		}

		return (T) Proxy.newProxyInstance(loader, interfaces, handler);
	}
}

interface IAdvice {
	public void exec();
}

class BeforeAdvice implements IAdvice {
	@Override
	public void exec() {
		System.out.println("我是前置通知，我被执行了...");
	}

}