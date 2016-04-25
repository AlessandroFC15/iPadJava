package ipadjava;

public abstract class Device {
	protected boolean isTurnedOn;
	Data InitialDate;

	Device() {
		isTurnedOn = true;
		InitialDate = new Data(10, 04, 2016);
	};

	Device(boolean state) {
		isTurnedOn = state;
		InitialDate = new Data(10, 04, 2016);
	}

	Device(final Device device) {
		isTurnedOn = device.isTurnedOn;
		InitialDate = new Data(device.InitialDate);
	}

	public void turnOn() {
	}

	public void turnOff() {
		// Check to see if the iPad isn't already turned off.
		if (!isTurnedOn) {
			System.out.println("# Device is already turned off.\n\n");

		} else {
			isTurnedOn = false;

			System.out.println("# Device is now turned off.\n");
		}
	}

	public boolean isOn() {
		return isTurnedOn;
	}

	// Overriding

	public String toString() {
		String output;

		output = ".: Device Specs :.\n" + "\n>> STATUS = "
				+ (isTurnedOn ? "ON" : "OFF") + InitialDate.toString();

		return output;
	}
}
