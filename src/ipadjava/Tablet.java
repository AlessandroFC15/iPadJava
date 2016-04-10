package ipadjava;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public abstract class Tablet extends Device implements InternetConnectable {

	public static void main(String[] args) {
		IPad ipad = new IPad();

		ipad.unlockScreen();
	}

	protected float storageCapacity; // Measured in GB
	protected float freeMemory; // Measured in GB
	protected String lockScreenPassword;
	protected boolean screenLocked;
	protected boolean wiFiOn;
	protected boolean mobileDataOn;
	protected HashMap<String, Double> appsInstalled;
	protected ArrayList<String> activeApps;

	Tablet() {
		setSpecsToDefault();
	}

	Tablet(int storage) {
		setSpecsToDefault();

		storageCapacity = validateValue(storage, 1, 128, "storage capacity");

		freeMemory = storageCapacity;
	}

	Tablet(Tablet oldTablet) {
		super(oldTablet);

		storageCapacity = oldTablet.storageCapacity;
		freeMemory = oldTablet.freeMemory;
		screenLocked = oldTablet.screenLocked;
		lockScreenPassword = oldTablet.lockScreenPassword;
		wiFiOn = oldTablet.wiFiOn;
		mobileDataOn = oldTablet.mobileDataOn;
		appsInstalled = new HashMap<>(oldTablet.appsInstalled);
		activeApps = new ArrayList<>(oldTablet.activeApps);
	}

	public boolean installApp(String name, double size) {
		// Check to see if the app isn't already installed
		if (!isAppInstalled(name)) {
			// Proceed to check if there is enough space to install the app
			if (size / 1000 <= freeMemory) {
				appsInstalled.put(name, size);

				freeMemory -= size / 1000;

				System.out.println("|| The app " + name
						+ " was successfully installed. ||\n");

				return true;
			} else {
				System.out.println("There isn't enough space to install "
						+ "the " + name
						+ " app. Consider uninstalling some other apps.");

				return false;
			}
		} else {
			System.out.println("# App " + name + " is already installed. #\n");

			return false;
		}
	}

	public boolean uninstallApp(String name) {
		// Check to see if the app is indeed installed
		if (isAppInstalled(name)) {
			System.out.println("|| Uninstalling " + name + "... ||\n");

			// Close the app, in case it is open
			closeApp(name);

			// The memory the app held is set free. Needs to convert to GB.
			freeMemory += appsInstalled.get(name) / 1000;

			// Proceed to erase the app.
			appsInstalled.remove(name);

			System.out.println("|| The app " + name
					+ " was successfully uninstalled. ||\n");

			return true;
		} else {
			System.out.println("# The app " + name + " isn't installed. #\n");

			return false;
		}
	}

	public boolean openApp(String name) {
		// Check to see if the app is installed
		if (isAppInstalled(name)) {
			// Check to see if the app isn't already open
			if (!isAppOpen(name)) {
				activeApps.add(name);

				return true;
			} else {
				System.out.println("\n# App " + name + " is already open. #\n");

				return false;
			}
		} else {
			System.out.println("\n# Couldn't open app " + name
					+ ", because it isn't installed. #\n");

			return false;
		}
	}

	public boolean closeApp(String name) {
		// Check to see if the app is installed.
		if (isAppInstalled(name)) {
			// Check to see if the app is open.
			if (isAppOpen(name)) {
				activeApps.remove(name);
				System.out.println("|| App " + name
						+ " was successfully closed. ||\n");
				return true;
			} else {
				System.out.println("# App " + name + " wasn't open. #\n");
				return false;
			}
		}

		System.out.println("# App " + name + " isn't even installed. #\n");
		return false;
	}

	public void showAppsInstalled() {
		// Check to see if the iPad isn't empty.
		if (!appsInstalled.isEmpty()) {
			System.out.println(".: APPS INSTALLED :.\n");

			// Iterate through appsInstalleds
			for (String nameOfApp : appsInstalled.keySet()) {
				System.out.println("Name: " + nameOfApp + " | "
						+ appsInstalled.get(nameOfApp) + "MB");
			}
		} else {
			System.out.println(">> There are no apps installed <<\n");
		}
	}

	public void showActiveApps() {
		// Check to see if there is any active app.
		if (!activeApps.isEmpty()) {
			System.out.println("\n.: ACTIVE APPS :.\n");

			for (String nameOfApp : activeApps) {
				System.out.println(">> " + nameOfApp);
			}
		} else {
			System.out.println(">> There are no apps open <<\n");
		}
	}

	public boolean closeAllApps() {
		// Check to see if there is any app open.
		if (!activeApps.isEmpty()) {
			// Clear the vector that holds the name of the active apps.
			activeApps.clear();
			System.out.println("\n|| All apps were closed. ||\n");

			return true;
		} else {
			System.out.println("\n|| All apps were already closed. ||\n");
			return false;
		}
	}

	public boolean uninstallAllApps() {
		// Check to see if there is any app in the device
		if (!appsInstalled.isEmpty()) {
			// Close all apps
			activeApps.clear();

			// Uninstall all apps
			appsInstalled.clear();

			// Reset memory to its initial state.
			freeMemory = storageCapacity;

			System.out.println("\n\n|| All apps were uninstalled ||\n\n");

			return true;
		} else {
			System.out.println("\n\n|| There are no apps in the iPad. ||\n\n");
			return false;
		}
	}

	public boolean unlockScreen() {

		if (!isScreenUnlocked()) {
			String password;
			while (true) {
				System.out
						.print("\n>> Enter password to unlock screen (0 to Quit): ");

				password = cin.nextLine();

				if (password.equals("0")) {
					System.out
							.println("|| Unlock process was cancelled. iPad remains locked. ||\n");
					return false;
				} else if ((password.length() >= 4) & (password.length() <= 32)) {
					if (password.equals(lockScreenPassword)) {
						screenLocked = false;
						System.out.println("\n|| Screen is now unlocked ||\n");
						return true;
					} else {
						System.out.println("\n# Wrong password. Try again #\n");
					}
				} else {
					System.out
							.println("\n# Password must be 4 to 32 chars. Try again #\n");
				}
			}
		}

		System.out.println("\n| Screen was already unlocked. |\n");
		return false;
	}

	public boolean lockScreen() {
		// Check to see if the screen is indeed unlocked.
		if (isScreenUnlocked()) {
			screenLocked = true;
			System.out.println("\n|| Screen is now locked ||\n");
			return true;
		} else {
			System.out.println("\n| Screen was already locked. |\n");
			return false;
		}
	}

	public boolean isScreenUnlocked() {
		return !screenLocked;
	}

	public void turnWiFiOn() {
		if (wiFiOn) {
			System.out.println("\n# WiFi is already turned on.\n");
		} else {
			wiFiOn = true;
			System.out.println("\n# WiFi is now turned on.\n");
		}
	}

	public void turnWiFiOff() {
		if (!wiFiOn) {
			System.out.println("\n# WiFi is already turned off.\n");
		} else {
			wiFiOn = false;
			System.out.println("\n# WiFi is now turned off.\n");
		}
	}

	public void turnMobileDataOn() {
		if (mobileDataOn) {
			System.out
					.println("\n# Mobile data network is already turned on.\n");
		} else {
			mobileDataOn = true;
			System.out.println("\n# Mobile data network is now turned on.\n");
		}
	}

	public void turnMobileDataOff() {
		if (!mobileDataOn) {
			System.out
					.println("\n# Mobile data network is already turned off.\n");
		} else {
			mobileDataOn = false;
			System.out.println("\n# Mobile data network is now turned off.\n");
		}
	}

	public boolean isInternetAvailable() {
		return wiFiOn || mobileDataOn;
	};

	public boolean isDeviceEmpty() {
		return appsInstalled.isEmpty();
	}

	public boolean isAnyAppOpen() {
		return !activeApps.isEmpty();
	}

	protected float validateValue(float value, float min, float max, String name) {
		while (true) {
			if (value >= min && value <= max) {
				break;
			}

			System.out.println("\n>> Invalid value for " + name
					+ ". Must be between " + min + " and " + max + ".");

			System.out.print("\n>> Enter a new value: ");
			value = cin.nextFloat();
		}

		return value;
	};

	protected void setLockScreenPassword() {
		String password;
		while (true) {
			System.out
					.print("\n>> Set initial password to lock screen (4-32 chars): ");

			password = cin.nextLine();

			if ((password.length() >= 4) && (password.length() <= 32)) {
				lockScreenPassword = password;
				System.out
						.println("\n|| Lock screen password set successfully ||\n");
				break;
			}

			System.out
					.println("\n# Password must be 4 to 32 chars. Try again. #\n");
		}
	}

	protected boolean isAppInstalled(String name) {
		// Find the app in the unordered map appsInstalled.
		return appsInstalled.containsKey(name);
	}

	private boolean isAppOpen(String name) {
		return activeApps.contains(name);
	}

	private void setSpecsToDefault() {
		storageCapacity = 32;
		freeMemory = storageCapacity;
		screenLocked = true;
		wiFiOn = true;
		mobileDataOn = false;
		lockScreenPassword = "";
		appsInstalled = new HashMap<String, Double>();
		activeApps = new ArrayList<>();
	}

	private void installDefaultApps() {}

	public String toString() {
		return super.toString() + "\n>> STORAGE CAPACITY = " + storageCapacity
				+ "GB" + "\n>> FREE MEMORY = " + freeMemory + "GB"
				+ "\n>> NUM OF APPS INSTALLED = " + appsInstalled.size()
				+ "\n>> NUM OF ACTIVE APPS = " + activeApps.size()
				+ "\n>> SCREEN LOCKED = " + (screenLocked ? "YES" : "NO");
	}

	static Scanner cin = new Scanner(System.in);
}
