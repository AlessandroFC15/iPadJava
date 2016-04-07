package ipadjava;

public abstract class Device {

	Device() {
	};

	Device(boolean state) {
	}

	Device(Device device) {
	}

	public void turnOn() {
	}

	public void turnOff() {
	}

	public boolean isOn() {
		return isTurnedOn;
	}

	protected boolean isTurnedOn;
	Data InitialDate;
}
