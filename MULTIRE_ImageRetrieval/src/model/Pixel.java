package model;

public class Pixel {
	
	int value;
	int label;
	
	public Pixel() {
		
	}
	
	public Pixel(int value, int label) {
		this.label = label;
		this.value = value;
	}
	
	public int getLabel() {
		return label;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setLabel(int label) {
		this.label = label;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this.label == ((Pixel)o).label){
			return true;
		}
		return false;
	}
}
