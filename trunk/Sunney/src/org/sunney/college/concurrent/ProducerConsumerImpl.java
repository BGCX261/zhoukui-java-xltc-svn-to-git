package org.sunney.college.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerImpl {
	public static void main(String[] args) {
		Store store = new Store();

		ExecutorService proConServiceExecutor = Executors.newFixedThreadPool(2);
		Producer1 producer = new Producer1(store);
		Consumer1 consumer = new Consumer1(store);

		try {
			proConServiceExecutor.execute(producer);
			proConServiceExecutor.execute(consumer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			proConServiceExecutor.shutdown();
		}
	}
}

class Store {

	private Lock producerConsumberLock;
	private Condition producerCondition;
	private Condition consumerCondition;

	private final int POOL_SIZE = 2;
	private int productNumber;
	private int[] productPool;

	public Store() {
		productPool = new int[POOL_SIZE];
		productNumber = 0;

		producerConsumberLock = new ReentrantLock();
		producerCondition = producerConsumberLock.newCondition();
		consumerCondition = producerConsumberLock.newCondition();
	}

	public void produce() {
		try {
			while (productNumber >= POOL_SIZE) {
				System.out.println("productNumber=" + productNumber);
				System.out.println("Pool is full, producer " + Thread.currentThread().getName() + " wait...");
				producerCondition.await();
			}

			producerConsumberLock.lock();
			productPool[productNumber++] = 1;
			System.out.println(Thread.currentThread().getName() + " Porduced a product....");

			if (productNumber > 0) {
				consumerCondition.signalAll();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			producerConsumberLock.unlock();
		}

	}

	public void consume() {
		try {
			while (productNumber <= 0) {
				System.out.println("productNumber=" + productNumber);
				System.out.println("Pool is empty, consumer " + Thread.currentThread().getName() + " wait...");
				consumerCondition.await();
			}

			producerConsumberLock.lock();
			productNumber--;
			System.out.println(Thread.currentThread().getName() + " Consumed a product....");
			if (productNumber <= POOL_SIZE - 1 && productNumber >= 0) {
				producerCondition.signalAll();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			producerConsumberLock.unlock();
		}

	}

}

class Consumer1 implements Runnable {
	private Store pcl;

	public Consumer1(Store pcl) {
		this.pcl = pcl;
	}

	@Override
	public void run() {
		while (true) {
			pcl.consume();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}

class Producer1 implements Runnable {
	private Store pcl;

	public Producer1(Store pcl) {
		this.pcl = pcl;
	}
	@Override
	public void run() {
		while (true) {
			pcl.produce();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
