package com.tres.network.packet.cipher;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class CommonKeyNetworkCipher extends NetworkCipher {

	protected Cipher encryptCipher;

	protected Cipher decryptCipher;

	protected SecretKey key;

	protected IvParameterSpec vector;

	protected SecureRandom random;

	public CommonKeyNetworkCipher(SecretKey key) {
		this.key = key;

		try {
			this.encryptCipher = Cipher.getInstance(this.getTransformation());
			this.decryptCipher = Cipher.getInstance(this.getTransformation());
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			throw new RuntimeException(e);
		}

		this.random = new SecureRandom();

		this.randomizeVector();

		try {
			this.encryptCipher.init(Cipher.ENCRYPT_MODE, this.key, this.vector);
			this.decryptCipher.init(Cipher.DECRYPT_MODE, this.key, this.vector);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
			throw new RuntimeException(e);
		}
	}

	private void randomizeVector() {
		byte[] iv = new byte[16];
		this.random.nextBytes(iv);

		this.vector = new IvParameterSpec(iv);
	}

	@Override
	protected String getTransformation() {
		return "AES/CBC/PKCS5Padding";
	}

	@Override
	public byte[] encrypt(byte[] data) throws CryptoException {
		try {
			return this.encryptCipher.doFinal(data);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			throw new CryptoException(e);
		}
	}

	@Override
	public byte[] decrypt(byte[] data) throws CryptoException {
		try {
			return this.decryptCipher.doFinal(data);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			throw new CryptoException(e);
		}
	}
}
