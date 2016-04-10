package ipadjava;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public abstract class Tablet extends Device implements InternetConnectable {

	public static final int IPAD = 0;
	public static final int SAMSUNG = 1;
	
	public static void main(String[] args) {
		Tablet iPad = new IPad();

		menu(iPad);
		
		System.out.println(iPad.toString());
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
						.println("\n|| Lock screen password set successfully ||");
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
	
	private static void menu(Tablet tablet)
	{
	    int typeOfObject;
	    
	    if (tablet instanceof IPad)
	    {
	        typeOfObject = IPAD;
	    } else if (tablet instanceof SamsungTablet)
	    {
	        typeOfObject = SAMSUNG;
	    } else
	    {
	        return;
	    }
	    
	    String choice;
	    int op;
	    
	    do {
	    	// The options will change accordingly to the type of object.
	        
	        System.out.print("\n\t\t|| " + (typeOfObject == IPAD? "iPad" : "Samsung") + " Control Center ||\n"
	        + "\n1 => Show Open Apps"
	        + "\t\t2 => Install App"
	        + "\n\n3 => Uninstall App"
	        + "\t\t4 => Show Apps Installed"
	        + "\n\n5 => Get " + (typeOfObject == IPAD? "iPad" : "Samsung") + " Specs"
	        + "\t\t6 => Open App"
	        + "\n\n7 => Close App"
	        + "\t\t\t8 => Close All Apps"
	        + "\n\n9 => Uninstall All Apps"
	        + "\t\t10 => Turn Wi-Fi On"
	        + "\n\n11 => Turn Wi-Fi Off"
	        + "\t\t12 => Turn Mobile Data On"
	        + "\n\n13 => Turn Mobile Data Off"
	        + "\t14 => Unlock Screen"
	        + "\n\n15 => Lock Screen"
	        + "\t\t16 => Turn On"
	        + "\n\n17 => Turn Off"
	        + "\t\t\t18 => " + (typeOfObject == IPAD? "Get Number of iPads Created" : "Insert SD Card")
	        + "\n\n19 => " + (typeOfObject == IPAD? "Update iOS Version" : "Remove SD Card")
	        + (typeOfObject == SAMSUNG? "\t\t20 => Change SD Card\n\n" : "\t")
	        + "-1 => Quit\n");
	        
	        while (true)
	        {
	        	System.out.print("\n>> Enter your option: ");
	        	choice = cin.nextLine();
	        	
	        	try {
		        	op = Integer.parseInt(choice);
		        	break;
		        } catch (NumberFormatException e)
		        {
		        	System.out.println("\n# Enter a valid number #");
		        }
	        }
	        
	        // For the options between 1 and 13, the program will only proceed if the tablet is turned on 
	        // and also if it is unlocked.
	        
	        if (op >= 1 && op <= 13)
	        {
	        	if (tablet.isOn())
	        	{
	        		if (tablet.isScreenUnlocked())
	        		{
	        			// With the tablet on and unlocked, we can proceed to perform the option chosen.
	                    switch (op)
	                    {
	                        case 1:
	                            tablet.showActiveApps();
	                            break;
	                        case 2:
	                            // To download any app, internet must be available
	                            if (tablet.isInternetAvailable())
	                            {
	                                chooseAppToInstall(tablet);
	                            } else
	                            {
	                                System.out.println("\n# There is no internet connection to download apps. "
	                                		+ "\n# Turn WiFi or mobile data on. \n");
	                            }
	                            break;
	                        case 3:
	                            chooseAppToUninstall(tablet);
	                            break;
	                        case 4:
	                            tablet.showAppsInstalled();
	                            break;
	                        case 5:
	                            System.out.println(tablet.toString());
	                            break;
	                        case 6:
	                            chooseAppToOpen(tablet);
	                            break;
	                        case 7:
	                            chooseAppToClose(tablet);
	                            break;
	                        case 8:
	                            tablet.closeAllApps();
	                            break;
	                        case 9:
	                            tablet.uninstallAllApps();
	                            break;
	                        case 10:
	                            tablet.turnWiFiOn();
	                            break;
	                        case 11:
	                            tablet.turnWiFiOff();
	                            break;
	                        case 12:
	                            tablet.turnMobileDataOn();
	                            break;
	                        case 13:
	                            tablet.turnMobileDataOff();
	                            break;
	                    }
	        		} else 
	                {
	                    System.out.println("\n# Unlock the tablet sreen first #\n");
	                }
	            } else 
	            {
	                System.out.println("\n# Turn the tablet on first #\n");
	            }
	        } else if (op >= 14 && op <= 15)
	        {
	        	// For the options 14 and 15, we will only check if the tablet is on.
	            if (tablet.isOn())
	            {
	                switch (op)
	                {
	                    case 14:
	                        tablet.unlockScreen();
	                        break;
	                    case 15:
	                        tablet.lockScreen();
	                        break;
	                }
	            } else 
	            {
	                 System.out.println("# Turn the tablet on first #\n");
	            }
	        } else 
	        {
	        	// For the other options, there are no pre-conditions that the iPad must be in.
	            switch (op)
	            {
	                case 16:
	                    tablet.turnOn();
	                    break;
	                case 17:
	                    tablet.closeAllApps();
	                    tablet.turnOff();
	                    break;
	                case 18:
	                    if (typeOfObject == IPAD)
	                    {
	                        System.out.println(">> NUMBER OF IPADS = " + IPad.getNumberOfiPads());
	                    } else
	                    {
	                        ((SamsungTablet) tablet).increaseStorage();
	                    }
	                    
	                    break;
	                case 19:
	                    if (typeOfObject == IPAD)
	                    {
	                        IPad.updateIOSVersion();
	                       System.out.println(">> IOS Version update for all iPads. ");
	                    } else
	                    {
	                        ((SamsungTablet) tablet).removeExtraStorage();
	                    }
	                    
	                    break;
	                case 20:
	                    if (typeOfObject == IPAD)
	                    {
	                        System.out.println("\n# Invalid option. Try again.");
	                    } else
	                    {
	                        ((SamsungTablet) tablet).changeExtraStorage();
	                    }
	                    
	                    break;
	                case -1:
	                    System.out.println("# PROGRAM FINISHED #\n");
	                    break;
	                default:
	                    System.out.println("\n# Invalid option. Try again.");
	                    break;
	            }
	        }
	    } while (op != -1);
	}
	
	private static void addAppsToInstall(HashMap<String, Double> appsToInstall)
	{
		appsToInstall.put("Instagram", 23.0);
		appsToInstall.put("Facebook", 108.0);
		appsToInstall.put("Snapchat", 73.0);
		appsToInstall.put("Messenger", 89.0);
		appsToInstall.put("Uber", 63.0);
		appsToInstall.put("Spotify", 84.0);
		appsToInstall.put("Netflix", 33.0);
		appsToInstall.put("Twitter", 69.0);
		appsToInstall.put("Google Maps", 47.0);
		appsToInstall.put("Periscope", 17.0);
	}

	private static void chooseAppToInstall(Tablet tablet)
	{
		// Array of pairs to hold the apps to be presented in App Store
		HashMap<String, Double> appsToInstall = new HashMap<>();
		
		addAppsToInstall(appsToInstall);
		
		String choice;
		
	    while (true) 
	    {
	        System.out.println("\t\t.: App Store :.\n");
	        
	        for (String nameOfApp : appsToInstall.keySet())
	        {
	        	System.out.println("=> " + nameOfApp + " (" + appsToInstall.get(nameOfApp) + "MB)");
	        }
	        
	        System.out.println("INS => Install Another App");
	        System.out.println("QUIT => Quit App Store");
	        
	        System.out.print("\n>> Enter your choice: ");
	        choice = cin.nextLine();
	        
	        if (appsToInstall.containsKey(choice))
	        {
	        	if (tablet.installApp(choice, appsToInstall.get(choice)))
	            {
	                System.out.println("\n|| App " + choice + " was successfully installed. ||\n");
	            }
	        } else if (choice.toUpperCase().equals("INS"))
	        {
	        	String nameOfApp;
	        	double sizeOfApp;
	        	
	            // Validate the name of the app.
	            while (true)
	            {
	                System.out.print("\n\n>> Enter name of app (Max 25 chars): ");
	                
	                nameOfApp = cin.nextLine();
	                
	                if (nameOfApp.length() > 0)
	                {
	                    nameOfApp = nameOfApp.substring(0, 24);
	                    break;
	                }
	                
	                System.out.println("# Invalid name. Try again.");
	            }
	            
	            // Validate the size of the app.
	            while (true)
	            {
	                System.out.print("\n\n>> Enter size of app in MB: ");
	                sizeOfApp = cin.nextFloat();
	                cin.nextLine();
	                
	                if (sizeOfApp > 0)
	                {
	                    break;
	                }
	                
	                System.out.println("\n# Invalid size. Try again.");
	            }
	                
	            tablet.installApp(nameOfApp, sizeOfApp);
	            
	        } else if (choice.toUpperCase().equals("QUIT"))
	        {
	            System.out.println("# App Store closed #\n");
	            break;
	        } else
	        {
	            System.out.println("# Invalid option. Try again #\n");
	        }
	    }
	}
	
	private static void chooseAppToUninstall(Tablet tablet)
	{
		// Check to see if the iPad is not empty
	    if (! tablet.isDeviceEmpty())
	    {
	        // Show a list of installed apps
	        tablet.showAppsInstalled();
	        
	        // Ask the user to select an app
	        while (true)
	        {
	            String nameOfApp;
	            
	            System.out.print("\n>> Enter name of the app to be uninstalled (X to quit): "); 
	            nameOfApp = cin.nextLine();
	            
	            if (nameOfApp.toUpperCase().equals("X"))
	            {
	                break;
	            }
	            
	            tablet.uninstallApp(nameOfApp);
	        }
	        
	    } else 
	    {
	        System.out.println("\n|| There are no apps installed ||\n");
	    }
	}
	
	private static void chooseAppToOpen(Tablet tablet)
	{
	    // Check to see there are apps installed to open
	    if (! tablet.isDeviceEmpty())
	    {
	        // Show a list of installed apps
	        tablet.showAppsInstalled();
	        
	        // Ask the user to select an app to open
	        while (true)
	        {
	            String nameOfApp;
	            
	            System.out.print("\n>> Enter name of the app to open (X to quit): "); 
	            nameOfApp = cin.nextLine();
	            
	            if (nameOfApp.toUpperCase().equals("X"))
	            {
	                break;
	            }
	            
	            tablet.openApp(nameOfApp);
	        }
	    } else 
	    {
	    	System.out.println("\n|| There are no apps installed ||\n");
	    }
	}
	
	private static void chooseAppToClose(Tablet tablet)
	{
		// Check to see if there are any apps active
	    if (tablet.isAnyAppOpen())
	    {
	        // Show apps that are open.
	        tablet.showActiveApps();
	        
	        while (true)
	        {
	        	String nameOfApp;
	            
	            System.out.print("\n>> Enter name of the app to clos (X to quit): "); 
	            nameOfApp = cin.nextLine();
	            
	            if (nameOfApp == "X" || nameOfApp == "x")
	            {
	                break;
	            }
	            
	            tablet.closeApp(nameOfApp);
	        }
	    } else 
	    {
	        System.out.println("\n|| There are no apps open ||\n");
	    }
	}

	static Scanner cin = new Scanner(System.in);
}
