package adaptiveHuffman.encoder;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import adaptiveHuffman.tree.*;

public class Encoder {

	public static void main(String[] args) {
		Encoder enc = new Encoder();
		enc.encode(args[0]);
	}
	
	public void encode(String filename) {
		FileInputStream in = null;
		FileOutputStream out = null;
		
		try {
			in = new FileInputStream(filename);
			out = new FileOutputStream("output.txt");
			int c;
			
			while((c = in.read()) != -1) {
				System.out.println(c);
			}
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
