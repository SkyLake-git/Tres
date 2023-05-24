package com.tres.network.packet.cipher;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Tres Application encrypting system.
 * using Public-key cryptography
 */
public class PublicKeyNetworkCipher extends NetworkCipher {

	protected Cipher encryptCipher;

	protected Cipher decryptCipher;

	protected PublicKey publicKey;

	protected PrivateKey privateKey;

	public PublicKeyNetworkCipher(PublicKey publicKey, PrivateKey privateKey) {
		this.encryptCipher = null;
		this.decryptCipher = null;

		try {
			if (publicKey != null) this.encryptCipher = Cipher.getInstance(this.getTransformation());
			if (privateKey != null) this.decryptCipher = Cipher.getInstance(this.getTransformation());
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			throw new RuntimeException(e);
		}

		this.publicKey = publicKey;
		this.privateKey = privateKey;

		try {
			if (this.encryptCipher != null) this.encryptCipher.init(Cipher.ENCRYPT_MODE, this.publicKey);
			if (this.decryptCipher != null) this.decryptCipher.init(Cipher.DECRYPT_MODE, this.privateKey);
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
		if (this.encryptCipher == null) {
			throw new CryptoException("Encrypt not supported: Public Key not provided");
		}

		try {
			return this.encryptCipher.doFinal(data);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			throw new CryptoException(e);
		}
	}

	@Override
	public byte[] decrypt(byte[] data) throws CryptoException {
		if (this.decryptCipher == null) {
			throw new CryptoException("Decrypt not supported: Private Key not provided");
		}

		try {
			return this.decryptCipher.doFinal(data);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			throw new CryptoException(e);
		}
	}
}
