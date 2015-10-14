package adaptiveHuffman.decoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import adaptiveHuffman.BitInputStream;
import adaptiveHuffman.tree.*;

public class Decoder {
	
	private BitInputStream in = null;
	private FileOutputStream out = null;
	
    public static void main(String[] args) {
    	if(args.length < 2) {
    		System.err.println("Usage: adaptiveHuffman.decoder inputFile outputFile");
    	}
    	else {
    		Decoder dec = new Decoder(args[0],args[1]);
    		Tree tree = new Tree();
    		File in = new File(args[0]);
    		dec.decode(tree);
    		File out = new File(args[1]);
    		System.out.println("Finished decompression of: "+in.getName());
    		System.out.println("Original size: "+in.length()+" bytes");
    		System.out.println("Uncompressed size: "+out.length()+" bytes");
    		System.out.println("Compression ratio: "+((float)out.length()/(float)in.length()));
    	}
    }
    
    public Decoder(String in, String out) {
    	try {
			this.in = new BitInputStream(new FileInputStream(in));
			this.out = new FileOutputStream(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	
    }
    
    
	public void decode(Tree tree) {
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
