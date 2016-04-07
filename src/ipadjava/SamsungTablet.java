package ipadjava;

public class SamsungTablet extends Tablet {
	
    SamsungTablet() {}
    SamsungTablet(int storage) {}
    SamsungTablet(SamsungTablet tablet) {}
    
    public void turnOn() {}
    public boolean insertSDCard() {return true;}
    public boolean removeSDCard() {return true;}
    public boolean changeSDCard() {return true;}
    
    private void installDefaultApps() {}
    private void openDefaultApps() {}
    
    private boolean externalSDCard;
    private int sizeSDCard;
}
