package com.tres.network.packet.compression;

public interface Compressor {

	boolean willCompress(byte[] data);

	byte[] compress(byte[] data) throws CompressException;

	byte[] decompress(byte[] data) throws CompressException;
}
