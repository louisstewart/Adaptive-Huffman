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
	*/
	@Test
	public void testFirstInsert() {
		tree.insertInto('a');
		tree.printTree();
	}

}
