package com.joker.myfw.util;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class PasswordUtil {
	
	public static final String ENCODE = "UTF-8";
	
	private static final String CRYPT_MD5 = "MD5";
	private static final String CRYPT_SHA1 = "SHA1";
	private static final String CRYPT_AES = "AES";

	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
		'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	
	/**
	 * 将二进制转换成16进制
	 * @param bytes
	 * @return
	 */
	public static String byteToHex(byte[] bytes){
		StringBuilder buf = new StringBuilder();
		
		int size = bytes.length;
		for(int i = 0; i < size; i++){
			buf.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[i] & 0x0f]);
		}
		
		return buf.toString();
	}
	
	/**
	 * 将16进制转换为二进制
	 * @param text
	 * @return
	 */
	public static byte[] hexToByte(String text){
		int size = text.length();
		if(size <= 0){
			return null;
		}
		
		byte[] bytes = new byte[size / 2];
		int high;
		int low;
		for(int i = 0; i < size / 2; i ++){
			high = Integer.parseInt(text.substring(i * 2, i * 2 + 1), 16);
			low = Integer.parseInt(text.substring(i * 2 + 1, i * 2 + 2), 16);
			bytes[i] = (byte)(high * 16 + low);
		}
		
		return bytes;
	}
	
	/**
	 * MD5加密
	 * @param content
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	public static String MD5(String content) throws NoSuchAlgorithmException{
		return encrypt(content, CRYPT_MD5);
	}
	
	/**
	 * SHA-1加密
	 * @param content
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	public static String SHA1(String content) throws NoSuchAlgorithmException{
		return encrypt(content, CRYPT_SHA1);
	}
	
	/**
	 * 加密
	 * @param content	被加密的字符串
	 * @param cryptType 加密类型(如MD5之类的)
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	public static String encrypt(String content, String cryptType) throws NoSuchAlgorithmException{
		MessageDigest messageDigest = MessageDigest.getInstance(cryptType);
		byte[] bytes = messageDigest.digest(content.getBytes());
		return byteToHex(bytes);
	}
	
	/**
	 * HMAC_MD5 签名
	 * @param content	被加密的字符串
	 * @param key		签名所用的key
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 */
	public static String HMAC_MD5(String content, String key) throws InvalidKeyException, NoSuchAlgorithmException{
		String HMAC_MD5 = "HmacMD5";
		SecretKeySpec signKey = new SecretKeySpec(key.getBytes(), HMAC_MD5);
		Mac mac = Mac.getInstance(HMAC_MD5);
		mac.init(signKey);
		byte[] bytes = mac.doFinal(content.getBytes());
		return byteToHex(bytes);
	}
	
	/**
	 * AES加密
	 * @param content
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String AESEncrypt(String content, String key) throws Exception{
		SecretKeySpec signKey = getKey(key);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, signKey, iv);
		byte[] encrypted = cipher.doFinal(content.getBytes());
		return byteToHex(encrypted);
	}
	
	/**
	 * AES解密
	 * @param content
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String AESDecrypt(String content, String key) throws Exception{
		SecretKeySpec signKey = getKey(key);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(Cipher.DECRYPT_MODE, signKey, iv);
        byte[] encrypted1 = hexToByte(content);

        byte[] original = cipher.doFinal(encrypted1);
        String originalString = new String(original);
        return originalString;
	}
	
	/**
	 * 建立AES的密钥
	 */
	private static SecretKeySpec getKey(String key) throws Exception {
		byte[] arrBTmp = key.getBytes();
		byte[] arrB = new byte[16]; // 创建一个空的16位字节数组（默认值为0）

		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}

		SecretKeySpec skeySpec = new SecretKeySpec(arrB, CRYPT_AES);

		return skeySpec;
	}
	
}
