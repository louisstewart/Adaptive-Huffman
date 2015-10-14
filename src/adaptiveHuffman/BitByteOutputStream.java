package adaptiveHuffman;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Write individual bits to an output stream, or write whole bytes.
 * 
 * Bits are stored in a buffer until the buffer contains 8 bits, then
 * it is written to the output stream.
 * 
 * Class was modified to suit the style of BitInputStream.
 * 
 * @author 1300002022
 *
 */
public class BitByteOutputStream {
	
	// Buffer of bits to write to output.
	private int bitBuffer = 0;
    private int bitsLeftInBuffer = 0;
    
    // Stream to write bits to.
    private OutputStream out;
    
    public BitByteOutputStream(OutputStream out) {
    	if(out == null) {
    		throw new NullPointerException("Output stream is invalid.");
    	}
    	this.out = out;
    }
	
	/**
     * Write a bit into the buffer, unless buffer is full.
     * If full then flush the buffer.
     */
    public void writeBit(boolean bit) {
        // Add bit to buffer.
        bitBuffer <<= 1;
        if (bit) bitBuffer |= 1;
        
        // Keep track of how many bits in buffer.
        bitsLeftInBuffer++;
        
        // If buffer full, flush.
        if (bitsLeftInBuffer == 8) flush();
    } 
    
    /**
     * Write the value x to output stream. 
     * 
     * If buffer is empty, then just write x straight
     * to the output. Otherwise, write the contents of
     * buffer to output as well as the first n bits of x
     * then fill buffer with remaining bits of x.
     * 
     * @param x - byte to write to output.
     */
    public void writeByte(int x) {
        if( x <= 0 && x > 256) {
        	throw new IllegalArgumentException("Value not in range.");
        }
        
        if (bitsLeftInBuffer == 0) {
        	// If buffer empty just write x to out.
            try {
                out.write(x);
            }
            catch (IOException e) {
            	System.err.println("Write Error");
                e.printStackTrace();
            }
            return;
        }

        for (int i = 0; i < 8; i++) {
            boolean bit = ((x >>> (8 - i - 1)) & 1) == 1;
            writeBit(bit);
        }
    }
    
    /**
     * Write the buffer to the output stream.
     * 
     * If buffer is not full (8 bits) then fill it in \
     * with trailing 0s and write to output.
     * 
     * Then clear buffer and reset.
     */
    public void flush() {
        if (bitsLeftInBuffer == 0) {
        	return;
        }
        if (bitsLeftInBuffer > 0) {
        	// Fill empty space in buffer with 0s.
        	bitBuffer <<= (8 - bitsLeftInBuffer); 
        }
        try {
            out.write(bitBuffer);
        }
        catch (IOException e) {
        	System.err.println("Write Error");
            e.printStackTrace();
        }
        // Reset buffer.
        bitsLeftInBuffer = 0;
        bitBuffer = 0;
    }
    
    public void close() {
    	try {
    		out.close();
    	}
    	catch(IOException e) {
    		System.err.println("Error closing output stream.");
    		e.printStackTrace();
    	}
    }

}
