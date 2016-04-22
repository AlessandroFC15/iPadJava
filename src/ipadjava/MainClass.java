package ipadjava;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class MainClass {
	
	public enum TypeOfTablet{
		IPAD("iPad"), SAMSUNG("Samsung Tablet");
		
		private String name;
		
		TypeOfTablet(String name)
		{
			this.name = name;
		}
		
		public String getName()
		{
			return name;
		}
	}
	
	public static void main(String[] args) {
		ArrayList<Tablet> tablets = new ArrayList<>();
		
		tablets.add(new IPad());
		
		// Método setLockScreenPassword será chamado nessa criação
		tablets.add(new SamsungTablet());
		
		for (Tablet tablet : tablets)
		{
			// Testando método sobrescrito em IPad
			tablet.unlockScreen();
			
			if (tablet instanceof IPad)
			{
				((IPad) tablet).setTouchID();
			} else if (tablet instanceof SamsungTablet)
			{
				((SamsungTablet) tablet).increaseStorage();
			}
		}
	}
	
	// Functions related to the menu
	
		private static void menu(Tablet tablet)
		{
		    TypeOfTablet typeOfObject;
		    
		    if (tablet instanceof IPad)
		    {
		        typeOfObject = TypeOfTablet.IPAD;
		    } else if (tablet instanceof SamsungTablet)
		    {
		        typeOfObject = TypeOfTablet.SAMSUNG;
		    } else
		    {
		        return;
		    }
		    
		    String choice;
		    int op;
		    
		    do {
		    	// The options will change accordingly to the type of object.
		        
		        System.out.print("\n\t\t|| " + typeOfObject.getName() + " Control Center ||\n"
		        + "\n1 => Show Open Apps"
		        + "\t\t2 => Install App"
		        + "\n\n3 => Uninstall App"
		        + "\t\t4 => Show Apps Installed"
		        + "\n\n5 => Get " + typeOfObject.getName() + " Specs"
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
		        + "\t\t\t18 => " + (typeOfObject == TypeOfTablet.IPAD? "Get Number of iPads Created" : "Insert SD Card")
		        + "\n\n19 => " + (typeOfObject == TypeOfTablet.IPAD? "Update iOS Version" : "Remove SD Card")
		        + (typeOfObject == TypeOfTablet.SAMSUNG? "\t\t20 => Change SD Card\n\n" : "\t")
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
		                    if (typeOfObject == TypeOfTablet.IPAD)
		                    {
		                        System.out.println(">> NUMBER OF IPADS = " + IPad.getNumberOfiPads());
		                    } else
		                    {
		                        ((SamsungTablet) tablet).increaseStorage();
		                    }
		                    
		                    break;
		                case 19:
		                    if (typeOfObject == TypeOfTablet.IPAD)
		                    {
		                        IPad.updateIOSVersion();
		                       System.out.println(">> IOS Version update for all iPads. ");
		                    } else
		                    {
		                        ((SamsungTablet) tablet).removeExtraStorage();
		                    }
		                    
		                    break;
		                case 20:
		                    if (typeOfObject == TypeOfTablet.IPAD)
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
					    String inputSizeOfApp = cin.nextLine();
					    
					    try {
					    	sizeOfApp = Float.parseFloat(inputSizeOfApp);
					    	
					    	if (sizeOfApp > 0)
					        {
					            break;
					        }
					        
					        System.out.println("\n# Invalid size. Try again.");
					    } catch (NumberFormatException e)
					    {
					    	System.out.println("\n# Enter a valid number #");
					    }
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
