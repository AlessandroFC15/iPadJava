package ipadjava;

public interface Expandable {
	
	public boolean increaseStorage();
	
	public boolean increaseStorage(int size);
	
	public boolean removeExtraStorage();
	
	public boolean changeExtraStorage();
}
