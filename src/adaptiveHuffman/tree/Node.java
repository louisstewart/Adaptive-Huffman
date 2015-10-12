package adaptiveHuffman.tree;

public class Node {
	
	protected Node parent = null;
	protected Node left = null;
	protected Node right = null;
	protected boolean isNYT = false;
	protected boolean isLeaf = false;
	
	private int weight;
	private int index;
	private char value;
	
	public Node(Node parent, Node left, Node right, int weight, int index) {
		this.parent = parent;
		this.weight = weight;
		this.index = index;
	}
	
	/**
	 * NYT Node constructor.
	 * Only needs to know parent node, as NYT
	 * has no children and is always weight 0 
	 * and index 0.
	 * 
	 * @param parent
	 */
	public Node(Node parent) {
		this.parent = parent;
		this.weight = 0;
		this.index = 0;
		this.isNYT = true;
	}
	
	/**
	 * Leaf Node constructor. 
	 * New leaf is always index 1 and weight 1.
	 * 
	 * @param parent
	 * @param value
	 */
	public Node(Node parent, char value) {
		this.parent = parent;
		this.weight = 1;
		this.index = 1;
		this.value = value;
		this.isLeaf = true;
		this.isNYT = false;
	}
	
	public String toString() {
		if(this.isLeaf) {
			return "I am leaf with value: "+this.value+" weight: "+this.weight+" index: "+this.index;
		}
		else if(this.isNYT) {
			return "I am nyt"+" weight: "+this.weight+" index: "+this.index;
		}
		else {
			return "I am internal node"+" weight: "+this.weight+" index: "+this.index;
		}
	}

	/*public void setParent(Node parent) {
		this.parent = parent;
	}
	
	public Node getParent() {
		return this.parent;
	}*/
	
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
	
	public void setIndex(int index) {
		this.index = index;
	}

}
