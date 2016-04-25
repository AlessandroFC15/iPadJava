package ipadjava;

import java.util.HashMap;
import java.util.Scanner;

public class TouchID {
	// string nameOfUser => <int fingerUsed>
	private HashMap<String, Integer> registeredFingerprints;

	TouchID() {
		registeredFingerprints = new HashMap<>();
	}

	TouchID(final TouchID oldTouchID) {
		registeredFingerprints = new HashMap<>(
				oldTouchID.registeredFingerprints);
	}

	public boolean addFingerPrint() {
		String nameOfUser;
		int fingerUsed;

		System.out.println("\n.: ADD NEW FINGERPRINT :.\n");

		// 1st Step = Get the name of the user
		while (true) {
			System.out.print(">> Enter your name (Max 25 chars): ");

			nameOfUser = cin.nextLine();

			if (nameOfUser.length() > 0) {
				// Check to see if the user isn't already registered
				if (!checkForFingerPrint(nameOfUser)) {
					break;
				} else {
					System.out.println("# The user " + nameOfUser
							+ " is already registered #\n");
				}
			} else {
				System.out.println("# Enter a valid name #\n");
			}
		}

		// 2nd Step = Get the finger used by the user
		System.out.println("\n|| FINGERPRINT ||\n" + "\n| 1 - Left Thumb"
				+ "\t| 6 - Right Thumb" + "\n| 2 - Left Index"
				+ "\t| 7 - Right Index" + "\n| 3 - Left Middle"
				+ "\t| 8 - Right Middle" + "\n| 4 - Left Ring"
				+ "\t\t| 9 - Right Ring" + "\n| 5 - Left Pinky"
				+ "\t| 10 - Right Pinky");

		while (true) {
			System.out.print("\n>> Choose finger to be your TouchID: ");

			String inputFinger = cin.nextLine();

			try {
				fingerUsed = Integer.parseInt(inputFinger);

				if (fingerUsed >= 1 && fingerUsed <= 10) {
					break;
				}

				System.out.println("# Enter a valid number (1 to 10). #\n");

			} catch (NumberFormatException e) {
				System.out.println("# Enter a valid number (1 to 10). #\n");
			}
		}

		System.out.println("\n|| Fingerprint successfully registered ||\n");
		// 3st Step = Add data to the data structure
		registeredFingerprints.put(nameOfUser, fingerUsed);

		return true;
	}

	public boolean removeFingerPrint() {
		if (isAnyFingerPrintRegistered()) {
			showFingerPrintsRegistered();

			String nameOfUser;

			System.out.print("\n>> Enter the name to be removed: ");
			nameOfUser = cin.nextLine();

			if (checkForFingerPrint(nameOfUser)) {
				registeredFingerprints.remove(nameOfUser);
				System.out.println("|| The user " + nameOfUser
						+ " was successfully erased ||");
				return true;
			}

			System.out.println("# This name isn't registered #");
			return false;
		} else {
			System.out
					.println(">> There are no fingerprints to be removed <<\n");
			return false;
		}
	}

	public boolean removeAllFingerPrints() {
		return true;
	}

	public boolean unlockScreen() {
		String nameOfUser;
		int fingerUsed;

		System.out.print(">> To unlock the screen, please enter your name: ");
		nameOfUser = cin.nextLine();

		// Check to see if there is a user with this name in the database
		if (checkForFingerPrint(nameOfUser)) {
			while (true) {
				System.out.print("\n>> Enter the finger you registered: ");
				fingerUsed = cin.nextInt();

				if (fingerUsed >= 1 && fingerUsed <= 10) {
					break;
				}

				System.out.println("# Enter a valid number (1 to 10). #\n");
			}

			if (fingerUsed == registeredFingerprints.get(nameOfUser)) {
				System.out.println("\n|| THE SCREEN IS NOW UNLOCKED ||\n");
				return true;
			} else {
				System.out
						.println("\n# Sorry! We couldn't match your fingerprint! #");
				return false;
			}
		} else {
			System.out
					.println("# There are no fingerprints registered in your name #\n");
			return false;
		}
	}

	private void showFingerPrintsRegistered() {
		// Check to see if the iPad isn't empty.
		if (isAnyFingerPrintRegistered()) {
			System.out.println("\n.: FINGERPRINTS :.\n");

			// Iterate through the unordered map
			// app holds the pair <nameOfUser, chosenFinger>
			for (String username : registeredFingerprints.keySet()) {
				System.out.println("Name: "
						+ registeredFingerprints.get(username));
			}
		} else {
			System.out.println(">> There are no fingerprints registered <<\n");
		}
	}

	private boolean isAnyFingerPrintRegistered() {
		return !registeredFingerprints.isEmpty();
	}

	private boolean checkForFingerPrint(final String name) {
		return registeredFingerprints.containsKey(name);
	}

	static Scanner cin = new Scanner(System.in);
}
