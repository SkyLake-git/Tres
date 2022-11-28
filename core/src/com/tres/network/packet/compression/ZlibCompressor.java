package com.tres.network.packet.compression;

import java.util.Arrays;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ZlibCompressor implements Compressor {

	private static ZlibCompressor instance = null;

	public static ZlibCompressor getInstance() {
		if (instance == null) {
			instance = new ZlibCompressor();
		}

		return instance;
	}

	@Override
	public boolean willCompress(byte[] data) {
		return true;
	}

	@Override
	public byte[] compress(byte[] data) throws CompressException {
		byte[] result = new byte[2048];
		Deflater zip = new Deflater();
		zip.setInput(data);
		zip.finish();
		int length = zip.deflate(result);
		zip.end();
		return Arrays.copyOf(result, length);
	}

	@Override
	public byte[] decompress(byte[] data) throws CompressException {
		byte[] result = new byte[2048];

		Inflater zip = new Inflater();
		zip.setInput(data);
		int length = 0;
		try {
			length = zip.inflate(result);
		} catch (DataFormatException e) {
			throw new CompressException();
		}

		zip.end();

		return Arrays.copyOf(result, length);
	}
}
