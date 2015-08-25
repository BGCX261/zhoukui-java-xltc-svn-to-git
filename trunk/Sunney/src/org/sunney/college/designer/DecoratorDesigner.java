package org.sunney.college.designer;

public class DecoratorDesigner {
	public static void main(String[] args) {
		Bird bird = new Bee();
		System.out.println(bird.getDecription() + "能飞行" + bird.fly() + "米！");
		bird = new RocketBee(bird);
		System.out.println(bird.getDecription() + "能飞行" + bird.fly() + "米！");
	}
}

abstract class Bird {
	private String decription = "Unknow Bird";

	public abstract int fly();

	public String getDecription() {
		return decription;
	}
}

abstract class InsectBird extends Bird {
	public abstract String getDecription();
}

class Bee extends InsectBird {
	public int fly() {
		return 10;
	}

	public String getDecription() {
		return "Bee";
	}
}

class RocketBee extends Bee {
	Bird bird;

	public RocketBee(Bird bird) {
		this.bird = bird;
	}

	public int fly() {
		return 1000 + bird.fly();
	}

	public String getDecription() {
		return bird.getDecription() + ",Rocket";
	}
}
