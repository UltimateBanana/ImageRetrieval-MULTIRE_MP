package model;

public class Pair {
	
	int coherent;
	int noncoherent;
	
	public Pair() {
		
	}
	
	public Pair(int coherent, int noncoherent) {
		this.coherent = coherent;
		this.noncoherent = noncoherent;
	}
	
	public int getCoherent() {
		return coherent;
	}
	
	public int getNoncoherent() {
		return noncoherent;
	}
	
	public void setCoherent(int coherent) {
		this.coherent = coherent;
	}
	
	public void setNoncoherent(int noncoherent) {
		this.noncoherent = noncoherent;
	}
}
