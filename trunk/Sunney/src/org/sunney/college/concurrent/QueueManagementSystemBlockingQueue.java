package org.sunney.college.concurrent;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class QueueManagementSystemBlockingQueue {
	public static void main(String[] args) throws InterruptedException {
		Center1 center = new Center1();
		ExecutorService exec = Executors.newCachedThreadPool();
		Producerr1 producer = new Producerr1(center);
		Consumerr1 consumer = new Consumerr1(center);

		exec.execute(producer);
		for (int i = 0; i < 10; i++) {
			exec.execute(consumer);
		}

		 TimeUnit.SECONDS.sleep(10);
		 exec.shutdown();

	}
}

class Producerr1 implements Runnable {
	private Center1 center;

	public Producerr1(Center1 center) {
		this.center = center;
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			center.produce();
		}
	}

}

class Consumerr1 implements Runnable {
	private Center1 center;

	public Consumerr1(Center1 center) {
		this.center = center;
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			center.consume();
		}
	}

}

class Center1 extends Thread {
	private final static int MAXCOUNT = 10;

	private BlockingQueue<Waiter1> waiters;
	private BlockingQueue<Customerr1> customers;

	private Random rand = new Random(47);

	private final static int PRODUCERSLEEPSEED = 100;
	private final static int CONSUMERSLEEPSEED = 10000;

	public Center1() {
		this.waiters = new LinkedBlockingQueue<Waiter1>(MAXCOUNT);
		for (int i = 0; i < MAXCOUNT; i++) {
			waiters.add(new Waiter1());
		}
		this.customers = new LinkedBlockingQueue<Customerr1>();
		/*for (int j = 0; j < 20; j++) {
			this.customers.add(new Customerr1());
		}*/
	}

	public void produce() {
		try {
			TimeUnit.MILLISECONDS.sleep(rand.nextInt(PRODUCERSLEEPSEED));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.customers.add(new Customerr1());
	}

	public void consume() {
		try {
			// 服务窗口可用
			Waiter1 waiter = this.waiters.take();
			this.waiters.remove(waiter);
			// 客户可用
			Customerr1 customer = this.customers.take();
			this.customers.remove(customer);

			// Do sth....
			System.out.println(waiter + "正在为" + customer + "服务...");
			TimeUnit.MILLISECONDS.sleep(rand.nextInt(CONSUMERSLEEPSEED));

			this.waiters.add(waiter);
		} catch (InterruptedException e) {
			System.err.println("---" + e.getMessage());
		}
	}
}

class Waiter1 {
	private final int id = counter++;
	private static int counter = 1;

	@Override
	public String toString() {
		if (id > 9)
			return "Waiter [id=" + id + "]";
		return "Waiter [id=0" + id + "]";

	}
}

class Customerr1 {
	private final int id = counter++;
	private static int counter = 1;

	@Override
	public String toString() {
		if (id > 9) {
			return "Customer [id=" + id + "]";
		}
		return "Customer [id=0" + id + "]";
	}
}
