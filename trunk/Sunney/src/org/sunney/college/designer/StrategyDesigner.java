package org.sunney.college.designer;

public class StrategyDesigner {
	public static void main(String[] args) {
		Duck mallard = new MallardDuck();
		mallard.setFly(new FlyNoWay());
		mallard.display();
		mallard.performFly();
	}
}

abstract class Duck {
	private Fly fly;
	private Quack quack;

	public abstract void display();

	public void swim() {
		System.out.println("Duck: I'm swiming...");
	}

	public void performFly() {
		fly.fly();
	}

	public void performQuack() {
		quack.quack();
	}

	public void setFly(Fly fly) {
		this.fly = fly;
	}

	public void setQuack(Quack quack) {
		this.quack = quack;
	}
}

class MallardDuck extends Duck {

	@Override
	public void display() {
		System.out.println("I'm a real Mallard duck!");
	}

}

interface Fly {
	void fly();
}

interface Quack {
	void quack();
}

class FlyNoWay implements Fly {

	@Override
	public void fly() {
		System.out.println("I can not fly...");
	}

}

class FlyWithWing implements Fly {

	@Override
	public void fly() {
		System.out.println("I can fly...");
	}

}
