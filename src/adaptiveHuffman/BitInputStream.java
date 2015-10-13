package adaptiveHuffman;

import java.io.IOException;
import java.io.InputStream;


/**
 * This class was taken from 
 * https://github.com/nayuki/Arithmetic-Coding/blob/master/src/nayuki/arithcode/BitInputStream.java
 * by user Nayuki.
 * 
 * Changes were made to remove unnecessary methods.
 * Reason for use was to avoid implementing my own single bit reading input stream.
 * While this would have been possible, this code was publicly available and correct
 * reference to the original author is being given here and in the report.
 * 
 */
public final class BitInputStream {
	
	// Underlying byte stream to read from.
	private InputStream input;
	
	// Either in the range 0x00 to 0xFF if bits are available, or is -1 if the end of stream is reached.
	private int nextBits;
	
	// Always between 0 and 7, inclusive.
	private int numBitsRemaining;
	
	private boolean isEndOfStream;
	
	
	
	// Creates a bit input stream based on the given byte input stream.
	public BitInputStream(InputStream in) {
		if (in == null)
			throw new NullPointerException("Argument is null");
		input = in;
		numBitsRemaining = 0;
		isEndOfStream = false;
	}
	
	
	
	// Reads a bit from the stream. Returns 0 or 1 if a bit is available, or -1 if the end of stream is reached. The end of stream always occurs on a byte boundary.
	public int read() throws IOException {
		if (isEndOfStream)
			return -1;
		if (numBitsRemaining == 0) {
			nextBits = input.read();
			if (nextBits == -1) {
				isEndOfStream = true;
				return -1;
			}
			numBitsRemaining = 8;
		}
		numBitsRemaining--;
		return (nextBits >>> numBitsRemaining) & 1;
	}
	
	// Closes this stream and the underlying InputStream.
	public void close() throws IOException {
		input.close();
	}
	
}