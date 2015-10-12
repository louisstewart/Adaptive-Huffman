package adaptiveHuffman.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Tree {
	
	private Node root;
	private Node NYT; // Current NYT node.
	private Map<Character, Node> seen = new HashMap<Character,Node>();
	// Easily access a node based on its value.
	private List<Node> order = new ArrayList<Node>();
	// Keep nodes in order based on weight.
	
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
	public void insertInto(Character value) {
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
	private Node giveBirth(char value) {
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
	
	private void updateTree(Node node) {
		while(node != root) {
			if(maxInWeightClass(node))  {
				Node toSwap = findHighestIndexWeight(node);
				swap(toSwap,node);
				
			}
			node.increment();
			node = node.parent;
		}
		node.increment();
		node.setIndex(order.indexOf(node));
	}
	
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
	
	private Node findHighestIndexWeight(Node node) {
		Node next;
		int index = node.getIndex() + 1;
		int weight = node.getWeight();
		while((next = order.get(index)).getWeight() == weight) {
			index++;
		}
		next = order.get(--index);
		return next;
		
	}
	
	private void swap(Node newNodePosition, Node oldNodeGettingSwapped) {
		int newIndex = newNodePosition.getIndex();
		int oldIndex = oldNodeGettingSwapped.getIndex();
		Node oldParent = oldNodeGettingSwapped.parent;
		Node newParent = newNodePosition.parent;
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
		oldNodeGettingSwapped.parent = newParent;
		newNodePosition.parent = oldParent;
		// Swap the indices of the nodes in order arraylist.
		order.set(newIndex, oldNodeGettingSwapped);
		order.set(oldIndex, newNodePosition);
		updateNodeIndices();
	}
	
	private void updateNodeIndices() {
		for(int i = 0; i < order.size(); i++) {
			Node node = order.get(i);
			node.setIndex(order.indexOf(node));
		}
	}
	
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
