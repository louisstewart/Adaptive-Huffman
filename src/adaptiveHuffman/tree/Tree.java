package adaptiveHuffman.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Tree {
	
	public Node root;
	public Node NYT; // Current NYT node.
	
	// Easily access a node based on its value.
	private Map<Integer, Node> seen = new HashMap<Integer,Node>();
	// Keep nodes in order based on weight.
	private List<Node> order = new ArrayList<Node>();
	
	/**
	 * Make a new tree with root == NYT node.
	 * Add NYT node to order list at index 0.
	 */
	public Tree() {
		this.root = new Node(null);
		this.NYT = root;
		order.add(root);
	}
	
	/**
	 * Insert a value into the tree.
	 * If value already exists in tree then update node weight
	 * and rearrange tree if necessary.
	 * 
	 * @param value - value to insert into tree
	 */
	public void insertInto(Integer value) {
		// Deal with value that exists in tree first.
		if(seen.containsKey(value)) {
			Node toUpdate = seen.get(value);
			updateTree(toUpdate);
		}
		else {
			Node parent = giveBirth(value);
			updateTree(parent);
		}
	}
	
	/**
	 * Check if character exists in tree already.
	 * 
	 * @param value - char to check.
	 * @return true if exists in tree.
	 */
	public boolean contains(Integer value) {
		if(seen.containsKey(value)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Given a value, find its code by traversing the tree. 
	 * Moving left = 0 bit, moving right = 1 bit
	 * 
	 * Bits are stored as booleans in a list in reverse order 
	 * because tree is traversed from leaf to root.
	 * 
	 * @param c - value to find in tree.
	 * @param seen - flag to say if c exists in tree or not
	 * @param buffer - list of bools representing bits
	 * @return - number of bits in the code.
	 */
	public int getCode(Integer c, boolean seen, ArrayList<Boolean> buffer) {
		int length = 0; 
		if(!seen) { // Return NYT code
			if(NYT == root) {
				return length; // Nothing in tree;
			}
			else {
				length = generateCode(NYT,buffer);
			}
		}
		else {
			length = generateCode(this.seen.get(c),buffer);
		}
		return length;
	}
	
	public boolean isEmpty() {
		return root == NYT;
	}
	
	/**
	 * Print the nodes of the tree using either pre-order
	 * or reverse breadth first (right child first) traversal 
	 * depending on which print function is used.
	 * 
	 * @param breadthFirst - flag to choose which print function.
	 */
	public void printTree(boolean breadthFirst) {
		if(breadthFirst) {
			printTreeBreadth(root);
		}
		else {
			printTreeDepth(root);
		}
	}
	
	/**
	 * Take current NYT node and replace it in the tree with an internal node.
	 * The internal node has an NYT node as left child, and a new leaf as right child.
	 * Weight of new internal node is weight of leaf child + NYT (which is 0).
	 */
	private Node giveBirth(int value) {
		Node newNYT = new Node(NYT);
		Node leaf = new Node(NYT, value);
		seen.put(value, leaf); // Add new value to seen.
		order.add(0,leaf);
		order.add(0,newNYT);
		
		Node oldNYT = NYT;
		NYT.isNYT = false; // Update the current NYT so that it is now internal node.
		NYT.left = newNYT; // Set children.
		NYT.right = leaf;
		NYT = newNYT; // Turn NYT pointer into the new NYT node.
		
		updateNodeIndices();
		return oldNYT;
	}
	
	/**
	 * Update the tree nodes to preserve the invariants that
	 * sibling nodes have adjacent indices, and that parents 
	 * have indices equal to the sum of child weights.
	 * 
	 * @param node
	 */
	private void updateTree(Node node) {
		while(node != root) {
			if(maxInWeightClass(node))  {
				Node toSwap = findHighestIndexWeight(node);
				swap(toSwap,node);
				
			}
			node.increment(); // Increment node weight.
			node = node.parent;
		}
		node.increment();
		node.setIndex(order.indexOf(node));
	}
	
	/**
	 * Check if a node is the highest indexed node
	 * for its weight value.
	 * 
	 * @param node
	 * @return
	 */
	private boolean maxInWeightClass(Node node) {
		int index = order.indexOf(node);
		int weight = node.getWeight();
		for(int i = index+1; i < order.size(); i++) {
			Node next = order.get(i);
			if(next != node.parent && next.getWeight() == weight) {
				return true;
			}
			else if(next != node.parent && next.getWeight() > weight){
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Find the node with the highest index that is the
	 * same weight as the argument node.
	 * 
	 * @param node
	 * @return
	 */
	private Node findHighestIndexWeight(Node node) {
		Node next;
		int index = node.getIndex() + 1;
		int weight = node.getWeight();
		while((next = order.get(index)).getWeight() == weight) {
			index++;
		}
		next = order.get(--index); // Overshot correct index, need to decrement.
		return next;
		
	}
	
	/**
	 * Swap 2 nodes in a tree, preserving the indices of 
	 * the positions, and the parent nodes.
	 * 
	 * @param newNodePosition
	 * @param oldNodeGettingSwapped - node which needs to
	 * 		change position due to weight increment.
	 */
	private void swap(Node newNodePosition, Node oldNodeGettingSwapped) {
		int newIndex = newNodePosition.getIndex();
		int oldIndex = oldNodeGettingSwapped.getIndex();
		
		// Keep track of parents of both nodes getting swapped.
		Node oldParent = oldNodeGettingSwapped.parent;
		Node newParent = newNodePosition.parent;
		
		// Need to know if nodes were left or right child.
		boolean oldNodeWasOnRight, newNodePositionOnRight;
		oldNodeWasOnRight = newNodePositionOnRight = false;
		
		if(newNodePosition.parent.right == newNodePosition) {
			newNodePositionOnRight = true;
		}
		if(oldNodeGettingSwapped.parent.right == oldNodeGettingSwapped) {
			oldNodeWasOnRight = true;
		}
		if(newNodePositionOnRight) {
			newParent.right = oldNodeGettingSwapped;
		}
		else{
			newParent.left = oldNodeGettingSwapped;
		}
		if(oldNodeWasOnRight) {
			oldParent.right = newNodePosition;
		}
		else {
			oldParent.left = newNodePosition;
		}
		// Update the parent pointers. 
		oldNodeGettingSwapped.parent = newParent;
		newNodePosition.parent = oldParent;
		// Swap the indices of the nodes in order arraylist.
		order.set(newIndex, oldNodeGettingSwapped);
		order.set(oldIndex, newNodePosition);
		updateNodeIndices();
	}
	
	/**
	 * Correct the index value of a node after
	 * inserting new nodes into the order list.
	 */
	private void updateNodeIndices() {
		for(int i = 0; i < order.size(); i++) {
			Node node = order.get(i);
			node.setIndex(order.indexOf(node));
		}
	}
	
	/** 
	 * Generate in reverse order a list of booleans that represent 
	 * the bits for the code of a value in the tree.
	 * 
	 * List generated in reverse order because we traverse the tree
	 * from node up to root.
	 * 
	 * @param in - Node to start from (leaf or nyt)
	 * @param buffer - list of bools representing bits
	 * @return number of bits in code == length of list.
	 */
	private int generateCode(Node in, ArrayList<Boolean> buffer) {
		Node node = in;
		Node parent;
		int length = 0;
		while(node.parent != null) {
			parent = node.parent;
			if(parent.left == node) {
				buffer.add(false);
				length++;
			}
			else {
				buffer.add(true);
				length++;
			}
			node = parent;
		}
		return length;
	}
	
	/**
	 * Pre-order depth first print of tree nodes.
	 * 
	 * @param node 
	 */
	private void printTreeDepth(Node node){
		if(node.isNYT) {
			System.out.println(node);
		} 
		else if(node.isLeaf) {
			System.out.println(node);
		}
		else { // Node is an internal node. 
			System.out.println(node);
			printTreeDepth(node.left);
			printTreeDepth(node.right);
		}
	}
	
	/**
	 * Breadth first printing of tree.
	 * 
	 * Goes to right child of node first before left,
	 * so that nodes are printed in decreasing indices.
	 * 
	 * @param root
	 */
	private void printTreeBreadth(Node root) {
		Queue<Node> queue = new LinkedList<Node>() ;
	    if (root == null)
	        return;
	    queue.clear();
	    queue.add(root);
	    while(!queue.isEmpty()){
	        Node node = queue.remove();
	        System.out.println(node);
	        if(node.right != null) queue.add(node.right);
	        if(node.left != null) queue.add(node.left);
	    }

	}
	
}
