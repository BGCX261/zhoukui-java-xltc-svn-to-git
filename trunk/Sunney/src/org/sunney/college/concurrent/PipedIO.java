package org.sunney.college.concurrent;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PipedIO {
	public static void main(String[] args) throws IOException, InterruptedException {
		Sender sender = new Sender();
		Receiver receiver = new Receiver(sender);
		
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(sender);
		exec.execute(receiver);
		TimeUnit.SECONDS.sleep(3);
		exec.shutdownNow();
	}
}

class Sender implements Runnable {
	private Random rand = new Random(47);
	private PipedWriter out = new PipedWriter();

	public PipedWriter getPipedWriter() {
		return out;
	}

	@Override
	public void run() {
		try {
			while (true) {
				for (char c = 'A'; c <= 'Z'; c++) {
					out.write(c);
					TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
				}
			}
		} catch (IOException e) {
			System.err.println(e + " Sender write exception");
		} catch (InterruptedException e) {
			System.err.println(e + " Sender sleep interrupt");
		}
	}
}

class Receiver implements Runnable {
	private PipedReader in = new PipedReader();
	public Receiver(Sender sender) throws IOException{
		in = new PipedReader(sender.getPipedWriter());
	}
	public PipedReader getPipedReader() {
		return in;
	}
	@Override
	public void run() {
		try {
			while (true) {
				for (char c = 'A'; c <= 'z'; c++) {
					System.out.println("Read: " + (char) in.read() + ", ");
				}
			}
		} catch (IOException e) {
			System.err.println(e + " Sender write exception");
		}
	}
}