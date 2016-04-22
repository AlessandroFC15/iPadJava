package ipadjava;

public interface Unlockable {
	
	public boolean unlockScreen();
	
	public boolean unlockScreen(String password);
	
	public boolean lockScreen();
	
	public boolean isScreenUnlocked();
	
	// Overloading methods
	
	public void setLockScreenPassword();
	
	public void setLockScreenPassword(final String password);

}

