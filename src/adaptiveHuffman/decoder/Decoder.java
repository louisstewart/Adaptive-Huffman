package adaptiveHuffman.decoder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import adaptiveHuffman.BitInputStream;
import adaptiveHuffman.tree.*;

public class Decoder {
	
	private BitInputStream in = null;
	private FileOutputStream out = null;

    //private int bitBuffer;     // 8-bit buffer of bits to write out
    //private int n;          // number of bits remaining in buffer
    
    public static void main(String[] args) {
		Decoder dec = new Decoder("hotb_intermediate.txt","hotb_output.txt");
		Tree tree = new Tree();
		dec.decode("intermediate.txt", tree);
	}
    
    public Decoder(String in, String out) {
    	try {
			this.in = new BitInputStream(new FileInputStream(in));
			this.out = new FileOutputStream(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	
    }

    /*private void fillBuffer() {
        try {
            bitBuffer = in.read();
            n = 8;
        }
        catch (IOException e) {
            System.out.println("EOF");
            bitBuffer = -1;
            n = -1;
        }
    }

   /**
     * Close this input stream and release any associated system resources.
     *
    public void close() {
        try {
            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not close BinaryStdIn");
        }
    }

   /**
     * Returns true if standard input is empty.
     * @return true if and only if standard input is empty
     *
    public boolean isEmpty() {
        return bitBuffer == -1;
    }

   /**
     * Reads the next bit of data from standard input and return as a boolean.
     *
     * @return the next bit of data from standard input as a <tt>boolean</tt>
     * @throws RuntimeException if standard input is empty
     *
    public boolean readBoolean() {
        if (isEmpty()) throw new RuntimeException("Reading from empty input stream");
        n--;
        boolean bit = ((bitBuffer >> n) & 1) == 1;
        if (n == 0) fillBuffer();
        return bit;
    }*/

	
	public void decode(String filename, Tree tree) {
		try {
			
			int c = 0;
			
			if(tree.isEmpty()) { // Just write out first byte.
				int bitBuffer = 0;
				for(int i = 0; i<8;i++) {
					c = in.read();
					bitBuffer |= c;
					if(i!=7) bitBuffer <<= 1;
				}
				out.write(bitBuffer);
				tree.insertInto(bitBuffer);
			}
			Node node = tree.root;
			while((c = in.read()) != -1) {
				if(c == 1) node = node.right;
				if(c == 0) node = node.left;
				
				int value = 0;
				if(node.isNYT()) {
					value = readByte(in); 
					out.write(value);
					tree.insertInto(value);
					node = tree.root;
				}
				if(node.isLeaf()) {
					value = node.getValue();
					out.write(value);
					tree.insertInto(value);
					node = tree.root;
				}	
			}
			//tree.printTree(true);
			
		}
		catch (IOException e) {
			System.err.println("Error reading bytes");
			e.printStackTrace();
		}
		finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private int readByte(BitInputStream in) throws IOException {
		int bitBuffer = 0;
		int c;
		for(int i = 0; i<8;i++) {
			c = in.read();
			bitBuffer |= c;
			if(i!=7) bitBuffer <<= 1;
			
		}
		return bitBuffer;
	}

}
