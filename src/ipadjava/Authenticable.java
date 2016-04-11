package ipadjava;

public interface Authenticable {
	
	public boolean unlockScreen();
	
	public boolean lockScreen();
	
	public boolean isScreenUnlocked();
	
	// Overloading methods
	
	public void setLockScreenPassword();
	
	public void setLockScreenPassword(String password);

}
