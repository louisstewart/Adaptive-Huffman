package adaptiveHuffman.decoder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import adaptiveHuffman.tree.*;

public class Decoder {
	
	private FileInputStream in = null;
	private FileOutputStream out = null;

    private int bitBuffer;     // 8-bit buffer of bits to write out
    private int n;          // number of bits remaining in buffer
    
    public static void main(String[] args) {
		Decoder dec = new Decoder("intermediate.txt","output.txt");
		Tree tree = new Tree();
		dec.decode("intermediate.txt", tree);
	}
    
    public Decoder(String in, String out) {
    	try {
			this.in = new FileInputStream(in);
			this.out = new FileOutputStream(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	
    }

    private void fillBuffer() {
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
     */
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
     */
    public boolean isEmpty() {
        return bitBuffer == -1;
    }

   /**
     * Reads the next bit of data from standard input and return as a boolean.
     *
     * @return the next bit of data from standard input as a <tt>boolean</tt>
     * @throws RuntimeException if standard input is empty
     */
    public boolean readBoolean() {
        if (isEmpty()) throw new RuntimeException("Reading from empty input stream");
        n--;
        boolean bit = ((bitBuffer >> n) & 1) == 1;
        if (n == 0) fillBuffer();
        return bit;
    }

   /**
     * Reads the next 8 bits from standard input and return as an 8-bit char.
     * Note that <tt>char</tt> is a 16-bit type;
     * to read the next 16 bits as a char, use <tt>readChar(16)</tt>.
     *
     * @return the next 8 bits of data from standard input as a <tt>char</tt>
     * @throws RuntimeException if there are fewer than 8 bits available on standard input
     */
    public char readChar() {
        if (isEmpty()) throw new RuntimeException("Reading from empty input stream");

        // special case when aligned byte
        if (n == 8) {
            int x = bitBuffer;
            fillBuffer();
            return (char) (x & 0xff);
        }

        // combine last n bits of current buffer with first 8-n bits of new buffer
        int x = bitBuffer;
        x <<= (8 - n);
        int oldN = n;
        fillBuffer();
        if (isEmpty()) throw new RuntimeException("Reading from empty input stream");
        n = oldN;
        x |= (bitBuffer >>> n);
        return (char) (x & 0xff);
        // the above code doesn't quite work for the last character if n = 8
        // because buffer will be -1, so there is a special case for aligned byte
    }

	
	public void decode(String filename, Tree tree) {
		
		try {
			
			//out = new FileOutputStream("output");
			
			while((bitBuffer = in.read()) != -1) {
				if(tree.isEmpty()) {
					out.write(bitBuffer);
					tree.insertInto(bitBuffer);
				}
				else {
					bitBuffer = in.read();
				}
			}
			tree.printTree(true);
			
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
