package adaptiveHuffman.tree;

public abstract class Node {
	
	protected Node parent;
	protected int weight;
	protected int index;
	
	public Node(Node parent, int weight, int index) {
		this.parent = parent;
		this.weight = weight;
		this.index = index;
	}
	
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	public Node getParent() {
		return this.parent;
	}
	
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public int getWeight() {
		return this.weight;
	}
	
	public void increment() {
		this.weight++;
	}
	
	public int getIndex() {
		return this.index;
	}

}
