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

	@Test
	public void testInitialisation() {
		tree.printTree(true);
	}
	
	@Test
	public void testFirstInsert() {
		tree.insertInto(120);
		tree.printTree(false);
	}
	
	@Test
	public void testDoubleInsert() {
		tree.insertInto(101);
		tree.insertInto(123);
		tree.printTree(true);
	}
	
	@Test
	public void testInsertMultipleRepeatedCharacters() {
		tree.insertInto(123);
		tree.insertInto(101);
		tree.insertInto(123);
		tree.printTree(true);
	}
	
	@Test
	public void testInsertWithSwapsRequired() {
		tree.insertInto(103);
		tree.insertInto(102);
		tree.insertInto(104);
		tree.insertInto(103);
		tree.insertInto(102);
		tree.printTree(true);
	}
	
	@Test
	public void testInsertAbracadabra() {
		tree.insertInto(97);
		tree.insertInto(98);
		tree.insertInto(114);
		tree.insertInto(97);
		tree.insertInto(99);
		tree.insertInto(97);
		tree.insertInto(100);
		tree.insertInto(91);
		tree.insertInto(98);
		tree.insertInto(114);
		tree.insertInto(97);
		tree.printTree(true);
	}
	
}
