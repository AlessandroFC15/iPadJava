package ipadjava;

public class SamsungTablet extends Tablet implements Expandable {
	private boolean externalSDCard;
	private int sizeSDCard;

	SamsungTablet() {
		System.out.println(".:. Samsung Tablet Creation .:.");

		externalSDCard = false;
		sizeSDCard = 0;

		installDefaultApps();

		openDefaultApps();

		setLockScreenPassword();
	}

	SamsungTablet(int storage) {
		super(storage);

		System.out.println(".:. Samsung Tablet Creation .:.\n");

		externalSDCard = false;
		sizeSDCard = 0;

		installDefaultApps();

		openDefaultApps();

		setLockScreenPassword();
	}

	SamsungTablet(SamsungTablet oldTablet) {
		super(oldTablet);

		externalSDCard = oldTablet.externalSDCard;
		sizeSDCard = oldTablet.sizeSDCard;
	}

	public void turnOn() {
		if (isOn()) {
			System.out.println("# SamsungTablet is already turned on.\n");
		} else {
			isTurnedOn = true;
			openDefaultApps();
			System.out.println("# SamsungTablet is now turned on.\n");
		}

	}

	private boolean insertSDCard() {
		if (!externalSDCard) {
			int storage;
			System.out.print("\n>> Enter the size of the SD Card in GB: ");
			storage = cin.nextInt();

			sizeSDCard = (int) validateValue(storage, 2, 128, "SD card size");

			externalSDCard = true;
			storageCapacity += sizeSDCard;
			freeMemory += sizeSDCard;

			System.out.println("\n|| SD Card successfully inserted ||\n");

			return true;
		} else {
			System.out
					.println("# There is already a SD card inserted in the tablet. #\n"
							+ "\n# Remove it first and try again! #\n");

			return false;
		}
	}

	private boolean removeSDCard() {
		if (externalSDCard) {
			String choice;

			System.out.print("\n>> Are you sure you want to remove "
					+ "your SD Card (" + sizeSDCard + "GB) (Y or N): ");
			choice = cin.nextLine();

			if (choice.toUpperCase().equals("Y")) {
				externalSDCard = false;
				storageCapacity -= sizeSDCard;

				// If there isn't enough space in the tablet without the SD
				// card,
				// then all the apps will be uninstalled.
				if (freeMemory - sizeSDCard < 0) {
					appsInstalled.clear();
					activeApps.clear();
					freeMemory = storageCapacity;
				} else {
					freeMemory -= sizeSDCard;
				}

				sizeSDCard = 0;

				System.out.println("|| SD Card successfully removed ||\n");

				return true;
			} else {
				System.out.println("# SD Card wasn't removed #\n");
				return false;
			}
		} else {
			System.out.println("\n# No SD card to remove in the tablet. #\n");
			return false;
		}
	}

	private boolean changeSDCard() {
		if (externalSDCard) {
			float storage;
			System.out
					.print("\n>> Enter the size of the new SD Card (in GB): ");
			storage = cin.nextFloat();

			if (!removeSDCard()) {
				return false;
			}

			sizeSDCard = (int) validateValue(storage, 2, 128, "SD card size");

			externalSDCard = true;

			storageCapacity += sizeSDCard;
			freeMemory += sizeSDCard;

			System.out.println("|| SD Card successfully changed. ||\n");

			return true;
		} else {
			String choice;
			System.out
					.println("# No SD card to perfom change in the tablet. #");
			System.out
					.print("\n>> Would you like to insert a SD Card? (Y or N): ");
			choice = cin.nextLine();

			if (choice.toUpperCase().equals("Y")) {
				return insertSDCard();
			}

			return false;
		}
	}

	private void installDefaultApps() {
		// Installing Google
		appsInstalled.put("Google", 200.00);
		freeMemory -= appsInstalled.get("Google") / 1000;

		// Installing Safari
		appsInstalled.put("Google Chrome", 122.00);
		freeMemory -= appsInstalled.get("Google Chrome") / 1000;

		// Installing YouTube
		appsInstalled.put("YouTube", 50.00);
		freeMemory -= appsInstalled.get("YouTube") / 1000;

		// Installing iTunes
		appsInstalled.put("Google Play Store", 98.00);
		freeMemory -= appsInstalled.get("Google Play Store") / 1000;

	}

	private void openDefaultApps() {
		if (isAppInstalled("Google"))
			openApp("Google");

		if (isAppInstalled("Google Play Store"))
			openApp("Google Play Store");
	}

	public String toString() {
		String output = super.toString() + "\n>> SD CARD INSERTED = "
				+ (externalSDCard ? "YES" : "NO");

		if (externalSDCard) {
			output += "\n>> SIZE OF SD CARD: " + sizeSDCard + "GB";
		}

		return output;
	}

	@Override
	public boolean increaseStorage() {
		return insertSDCard();
	}

	@Override
	public boolean removeExtraStorage() {
		return removeSDCard();
	}

	@Override
	public boolean changeExtraStorage() {
		return changeSDCard();
	}
}
