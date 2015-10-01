package adaptiveHuffman.tree;

import java.util.HashMap;
import java.util.Map;

public class Tree {
	
	private Node root;
	priavte Node NYT;
	private Map seen = new HashMap<Character,Integer>(); 
	// Map of seen characters with their weight. Makes tree search easier.
	
	
	public Tree() {
		this.root = new NYTNode();
		this.NYT = root;
	}
	
	/**
	 * Take current NYT node and replace it in the tree with an internal node.
	 * The internal node has an NYT node as left child, and a new leaf as right child.
	 * Weight of new internal node is weight of leaf child + NYT (which is 0).
	 */
	private void giveBirth(Node currentNYT, char value) {
		Node child = new LeafNode(value,1,1);
		Node newNYT = new NYTNode();
		Node internal = new InternalNode(newNYT,child,1,2);
		currentNYT = internal;
		
	}
	
	/**
	 * Insert a value into the tree.
	 * If value already exists in tree then update node weight
	 * and rearrange tree if necessary.
	 * 
	 * @param value - value to insert into tree
	 */
	public void insertInto(Character value) {
		// Deal with value that exists in tree first.
		if(seen.containsKey(value)) {
			
			int weight = (int) seen.get(value); // Get the current weight.
			Node node = root;
			
			if(!(node instanceof NYTNode) && !(node instanceof InternalNode) 
					&& node.getWeight() == weight && ((LeafNode)node).getValue() == value) {
				// Node is root.
				node.increment();
				updateTree();
			}
			else if(node.getWeight() < weight) {
				
			}
			else {
				
			}
		}
	}
	
	private void updateTree() {}

}
