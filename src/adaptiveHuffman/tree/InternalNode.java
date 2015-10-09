package adaptiveHuffman.tree;

public class InternalNode extends Node{
	
	public Node left;
	public Node right;
	
	public InternalNode(Node left, Node right, Node parent, int weight, int index) {
		super(parent,weight,index);
		this.left = left;
		this.right = right;
	}

}
