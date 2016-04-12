package ipadjava;

public interface Lockable {
	
	public boolean unlockScreen();
	
	public boolean lockScreen();
	
	public boolean isScreenUnlocked();
	
	// Overloading methods
	
	public void setLockScreenPassword();
	
	public void setLockScreenPassword(String password);

}
