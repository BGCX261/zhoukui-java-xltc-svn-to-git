package org.sunney.college.designer;

import java.util.Observable;
import java.util.Observer;

public class ObeserverDesigner extends Observable {
	private float temperture;
	private float humidity;
	private float pressure;

	public ObeserverDesigner() {
	}

	public void measurementsChanged() {
		setChanged();
		notifyObservers();
	}

	public void setMeasurements(float temperture, float humidity, float pressure) {
		this.temperture = temperture;
		this.humidity = humidity;
		this.pressure = pressure;

		measurementsChanged();
	}

	public float getTemperture() {
		return temperture;
	}

	public float getHumidity() {
		return humidity;
	}

	public float getPressure() {
		return pressure;
	}

}

class CurrentConditionsDisplay implements Observer {
	private Observable oberservable;
	private float temperture;
	private float humidity;
	private float pressure;

	public CurrentConditionsDisplay(Observable oberservable) {
		this.oberservable = oberservable;
		this.oberservable.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof ObeserverDesigner) {
			ObeserverDesigner weatherData = (ObeserverDesigner) o;
			this.temperture = weatherData.getTemperture();
			this.humidity = weatherData.getHumidity();
			this.pressure = weatherData.getPressure();
			display();
		}
	}

	public void display() {
		System.out.println(this);
	}

	@Override
	public String toString() {
		return "CurrentConditionsDisplay [temperture=" + temperture + ", humidity=" + humidity + ", pressure=" + pressure + "]";
	}

}
