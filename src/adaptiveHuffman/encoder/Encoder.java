package adaptiveHuffman.encoder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import adaptiveHuffman.tree.*;

public class Encoder {
	
	public FileInputStream in = null;
	public FileOutputStream out = null;

    private int bitBuffer;     // 8-bit buffer of bits to write out
    private int n;          // number of bits remaining in buffer
    
    public static void main(String[] args) {
		Encoder enc = new Encoder("/Users/Louis/Desktop/in.txt","new2.txt");
		Tree tree = new Tree();
		enc.encode(tree);
	}
    
    public Encoder(String in, String out) {
    	try {
			this.in = new FileInputStream(in);
			this.out = new FileOutputStream(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	
    }

   /**
     * Write the specified bit to standard output.
     */
    private void writeBit(boolean bit) {
        // add bit to buffer
        bitBuffer <<= 1;
        if (bit) bitBuffer |= 1;

        // if buffer is full (8 bits), write out as a single byte
        n++;
        if (n == 8) clearBuffer();
    } 
    
    private void writeByte(int x) {
        assert x >= 0 && x < 256;

        // optimized if byte-aligned
        if (n == 0) {
            try {
                out.write(x);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        // otherwise write one bit at a time
        for (int i = 0; i < 8; i++) {
            boolean bit = ((x >>> (8 - i - 1)) & 1) == 1;
            writeBit(bit);
        }
    }
    
    private void clearBuffer() {
        if (n == 0) return;
        if (n > 0) bitBuffer <<= (8 - n);
        try {
            out.write(bitBuffer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        n = 0;
        bitBuffer = 0;
    }
	
	public void encode(Tree tree) {
		
		try {
			
			int c = 0;
			
			while((c = in.read()) != -1) {
				ArrayList<Boolean> buffer = new ArrayList<Boolean>();
				if (tree.contains(c)) {
					
					int len = tree.getCode(c,true,buffer);
					for(len=len-1 ;len>=0;len--){
						writeBit(buffer.get(len));
					}
					tree.insertInto((int)c);
				}
				else {
					int len = tree.getCode(c, false,buffer);
					for(len=len-1 ;len>=0;len--){
						writeBit(buffer.get(len));
					}
					writeByte(c);
					tree.insertInto(c);
				}
			}
			tree.printTree(true);
			clearBuffer();
		}
		catch (IOException e) {
			System.err.println("Error opening file for input");
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
	

}
