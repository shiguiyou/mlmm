package com.wanquan.mlmmx.mlmm.kotlin.rsa;

import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class RSAUtils {

	private static final String RSA_DECRYPT = "RSA/ECB/PKCS1Padding";
	private static final String RSA_ENCRYPT = "RSA";

	private static final int MAX_ENCRYPT_BLOCK = 117;// RSA最大加密明文大小
	private static final int MAX_DECRYPT_BLOCK = 128;// RSA最大解密密文大小

	/**
	 * 公钥加密
	 * 
	 * @param data
	 *            需加密的数据
	 * @param publicKey
	 *            公钥
	 * @return 加密后的数据
	 */
	public static String encrypyByPublicKey(byte[] data, String publicKey) {
		String encryptData = "";
		try {
			byte[] encrypyByte = encryptDataByPublicKey(data,
					loadPublicKey(publicKey));
			encryptData = Base64.encodeToString(encrypyByte, Base64.DEFAULT);
			return encryptData;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 用公钥加密 每次加密的字节数，不能超过密钥的长度值减去11
	 * 
	 * @param data
	 *            需加密数据的byte数据
	 * @param publicKey
	 *            公钥
	 * @return 加密后的byte数据
	 */
	private static byte[] encryptDataByPublicKey(byte[] data,
			PublicKey publicKey) {
		try {
			Cipher cipher = Cipher.getInstance(RSA_DECRYPT);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);

			int inputLen = data.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段加密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			byte[] encryptedData = out.toByteArray();
			out.close();
			return encryptedData;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 从字符串中加载公钥
	 * 
	 * @param publicKeyStr
	 *            公钥数据字符串
	 * @return
	 * @throws Exception
	 *             加载公钥时产生异常
	 */
	private static PublicKey loadPublicKey(String publicKeyStr) throws Exception {
		try {
			byte[] buffer = Base64Utils.decode(publicKeyStr);
			KeyFactory factory = KeyFactory.getInstance(RSA_ENCRYPT);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			return factory.generatePublic(keySpec);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	
	public static String decryptByPrivateKey(String encryptdata, String privateKey) throws Exception {
		String decrydata = "";
		try {
			byte[] decryByte = decryptDataByPrivateKey(Base64Utils.decode(encryptdata), loadPrivateKey(privateKey));
			decrydata = new String(decryByte);
			return decrydata;
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("解密异常");
		}
		
	}
	/**
	 * 用私钥解密
	 * 
	 * @param encryData
	 *            经过encryData()加密返回的byte数据
	 * @param privateKey
	 *            私钥
	 * @return 解密后的byte数据
	 */
	private static byte[] decryptDataByPrivateKey(byte[] encryData, PrivateKey privateKey) throws Exception {
		try {
			Log.i("hx", "============decryptDataByPrivateKey===============");
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);

			int inputLen = encryData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段解密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
					cache = cipher
							.doFinal(encryData, offSet, MAX_DECRYPT_BLOCK);
				} else {
					cache = cipher
							.doFinal(encryData, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_DECRYPT_BLOCK;
			}
			byte[] decryptedData = out.toByteArray();
			out.close();
			return decryptedData;

			// return cipher.doFinal(encryData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("hx", "解密异常");
			throw new Exception("解密异常");
		}
	}

	/**
	 * 从字符串中加载私钥 加载时使用PKCS8EncodedKeySpec(pkcs8编码的key指令 )
	 * 
	 * @param privateKeyStr
	 * @return
	 * @throws Exception
	 */
	private static PrivateKey loadPrivateKey(String privateKeyStr)
			throws Exception {
		byte[] buffer = Base64Utils.decode(privateKeyStr);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
		KeyFactory factory = KeyFactory.getInstance(RSA_DECRYPT);
		return factory.generatePrivate(keySpec);
	}

	/**
	 * 私钥对sign加密
	 * 
	 * @param data
	 * @param privateKeyStr
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static String encryptByPrivateKey(byte[] data, String privateKeyStr)
			throws Exception {
		String shaByte = "";
		try {
			byte[] privateKeyBytes = Base64Utils.decode(privateKeyStr);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(
					privateKeyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			Key privateK = keyFactory.generatePrivate(keySpec);
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, privateK);

			int inputLen = data.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offset = 0;
			byte[] cache;
			int i = 0;
			while ((inputLen - offset) > 0) {
				if ((inputLen - offset) > MAX_ENCRYPT_BLOCK) {
					cache = cipher.doFinal(data, offset, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(data, offset, inputLen - offset);
				}
				out.write(cache, 0, cache.length);
				i++;
				offset = i * MAX_ENCRYPT_BLOCK;
			}
			byte[] encryptData = out.toByteArray();
			// shaByte = new String(encryptData);
			out.close();

			shaByte = Base64.encodeToString(encryptData, Base64.DEFAULT);
			shaByte = URLEncoder.encode(shaByte);
			return shaByte;
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("Exception");
		}

	}


}
