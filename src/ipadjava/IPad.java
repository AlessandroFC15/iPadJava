package ipadjava;

public class IPad extends Tablet {
	IPad() {
		System.out.println(".:. iPad Creation .:.\n");

		touchID = new TouchID();

		installDefaultApps();

		openDefaultApps();

		setInitialSecuritySystem();

		numberOfiPads++;
	}

	IPad(int storage) {
		super(storage);

		System.out.println(".:. iPad Creation .:.\n");

		touchID = new TouchID();

		installDefaultApps();

		openDefaultApps();

		setInitialSecuritySystem();

		numberOfiPads++;
	}

	IPad(IPad oldIPad) {
		super(oldIPad);

		typeOfLockScreen = oldIPad.typeOfLockScreen;
		touchID = new TouchID(oldIPad.touchID);

		numberOfiPads++;
	}

	public void turnOn() {
		if (isOn()) {
			System.out.println("\n# iPad is already turned on.\n");
		} else {
			isTurnedOn = true;
			openDefaultApps();
			System.out.println("\n# iPad is now turned on.\n");
		}
	}

	

	public static void updateIOSVersion() {
		latestIOSVersion += 0.1;
	}

	public static int getNumberOfiPads() {
		return numberOfiPads;
	}

	// HELPER FUNCTIONS
	private void setInitialSecuritySystem() {
		int choice;
		System.out.println("\n|| CHOICE OF SECURITY SYSTEM ||\n"
				+ "\n| 1 - TouchID" + "\n| 2 - Regular Password\n");

		while (true) {
			System.out.print(">> Enter your choice: ");

			choice = cin.nextInt();
			cin.nextLine();

			if (choice == 1) {
				typeOfLockScreen = TOUCH_ID;
				setTouchID();
				break;
			} else if (choice == 2) {
				typeOfLockScreen = PASSWORD;
				setLockScreenPassword();
				break;
			} else {
				System.out.println("# Invalid choice. Try again! #\n");
			}
		}
	}

	public void setTouchID() {
		touchID.addFingerPrint();
	}

	private boolean unlockPassword() {
		// The process of unlocking the screen by password of the iPad
		// is the same of a generic Tablet. For that reason, we simply
		// use the method to unlock the screen of the class Tablet
		return super.unlockScreen();
	}

	private boolean unlockTouchID() {
		if (touchID.unlockScreen()) {
			screenLocked = false;
			return true;
		}

		return false;
	}

	private void installDefaultApps() {

		// Installing Google
		appsInstalled.put("Google", 200.00);
		freeMemory -= appsInstalled.get("Google") / 1000;

		// Installing Safari
		appsInstalled.put("Safari", 100.00);
		freeMemory -= appsInstalled.get("Safari") / 1000;

		// Installing YouTube
		appsInstalled.put("YouTube", 50.00);
		freeMemory -= appsInstalled.get("YouTube") / 1000;

		// Installing iTunes
		appsInstalled.put("iTunes", 100.00);
		freeMemory -= appsInstalled.get("iTunes") / 1000;
	}

	private void openDefaultApps() {
		if (isAppInstalled("Google"))
			openApp("Google");

		if (isAppInstalled("iTunes"))
			openApp("iTunes");
	}

	public String toString() {
		String output = super.toString() + "\n>> iOS VERSION = "
				+ latestIOSVersion + "\n>> TYPE OF LOCK SCREEN = "
				+ (typeOfLockScreen == TOUCH_ID ? "TOUCH ID" : "PASSWORD");

		return output;
	}
	
	// Implementação do outro método sobrecarregado de Lockable
	
	public void setLockScreenPassword(String password) {
		while (true) {
			if ((password.length() >= 4) && (password.length() <= 32)) {
				lockScreenPassword = password;
				System.out
						.println("\n|| Lock screen password set successfully ||");
				break;
			}

			System.out
					.println("\n# Password must be 4 to 32 chars. Try again. #\n");
			
			System.out
			.print("\n>> Set initial password to lock screen (4-32 chars): ");

			password = cin.nextLine();
		}
	}
	
	// Sobrescrita do "método qualquer"
	
	public boolean unlockScreen() {
		// Check to see if the screen is indeed locked
		if (!isScreenUnlocked()) {
			// Depending of the type of lock screen selected,
			// the procedures are different.
			if (typeOfLockScreen == PASSWORD) {
				return unlockPassword();
			} else if (typeOfLockScreen == TOUCH_ID) {
				return unlockTouchID();
			}
		}

		System.out.println("\n| Screen was already unlocked. |\n");

		return false;
	}

	// ATTRIBUTES
	private int typeOfLockScreen;
	private TouchID touchID;

	/* STATIC VARIABLES */
	private static float latestIOSVersion = 9;
	private static int numberOfiPads = 0;

	/* CONSTANTS */
	private final static int TOUCH_ID = 1;
	private final static int PASSWORD = 2;
}
