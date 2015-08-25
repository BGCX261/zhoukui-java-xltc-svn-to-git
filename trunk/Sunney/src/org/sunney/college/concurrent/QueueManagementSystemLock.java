package org.sunney.college.concurrent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class QueueManagementSystemLock {
	public static void main(String[] args) throws InterruptedException {
		Center center = new Center();
		ExecutorService exec = Executors.newCachedThreadPool();
		Producer producer = new Producer(center);
		Consumer consumer = new Consumer(center);

		exec.execute(consumer);
		for (int i = 0; i < 10; i++)
			exec.execute(producer);

		TimeUnit.SECONDS.sleep(10);
		exec.shutdown();

	}
}

class Producer implements Runnable {
	private Center center;

	public Producer(Center center) {
		this.center = center;
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			center.produce();
		}
	}

}

class Consumer implements Runnable {
	private Center center;

	public Consumer(Center center) {
		this.center = center;
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			center.consume();
		}
	}

}

class Center extends Thread {
	private final static int MAXCOUNT = 10;


	private LinkedList<Waiter> waiters;
	private ArrayList<Customerr> customers;

	private Random rand = new Random(47);

	private final static int SLEEPSEED = 10000;

	public Center() {
		this.waiters = new LinkedList<Waiter>();
		for (int i = 0; i < MAXCOUNT; i++) {
			waiters.addLast(new Waiter());
		}
		this.customers = new ArrayList<Customerr>();
	}

	public void produce() {	
		try {
			if(this.customers.size() > 100){
				System.out.println("队伍太长了，不能办理这么多业务...");
				wait();
			}
			try {
				TimeUnit.MILLISECONDS.sleep(rand.nextInt(SLEEPSEED));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.customers.add(new Customerr());
			
			notifyAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public synchronized void consume() {
		try {
			while (waiters.size() <= 0 || customers.size() <= 0) {
				System.out.println("没有消费者，或者服务窗口都正在忙碌中...");
				wait();
			}
			// 服务窗口可用
			Waiter waiter = this.waiters.getFirst();
			this.waiters.remove(waiter);
			// 客户可用
			Customerr customer = this.customers.get(0);
			this.customers.remove(customer);

			// Do sth....
			System.out.println(waiter + "正在为" + customer + "服务...");
			TimeUnit.MILLISECONDS.sleep(rand.nextInt(SLEEPSEED));

			this.waiters.addLast(waiter);
		} catch (InterruptedException e) {
			System.err.println("---" + e.getMessage());
		}
	}
}

class Waiter {
	private final int id = counter++;
	private static int counter = 1;

	@Override
	public String toString() {
		return "Waiter [id=" + id + "]";
	}
}

class Customerr {
	private final int id = counter++;
	private static int counter = 1;

	@Override
	public String toString() {
		return "Customer [id=" + id + "]";
	}
}
