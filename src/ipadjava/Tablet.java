package ipadjava;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Tablet extends Device {

	Tablet() {
	}

	Tablet(int storageCapacity) {
	}

	Tablet(Tablet oldTablet) {
	}

	public boolean installApp(String name, float size) {
		return true;
	}

	public boolean uninstallApp(String name) {
		return true;
	}

	public boolean openApp(String name) {
		return true;
	}

	public boolean closeApp(String name) {
		return true;
	}

	public void showAppsInstalled() {
	}

	public void showActiveApps() {
	}

	public boolean closeAllApps() {
		return true;
	}

	public boolean uninstallAllApps() {
		return true;
	}

	public boolean unlockScreen() {
		return true;
	}

	public boolean lockScreen() {
		return true;
	}

	public boolean isScreenUnlocked() {
		return !screenLocked;
	}

	public void turnWiFiOn() {
	}

	public void turnWiFiOff() {
	}

	public void turnMobileDataOn() {
	}

	public void turnMobileDataOff() {
	}

	public boolean isInternetAvailable() {
		return wiFiOn || mobileDataOn;
	};

	public boolean isDeviceEmpty() {
		return true;
	}

	public boolean isAnyAppOpen() {
		return true;
	}

	protected float validateValuevalidateValue(float value, float min,
			float max, String name) {
		return -1;
	};

	protected void setLockScreenPassword() {
	}

	protected boolean isAppInstalled(String name) {
		return true;
	}

	private boolean isAppOpen(String name) {
		return true;
	}

	private void setSpecsToDefault() {
	}

	private void installDefaultApps() {
	}

	protected float storageCapacity; // Measured in GB
	protected float freeMemory; // Measured in GB
	protected String lockScreenPassword;
	protected boolean screenLocked;
	protected boolean wiFiOn;
	protected boolean mobileDataOn;
	HashMap appsInstalled;
	ArrayList<String> activeApps;

}
