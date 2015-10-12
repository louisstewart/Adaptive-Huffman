package adaptiveHuffman.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			
		}
		else {
			Node parent = this.giveBirth(value);
			updateTree(parent);
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
		return oldNYT;
	}
	
	private void updateTree(Node node) {
		while(node != root) {
			if(maxInWeightClass(node))  {
				Node toSwap = findHighestIndexWeight(node);
				swap(toSwap,node);
				node.increment();
				node = node.parent;
			}
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
		next = order.get(index);
		return next;
		
	}
	
	private void swap(Node newNodePosition, Node oldNodeGettingSwapped) {
		int newIndex = newNodePosition.getIndex();
		int oldIndex = oldNodeGettingSwapped.getIndex();
		if(newNodePosition.parent.right == newNodePosition) {
			newNodePosition.parent.right = oldNodeGettingSwapped;
		}
		else {
			newNodePosition.parent.left = oldNodeGettingSwapped;
		}
		if(oldNodeGettingSwapped.parent.right == oldNodeGettingSwapped) {
			oldNodeGettingSwapped.parent.right = newNodePosition;
		}
		else {
			oldNodeGettingSwapped.parent.left = newNodePosition;
		}
		order.set(newIndex, oldNodeGettingSwapped);
		order.set(oldIndex, newNodePosition);
	}
	
	public void printTree() {
		printTheTree(this.root);
	}
	
	private void printTheTree(Node node){
		if(node.isNYT) {
			System.out.println(node);
		} 
		else if(node.isLeaf) {
			System.out.println(node);
		}
		else { // Node is an internal node. 
			System.out.println(node);
			printTheTree(node.left);
			printTheTree(node.right);
		}
	}

}
