package adaptiveHuffman.tree;

public class Node {
	
	public Node parent = null;
	public Node left = null;
	public Node right = null;
	
	protected boolean isNYT = false;
	protected boolean isLeaf = false;
	
	private int weight;
	private int index;
	private int value;
	
	/**
	 * Internal node constructor
	 * @param parent
	 * @param left
	 * @param right
	 * @param weight
	 * @param index
	 */
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
	public Node(Node parent, int value) {
		this.parent = parent;
		this.weight = 1;
		this.index = 1;
		this.value = value;
		this.isLeaf = true;
		this.isNYT = false;
	}
	
	public boolean isLeaf() {
		return this.isLeaf;
	}
	
	public boolean isNYT() {
		return this.isNYT;
	}
	
	/**
	 * Return some sensible string representation of node
	 * - always returns index and weight and message about what 
	 *   sort of node it is
	 * - also returns value if is a leaf node.
	 */
	public String toString() {
		if(this.isLeaf) {
			return " index: "+this.index+" weight: "+this.weight+" value: "+this.value+" AM LEAF";
		}
		else if(this.isNYT) {
			return " index: "+this.index+" weight: "+this.weight+" AM NYT";
		}
		else {
			return " index: "+this.index+" weight: "+this.weight+" AM INTERNAL";
		}
	}
	
	/**************************************************************
	 * GETTERS AND SETTERS
	 **************************************************************/
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
	
	public int getValue() {
		return this.value;
	}

}
