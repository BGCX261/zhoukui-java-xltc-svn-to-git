package org.sunney.ztest;

public class Test {
	public static void main(String[] args) {
		System.out.println(Integer.toHexString(527));
	}
}

enum SpaceShip {
	SCOUT, CARGO;
	public String toString() {
		String id = name();
		return id;
	}
}
