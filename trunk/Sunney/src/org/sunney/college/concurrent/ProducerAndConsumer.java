package org.sunney.college.concurrent;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ProducerAndConsumer {
	public static void main(String[] args) throws Exception {
		CoinBox box = new CoinBox();
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new Consumer2(box));
		exec.execute(new Producer2(box));
		
		TimeUnit.MILLISECONDS.sleep(1);
		exec.shutdownNow();
	}
}

class Coin {
	private final int id;

	public Coin(int idn) {
		id = idn;
	}
	@Override
	public String toString() {
		return "Coin [id=" + id + "]";
	}

}

class CoinBox  {
	LinkedBlockingQueue<Coin> box = new LinkedBlockingQueue<Coin>();
	
	public void producess(Coin coin){
		box.add(coin);
		System.out.println("投入硬币：" + coin);
	}
	public void consumer(){
		try {
//			Coin coin = box.take();
			Coin coin = box.poll();
			if(coin == null){
				System.err.println("拿走硬币：" + coin);
			}else{
				System.out.println("拿走硬币：" + coin);
			}
		} catch (Exception e) {
			System.err.println("consumer exception");
		}
	}
	
}

class Consumer2 implements Runnable {
	private CoinBox box;

	public Consumer2(CoinBox box) {
		this.box = box;
	}

	@Override
	public void run() {
		while(!Thread.interrupted()){
			box.consumer();
		}
	}
}

class Producer2 implements Runnable {
	private CoinBox box;
	private Random rand = new Random(47);

	public Producer2(CoinBox box) {
		this.box = box;
	}

	@Override
	public void run() {
		while(!Thread.interrupted()){
			Coin coin = new Coin(rand.nextInt(500));
			box.producess(coin);
		}
	}
}
