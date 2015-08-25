package org.sunney.college.designer;

public class CommondDesigner {
	public static void main(String[] args) {
		CommandRemoteInvoker invoker = new CommandRemoteInvoker();

		Light light = new Light();
		GarageDoor garageDoor = new GarageDoor();

		invoker.setCommand(new LightOffCommand(light));
		invoker.buttonWasPressed();

		invoker.setCommand(new LightOnCommand(light));
		invoker.buttonWasPressed();

		invoker.setCommand(new GarageDoorDownCommand(garageDoor));
		invoker.buttonWasPressed();

		invoker.setCommand(new GarageDoorUpCommand(garageDoor));
		invoker.buttonWasPressed();

		invoker.setCommand(new GarageDoorLightOffCommand(new GarageDoor(new LightOffCommand(light))));
		invoker.buttonWasPressed();

		invoker.setCommand(new GarageDoorLightOnCommand(new GarageDoor(new LightOffCommand(light))));
		invoker.buttonWasPressed();
	}
}

/**
 * 使用命令对象  Invoker
 * 
 * @author 周奎-
 * 
 */
class CommandRemoteInvoker {
	Command slot;

	public CommandRemoteInvoker() {
	}

	public void setCommand(Command command) {
		slot = command;
	}

	public void buttonWasPressed() {
		slot.execute();
	}
}

/**
 * @func 命令接口
 * @author 周奎-
 */
interface Command {
	public void execute();
}

/**
 * 开灯命令
 * 
 * @author 周奎-
 * 
 */
class LightOnCommand implements Command {
	Light light;

	public LightOnCommand(Light light) {
		this.light = light;
	}

	@Override
	public void execute() {
		light.on();
	}
}

/**
 * 关灯命令
 * 
 * @author 周奎-
 * 
 */
class LightOffCommand implements Command {
	Light light;

	public LightOffCommand(Light light) {
		this.light = light;
	}

	@Override
	public void execute() {
		light.off();
	}
}

/**
 * 灯实体
 * 
 * @author 周奎-
 * 
 */
class Light {
	public void on() {
		System.out.println("开灯");
	}

	public void off() {
		System.out.println("关灯");
	}
}

class GarageDoor {
	Command lightCommand;

	public GarageDoor(Command lightCommand) {
		this.lightCommand = lightCommand;
	}

	public GarageDoor() {
	}

	public void up() {
		System.out.println("仓库开门");
	}

	public void down() {
		System.out.println("仓库关门");
	}

	public void lightOn() {
		System.out.print("仓库");
		lightCommand.execute();
	}

	public void lightOff() {
		System.out.print("仓库");
		lightCommand.execute();
	}

	/*
	 * public void lightOn(Light light) { System.out.print("仓库"); light.on(); }
	 * 
	 * public void lightOff(Light light) { System.out.print("仓库"); light.off();
	 * }
	 */
}

class GarageDoorUpCommand implements Command {
	GarageDoor garageDoor;

	public GarageDoorUpCommand(GarageDoor garageDoor) {
		this.garageDoor = garageDoor;
	}

	@Override
	public void execute() {
		garageDoor.up();
	}
}

class GarageDoorDownCommand implements Command {
	GarageDoor garageDoor;

	public GarageDoorDownCommand(GarageDoor garageDoor) {
		this.garageDoor = garageDoor;
	}

	@Override
	public void execute() {
		garageDoor.down();
	}
}

class GarageDoorLightOnCommand implements Command {
	GarageDoor garageDoor;

	public GarageDoorLightOnCommand(GarageDoor garageDoor) {
		this.garageDoor = garageDoor;
	}

	@Override
	public void execute() {
		garageDoor.lightOn();
	}
}

class GarageDoorLightOffCommand implements Command {
	GarageDoor garageDoor;

	public GarageDoorLightOffCommand(GarageDoor garageDoor) {
		this.garageDoor = garageDoor;
	}

	@Override
	public void execute() {
		garageDoor.lightOff();
	}
}
