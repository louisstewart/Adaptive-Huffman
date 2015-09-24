package adaptiveHuffman.tree;

public abstract class Node {
	
	protected int weight;
	protected int index;
	
	public Node(int weight, int index) {
		this.weight = weight;
		this.index = index;
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
