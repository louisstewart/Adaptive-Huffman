package adaptiveHuffman.tree;

public class LeafNode extends Node {
	private char value;

	public LeafNode(Node parent,char value, int weight, int index) {
		super(parent,weight,index);
		this.value = value;
	}
	
	/*
	 * Getters and setters for private members.
	 */
	public void setValue(char value) {
		this.value = value;
	}
	
	public char getValue() {
		return this.value;
	}	
	
}
