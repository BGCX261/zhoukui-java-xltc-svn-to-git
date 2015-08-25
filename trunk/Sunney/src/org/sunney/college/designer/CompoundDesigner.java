package org.sunney.college.designer;

import java.util.ArrayList;
import java.util.Iterator;

public class CompoundDesigner {
	public static void main(String[] args) {
		CompoundDesigner simulator = new CompoundDesigner();
		AbstractDuckFactory duckFactory = new DuckCounterFactory();
		simulator.simulator(duckFactory);
	}

	private void simulator(AbstractDuckFactory duckFactory) {
		Quackable mallardDuck = duckFactory.createMallardDuck();
		Quackable redheadDuck = duckFactory.createRedheadDuck();
		Quackable duckcall = duckFactory.createDuckCall();
		Quackable rubberDuck = duckFactory.createRubbertDuck();
		Quackable goose = new GooseAdapter(new Goose());
		
		Flock flockDucks = new Flock();
		flockDucks.add(mallardDuck);
		flockDucks.add(redheadDuck);
		flockDucks.add(duckcall);
		flockDucks.add(rubberDuck);
		flockDucks.add(goose);
		
		Quackologist quackologist = new Quackologist();
		flockDucks.registerObserver(quackologist);
		simulate(flockDucks);
		
		System.out.println("The ducks quacked " + QuackCounter.getNumberOfQuacks() + " times");
	}

	private void simulator1(AbstractDuckFactory duckFactory) {
		Quackable redheadDuck = duckFactory.createRedheadDuck();
		Quackable duckcall = duckFactory.createDuckCall();
		Quackable rubberDuck = duckFactory.createRubbertDuck();
		Quackable goose = new GooseAdapter(new Goose());
		System.out.println("\nDuck Simulator：With Composite - Flocks");

		Flock flockDucks = new Flock();
		flockDucks.add(redheadDuck);
		flockDucks.add(duckcall);
		flockDucks.add(rubberDuck);
		flockDucks.add(goose);

		Flock flockOfMallards = new Flock();
		Quackable mallardDuckOne = duckFactory.createMallardDuck();
		Quackable mallardDuckTwo = duckFactory.createMallardDuck();
		Quackable mallardDuckThree = duckFactory.createMallardDuck();
		flockOfMallards.add(mallardDuckThree);
		flockOfMallards.add(mallardDuckTwo);
		flockOfMallards.add(mallardDuckOne);

		flockDucks.add(flockOfMallards);

		System.out.println("\nDuck Simulator：Whole Flock Simulation");
		simulate(flockDucks);

		System.out.println("\nDuck Simulator：Whole Mallard Flock Simulation");
		simulate(flockOfMallards);

		System.out.println("The ducks quacked " + QuackCounter.getNumberOfQuacks() + " times");
	}

	private void simulator() {
		Quackable mallardDuck = new QuackCounter(new MallardDucky());
		Quackable redheadDuck = new QuackCounter(new RedheadDuck());
		Quackable duckcall = new QuackCounter(new DuckCall());
		Quackable rubberDuck = new QuackCounter(new RubberDuck());
		Quackable goose = new GooseAdapter(new Goose());
		System.out.println("\nDuck Simulator");
		simulate(mallardDuck);
		simulate(redheadDuck);
		simulate(duckcall);
		simulate(rubberDuck);
		simulate(goose);

		System.out.println("The ducks quacked " + QuackCounter.getNumberOfQuacks() + " times");
	}

	private void simulate(Quackable quack) {
		quack.quack();
	}
}

/**
 * 观察者模式
 * 
 * @author 周奎-
 * 
 */
interface QuackObservable {
	void registerObserver(Observer observer);

	void notifyObservers();
}

interface Observer {
	void update(QuackObservable duck);
}

class Quackologist implements Observer {
	@Override
	public void update(QuackObservable duck) {
		System.out.println("Quackologist: " + duck + " just quacked...");
	}

}

class Observable implements QuackObservable {
	ArrayList<Observer> observers = new ArrayList<Observer>();
	QuackObservable duck;

	public Observable(QuackObservable duck) {
		this.duck = duck;
	}

	@Override
	public void registerObserver(Observer observer) {
		observers.add(observer);
	}

	@Override
	public void notifyObservers() {
		Iterator iterator = observers.iterator();
		while (iterator.hasNext()) {
			Observer observer = (Observer) iterator.next();
			observer.update(duck);
		}
	}

}

/**
 * 组合模式
 * 
 * @author 周奎-
 * 
 */
class Flock implements Quackable {
	ArrayList<Quackable> quackers = new ArrayList<Quackable>();

	public void add(Quackable quacker) {
		quackers.add(quacker);
	}

