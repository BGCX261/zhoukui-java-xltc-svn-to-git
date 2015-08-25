package org.sunney.college.designer;

import java.util.ArrayList;
import java.util.Iterator;

public class IteratorDesigner {
	public static void main(String[] args) {
		PacakeHourseMenu breakfastMenu = new PacakeHourseMenu();
		DineMenu dineMenu = new DineMenu();

		printMneu(breakfastMenu.iterator());
		printMneu(dineMenu.iterator());
	}

	private static void printMneu(Iterator<MenuItem> iterator) {
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}
}

class PacakeHourseMenu {
	ArrayList<MenuItem> menuItems;

	public PacakeHourseMenu() {
		menuItems = new ArrayList<MenuItem>();
		addItem("K&B's Pancake Breakfast", "Pancakes with scrambled eggs, and toast", true, 2.99);
		addItem("K&B's Pancake Breakfast", "Pancakes with scrambled eggs, and sausage", true, 8.00);
	}

	private void addItem(String name, String description, boolean vegetarian, double price) {
		MenuItem menu = new MenuItem(name, description, vegetarian, price);
		menuItems.add(menu);
	}

	public Iterator<MenuItem> iterator() {
		return menuItems.iterator();
	}
}

class DineMenu {
	static final int MAX_ITEMS = 6;
	int numberOfItems = 0;
	MenuItem[] menuItems;

	public DineMenu() {
		menuItems = new MenuItem[MAX_ITEMS];
		addItem("Hot Dog Dine", "Pancakes with scrambled eggs, and toast", true, 5.56);
		addItem("Soup of Dine", "Pancakes with scrambled eggs, and sausage", true, 12.55);
	}

	private void addItem(String name, String description, boolean vegetarian, double price) {
		MenuItem menu = new MenuItem(name, description, vegetarian, price);
		if (numberOfItems >= MAX_ITEMS) {
			System.err.println("满了...");
		} else {
			menuItems[numberOfItems++] = menu;
		}
	}

	public Iterator<MenuItem> iterator() {
		return new DineMenuIterator(menuItems);
	}
}

class DineMenuIterator implements Iterator<MenuItem> {
	MenuItem[] menuItems;
	int position = 0;

	public DineMenuIterator(MenuItem[] menuItems) {
		this.menuItems = menuItems;
	}

	@Override
	public boolean hasNext() {
		if (position >= menuItems.length || menuItems[position] == null) {
			return false;
		}
		return true;
	}

	@Override
	public MenuItem next() {
		return menuItems[position++];
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("暂时不支持此方法...");
	}

}

class MenuItem {
	String name;
	String description;
	boolean vegetarian;
	double price;

	public MenuItem(String name, String description, boolean vegetarian, double price) {
		super();
		this.name = name;
		this.description = description;
		this.vegetarian = vegetarian;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isVegetarian() {
		return vegetarian;
	}

	public void setVegetarian(boolean vegetarian) {
		this.vegetarian = vegetarian;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "MenuItem [name=" + name + ", description=" + description + ", vegetarian=" + vegetarian + ", price=" + price + "]";
	}

}
