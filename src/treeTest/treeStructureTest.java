package treeTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import adaptiveHuffman.tree.Tree;

public class treeStructureTest {
	
	private Tree tree;

	@Before
	public void setUp() throws Exception {
		tree = new Tree();
	}

	/*@Test
	public void testInitialisation() {
		tree.printTree();
	}
	*
	@Test
	public void testFirstInsert() {
		tree.insertInto('a');
		tree.printTree();
	}
	*
	@Test
	public void testDoubleInsert() {
		tree.insertInto('a');
		tree.insertInto('b');
		tree.printTree(true);
	}
	*
	@Test
	public void testInsertMultipleRepeatedCharacters() {
		tree.insertInto('a');
		tree.insertInto('b');
		tree.insertInto('a');
		tree.printTree(true);
	}
	*
	@Test
	public void testInsertMultipleRepeatedCharacters() {
		tree.insertInto('a');
		tree.insertInto('b');
		tree.insertInto('a');
		tree.printTree(true);
	}
	*
	@Test
	public void testInsertWithSwapsRequired() {
		tree.insertInto('a');
		tree.insertInto('a');
		tree.insertInto('r');
		tree.insertInto('d');
		tree.insertInto('v');
		tree.printTree(true);
	}
	*/
	@Test
	public void testInsertAbracadabra() {
		tree.insertInto('a');
		tree.insertInto('b');
		tree.insertInto('r');
		tree.insertInto('a');
		tree.insertInto('c');
		tree.insertInto('a');
		tree.insertInto('d');
		tree.insertInto('a');
		tree.insertInto('b');
		tree.insertInto('r');
		tree.insertInto('a');
		tree.printTree(true);
	}
	
}
