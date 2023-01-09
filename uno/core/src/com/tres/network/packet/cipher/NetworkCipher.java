package com.tres.network.packet.cipher;

abstract public class NetworkCipher {

	abstract protected String getTransformation();

	abstract public byte[] encrypt(byte[] data) throws CryptoException;

	abstract public byte[] decrypt(byte[] data) throws CryptoException;
}