	@Override
	public void quack() {
		Iterator<Quackable> iterator = quackers.iterator();
		while (iterator.hasNext()) {
			Quackable quacker = iterator.next();
			quacker.quack();
		}
	}

	Observable observable;

	public Flock() {
		observable = new Observable(this);
	}

	@Override
	public void registerObserver(Observer observer) {
		this.observable.registerObserver(observer);
	}

	@Override
	public void notifyObservers() {
		this.observable.notifyObservers();
	}
}

interface Quackable extends QuackObservable {
	void quack();
}

class MallardDucky implements Quackable {

	@Override
	public void quack() {
		System.out.println("MallardDucky-Quack");
		notifyObservers();
	}

	Observable observable;

	public MallardDucky() {
		observable = new Observable(this);
	}

	@Override
	public void registerObserver(Observer observer) {
		this.observable.registerObserver(observer);
	}

	@Override
	public void notifyObservers() {
		this.observable.notifyObservers();
	}
}

class RedheadDuck implements Quackable {
	@Override
	public void quack() {
		System.out.println("RedheadDuck-Quack");
	}

	Observable observable;

	public RedheadDuck() {
		observable = new Observable(this);
	}

	@Override
	public void registerObserver(Observer observer) {
		this.observable.registerObserver(observer);
	}

	@Override
	public void notifyObservers() {
		this.observable.notifyObservers();
	}
}

class DuckCall implements Quackable {
	@Override
	public void quack() {
		System.out.println("DuckCall-Kwak");
	}

	Observable observable;

	public DuckCall() {
		observable = new Observable(this);
	}

	@Override
	public void registerObserver(Observer observer) {
		this.observable.registerObserver(observer);
	}

	@Override
	public void notifyObservers() {
		this.observable.notifyObservers();
	}
}

class RubberDuck implements Quackable {
	@Override
	public void quack() {
		System.out.println("RubberDuck-Squeak");
	}

	Observable observable;

	public RubberDuck() {
		observable = new Observable(this);
	}

	@Override
	public void registerObserver(Observer observer) {
		this.observable.registerObserver(observer);
	}

	@Override
	public void notifyObservers() {
		this.observable.notifyObservers();
	}
}

class Goose {
	public void honk() {
		System.out.println("Goose-Honk");
	}
}

/**
 * 适配器模式
 * 
 * @author 周奎-
 * 
 */
class GooseAdapter implements Quackable {
	Goose goose;

	public GooseAdapter(Goose goose) {
		super();
		this.goose = goose;
	}

	@Override
	public void quack() {
		goose.honk();
	}

	Observable observable;

	public GooseAdapter() {
		observable = new Observable(this);
	}

	@Override
	public void registerObserver(Observer observer) {
		this.observable.registerObserver(observer);
	}

	@Override
	public void notifyObservers() {
		this.observable.notifyObservers();
	}
}

/**
 * 装饰器 统计鸭子的叫声
 * 
 * @author 周奎-
 * 
 */
class QuackCounter implements Quackable {
	Quackable duck;
	static int numberOfQuacks;

	public QuackCounter(Quackable duck) {
		super();
		this.duck = duck;
	}

	@Override
	public void quack() {
		duck.quack();
		numberOfQuacks++;
	}

	public static int getNumberOfQuacks() {
		return numberOfQuacks;
	}

	Observable observable;

	public QuackCounter() {
		observable = new Observable(this);
	}

	@Override
	public void registerObserver(Observer observer) {
		this.observable.registerObserver(observer);
	}

	@Override
	public void notifyObservers() {
		this.observable.notifyObservers();
	}
}

abstract class AbstractDuckFactory {
	public abstract Quackable createMallardDuck();

	public abstract Quackable createRedheadDuck();

	public abstract Quackable createDuckCall();

	public abstract Quackable createRubbertDuck();
}

class DuckFactory extends AbstractDuckFactory {
	@Override
	public Quackable createMallardDuck() {
		return new MallardDucky();
	}

	@Override
	public Quackable createRedheadDuck() {
		return new RedheadDuck();
	}

	@Override
	public Quackable createDuckCall() {
		return new DuckCall();
	}

	@Override
	public Quackable createRubbertDuck() {
		return new RubberDuck();
	}
}

class DuckCounterFactory extends AbstractDuckFactory {
	@Override
	public Quackable createMallardDuck() {
		return new QuackCounter(new MallardDucky());
	}

	@Override
	public Quackable createRedheadDuck() {
		return new QuackCounter(new RedheadDuck());
	}

	@Override
	public Quackable createDuckCall() {
		return new QuackCounter(new DuckCall());
	}

	@Override
	public Quackable createRubbertDuck() {
		return new QuackCounter(new RubberDuck());
	}
}
