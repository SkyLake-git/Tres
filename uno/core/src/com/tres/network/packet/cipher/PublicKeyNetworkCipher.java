package com.tres.network.packet.cipher;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

/**
 * Tres Application encrypting system.
 * using Public-key cryptography
 */
public class PublicKeyNetworkCipher extends NetworkCipher {

	protected Cipher encryptCipher;
	protected Cipher decryptCipher;

	protected KeyPair keyPair;

	public PublicKeyNetworkCipher(KeyPair keyPair) {
		try {
			this.encryptCipher = Cipher.getInstance(this.getTransformation());
			this.decryptCipher = Cipher.getInstance(this.getTransformation());
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			throw new RuntimeException(e);
		}

		this.keyPair = keyPair;

		try {
			this.encryptCipher.init(Cipher.ENCRYPT_MODE, this.keyPair.getPublic());
			this.decryptCipher.init(Cipher.DECRYPT_MODE, this.keyPair.getPrivate());
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected String getTransformation() {
		return "RSA/ECB/PKCS1Padding";
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
