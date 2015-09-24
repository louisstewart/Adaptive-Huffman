package adaptiveHuffman.tree;

public class LeafNode extends Node {
	private char value;

	public LeafNode(char value, int weight, int index) {
		super(weight,index);
		this.value = value;
	}
	
	/*
	 * Getters and setters for private members.
	 * No setter for index, because once the node index is set 
	 * on construction, it is not changed. Instead the weight and
	 * value are changed when contents need to be shifted around
	 * the tree.
	 */
	public void setValue(char value) {
		this.value = value;
	}
	
	public char getValue() {
		return this.value;
	}	
	
}
