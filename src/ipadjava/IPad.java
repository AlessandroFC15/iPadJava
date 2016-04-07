package ipadjava;

public class IPad extends Tablet {
	public static void main(String[] args) {
		System.out.println("Hello World!");
	}
	
	IPad() {}
	
	IPad(int storage) {}
	
	IPad(IPad iPad) {}
	
	public void turnOn() {}
	
	public boolean unlockScreen() {return true;}
	
    public static void updateIOSVersion() {}
    
    public static int getNumberOfiPads() {return 0;}
    
    // HELPER FUNCTIONS
    private void setInitialSecuritySystem() {}
    private void setTouchID() {}
    private  boolean unlockPassword() {return true;}
    private boolean unlockTouchID() {return true;}
    private void installDefaultApps() {} 
    private void openDefaultApps() {}
    
    // ATTRIBUTES
    private int typeOfLockScreen;
    private TouchID touchID;
    
     /* STATIC VARIABLES */
    private static float latestIOSVersion;
    private static int numberOfiPads;
    
    /* CONSTANTS */
    private final static int TOUCH_ID = 1;
    private final static int PASSWORD = 2;
}
