package org.sunney.college.concurrent;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PiggyMoney {
	public static void main(String[] args) throws InterruptedException {
		Bank bank = new Bank();
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new CoinIn(bank));
		exec.execute(new CoinOut(bank));

		TimeUnit.MILLISECONDS.sleep(100);
		exec.shutdownNow();
	}
}

class Bank {
	private List<Money> bank = new LinkedList<Money>();

	private Lock lock = new ReentrantLock();
	private Condition inCondition = lock.newCondition();
	private Condition outCondition = lock.newCondition();

	public void coinIn() {
		lock.lock();
		try {
			if(bank.size() >= 100){
				System.err.println("主淫，您需要先花点钱....");
				inCondition.wait();
			}
			Money money = new Money();
			bank.add(money);
			System.out.println("主淫存入" + money);

			if (bank.size() > 0) {
				outCondition.signalAll();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}

	public void coinOut() {
		lock.lock();
		try {
			if (bank.size() <= 0) {
				System.err.println("主淫，您不能透支啊....");
				outCondition.wait();
			}

			Money money = bank.get(0);
			bank.remove(money);
			System.out.println("主淫取出" + money);
			
			if (bank.size() < 100) {
				inCondition.signalAll();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
}

class CoinOut implements Runnable {
	private Bank bank;

	public CoinOut(Bank bank) {
		this.bank = bank;
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			bank.coinOut();
		}
	}
}

class CoinIn implements Runnable {
	private Bank bank;

	public CoinIn(Bank bank) {
		this.bank = bank;
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			bank.coinIn();
		}
	}
}

class Money {
	private int id = counter++;
	private static int counter = 1;

	@Override
	public String toString() {
		return "Money [id=" + id + "]";
	}
}
