package com.tres.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestUtils {

	private static String digest(byte[] target, String algorithm) {
		MessageDigest digest;

		try {
			digest = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

		byte[] result = digest.digest(target);

		return String.format("%040x", new BigInteger(1, result));
	}


	public static String sha256(byte[] target) {
		return digest(target, "SHA-256");
	}
}
