package com.qtone.gy.utils;

import org.apache.log4j.Logger;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


/**
 * @ClassName Encrypt
 * @Description 对称加密算法
 * @author huangguangxi
 * @date 2017-09-21
 */
public class EncryptUtil {
	
//	static Logger logger = LoggerFactory.getLogger(EncryptUtil.class);
	private static Logger logger = Logger.getLogger(EncryptUtil.class);
	/**
	 * 密钥：此处可以修改,但是长度必须是8位字符
	 * @Description
	 */
	private static final String KEY_ALGORITHM = "QT_TOKEN";
	
	/**
	 * 算法名称/加密模式/填充方式
	 * @Description
	 */
	private static final String CIPHER_ALGORITHM = "DES/CBC/PKCS5Padding";

	/**
	 * 解密传入的字符串
	 * @param message 加密后的字符串
	 * @return
	 */
	public static String dencrypt(String message) {
		return dencrypt(message, KEY_ALGORITHM);
	}

	/**
	 * 解密传入的字符串
	 * @param message 加密后的字符串
	 * @param key 密钥
	 * @return
	 */
	public static String dencrypt(String message, String key) {
		String result = null;
		byte[] bytesrc = convertHexString(message);
		Cipher cipher;
		try {
			cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
			cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
			byte[] retByte = cipher.doFinal(bytesrc);
			return new String(retByte);
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
		} catch (NoSuchPaddingException e) {
			logger.error(e.getMessage());
		} catch (InvalidKeyException e) {
			logger.error(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		} catch (InvalidKeySpecException e) {
			logger.error(e.getMessage());
		} catch (InvalidAlgorithmParameterException e) {
			logger.error(e.getMessage());
		} catch (IllegalBlockSizeException e) {
			logger.error(e.getMessage());
		} catch (BadPaddingException e) {
			logger.error(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 对传入的字符串加密
	 * @param value 待加密的字符串
	 * @param key   密钥
	 * @return
	 */
	public static String encryptWithKey(String value,String key) {
		String result = "";
		try {
			result = toHexString(encrypt(value, key)).toUpperCase();
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
		return result;
	}
	
	/**
	 * 对传入的字符串加密
	 * @param value 待加密的字符串
	 * @return
	 */
	public static String encrypt(String value) {
		String result = "";
		try {
			//String temp = java.net.URLEncoder.encode(value, "utf-8");
			result = toHexString(encrypt(value, KEY_ALGORITHM)).toUpperCase();
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
		return result;
	}

	/**
	 * 对传入的字符串加密
	 * @param message 待加密的信息
	 * @param key 密钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(String message, String key) throws Exception {
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		return cipher.doFinal(message.getBytes("UTF-8"));
	}

	/**
	 * 将字符串转换为为byte数组
	 * @param ss
	 * @return 
	 */
	private static byte[] convertHexString(String ss) {
		byte digest[] = new byte[ss.length() / 2];
		for (int i = 0; i < digest.length; i++) {
			String byteString = ss.substring(2 * i, 2 * i + 2);
			int byteValue = Integer.parseInt(byteString, 16);
			digest[i] = (byte) byteValue;
		}
		return digest;
	}

	/**将字符串转换为以十六进制（基数 16）无符号整数形式的字符串表示形式
	 * @param b
	 * @return
	 */
	private static String toHexString(byte b[]) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String plainText = Integer.toHexString(0xff & b[i]);
			if (plainText.length() < 2)
				plainText = "0" + plainText;
			hexString.append(plainText);
		}
		return hexString.toString();
	}
	/**
	 * 加密用户信息
	 * @author huangguangxi
	 * @date:   2017年9月22日 下午3:01:01    
	 * @param userId 用户ID
	 * @param userType 用户类型
	 * @return      
	 * @return: String      
	 * @throws
	 */
	public static String encryptUser(int userId,int userType){
		//加密用户信息
		String message = userId + "-" + userType + "-" + System.currentTimeMillis();
		System.out.println("加密前：" + message);
		return encrypt(message);
	}

	public static String encryptUser(int userId,int userType,int orgId, String reqChannel){
		//加密用户信息
		String message = userId + "-" + userType + "-" + orgId + "-" + System.currentTimeMillis() + "-" 
		+ reqChannel;
		System.out.println("加密前：" + message);
		return encrypt(message);
	}

	
	/**
	 * 检验token是否合法
	 * @author guohaibing
	 * @date 2017年10月9日 上午10:13:04
	 * @param token
	 * @return
	 */
	public static boolean checkToken(String token){
		if(token != null){
			String[] tokenArr = dencrypt(token).split("-");
			if(tokenArr != null && (tokenArr.length == 4 || tokenArr.length == 5)){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
		
	}
	
	public static void main(String[] args) {
		//System.out.println(dencrypt("16C5142DB034A8345E18F7844C22E75B5D11FFE8DC7A9D35"));
		                           // "06B36F53471E373DAED9A992EBA2DB6EB2248342333438D8
		System.out.println(encryptUser(112,2,7,"APPLET"));
		System.out.println(encryptUser(113,2,7,"APPLET"));
		System.out.println(encryptUser(115,2,7,"APPLET"));
		System.out.println(encryptUser(116,2,7,"APPLET"));
		System.out.println(encryptUser(147,2,7,"APPLET"));
		//System.out.println(encrypt("oohtZ5C-2-MD6rUHQjbi9M11VSSY"));
		//System.out.println(dencrypt("A5B97F787B1DEC6A019C94FC2A5F0374F25BA2FE701927320884099EF4D3E158"));
		/*String headUrl = "http:\\thirdwx.qlogo.cn\\mmopen\tFVyjFJKvdncGEH8OSgJr7P5jOWVLFWKqWueiaicth1Xca0yaOYzLlQY4mRJvibdQlJyqRcNRmgupaZTfyPpjg7icnMLIzgXDwicw\132";
		String userName = "黄光熙";
		String openId = "oWTOy0u_elvOQN63Y2voWFqqKHi8";
		String backType = "1";
		String organizationId = "1";
		String param = headUrl+"&"+userName+"&"+openId+"&"+backType+"&"+organizationId;
		String encryptString = EncryptUtil.encrypt(param);
		System.out.println(encryptString);*/
		//System.out.println(encryptUser(1,9,1));
		//System.out.println(encryptUser(1600,1,3,"WEB"));
		//Integer toplimit = 29;
		//int round = (int)Math.round(toplimit*0.3);
		//System.out.println(round);
	}
}
