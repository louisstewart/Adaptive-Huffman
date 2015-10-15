# Adaptive-Huffman
Implementation of Adaptive Huffman Coding in Java using Vitter’s algorithm.

## How To Run Encoder

Compile using javac

	$ java adaptiveHuffman.encoder.Encoder InputFile OutputFile`

Where `InputFile` is some text or otherwise file to compress, and `OutputFile` is the location to write the compressed code to.

## How To Run Decoder

	$ java adaptiveHuffman.decoder.Decoder InputFile OutputFile

Where `InputFile` is the compressed intermediate file, and `OutputFile` is location to write the uncompressed data to.