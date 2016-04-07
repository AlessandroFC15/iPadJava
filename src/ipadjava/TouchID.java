package ipadjava;

import java.util.HashMap;

public class TouchID {
	TouchID() {}
    
    public boolean addFingerPrint() {return true;}
    public boolean removeFingerPrint() {return true;}
    public boolean removeAllFingerPrints() {return true;}
    public boolean unlockScreen() {return true;}
    
    private void showFingerPrintsRegistered() {}
    private boolean isAnyFingerPrintRegistered() {return true;}
    private boolean checkForFingerPrint(String name) {return true;}
    
    // unordered_map<string, pair <int, float >> registeredFingerprints;
    // string nameOfUser => pair <int fingerUsed, float timeToHold>
    private HashMap<String, Integer> registeredFingerprints; 
}
