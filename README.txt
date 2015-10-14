# Adaptive-Huffman

After compilation, run the encoder as follows:

java adaptiveHuffman.encoder.Encoder InputFile OutputFile

where InputFile is some text or otherwise file to compress, and OutputFile is the location to write the compressed code to.

To run decoder:

java adaptiveHuffman.decoder.Decoder InputFile OutputFile

where InputFile is the compressed intermediate file, and OutputFile is location to write the uncompressed data to.