package adaptiveHuffman.tree;

import java.util.HashMap;
import java.util.Map;

public class Tree {
	
	private Node root;
	private Map seen = new HashMap<Character,Integer>(); 
	// Map of seen characters with their weight. Makes tree search easier.
	
	
	public Tree() {
		this.root = new NYTNode(0);
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
