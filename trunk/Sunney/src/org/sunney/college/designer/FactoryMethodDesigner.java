package org.sunney.college.designer;

public class FactoryMethodDesigner {
	public static void main(String[] args) {
		AbstractProductFactory factory = new ProductFactory();
		Product p = factory.createProduct(BlackProduct.class);
		p.perform();
		System.out.println(p.getClass().getName());
		Object o;
	}
}

abstract class AbstractProductFactory {
	public abstract <T extends Product> Product createProduct(Class<T> type);
}

class ProductFactory extends AbstractProductFactory {
	@Override
	public <T extends Product> Product createProduct(Class<T> type) {
		Product p = null;
		try {
			p = (Product) Class.forName(type.getName()).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return p;
	}
}

interface Product {
	void perform();
}

class BlackProduct implements Product {
	@Override
	public void perform() {
		System.out.println("我是黑色的...");
	}
}

class YellowProduct implements Product {
	@Override
	public void perform() {
		System.out.println("我是黄色的...");
	}
}

class WhiteProduct implements Product {
	@Override
	public void perform() {
		System.out.println("我是白色的...");
	}
}
