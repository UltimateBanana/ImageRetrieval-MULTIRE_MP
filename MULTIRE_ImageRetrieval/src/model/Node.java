package model;

public class Node {

	int parent;
	int child;
	
	public Node() {
		// TODO Auto-generated constructor stub
	}
	
	public Node(int parent, int child) {
		setParent(parent);
		setChild(child);
	}
	
	public int getChild() {
		return child;
	}
	
	public int getParent() {
		return parent;
	}
	
	public void setChild(int child) {
		this.child = child;
	}
	
	public void setParent(int parent) {
		this.parent = parent;
	}
	
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if(this.parent == ((Node)o).getParent() && this.child == ((Node)o).getChild()){
			return true;
		}
		return false;
	}
}
