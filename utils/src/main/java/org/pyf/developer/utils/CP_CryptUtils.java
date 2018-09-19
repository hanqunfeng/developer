/*
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                   *
 ****************************************************************************
 * 源文件名:  org.cpframework.cp_utils.sss.java 													       
 * 功能: 加密解密工具类													   
 * 版本:	@version 1.0	                                                                   
 * 编制日期: 2014年1月14日 下午6:13:43 						    						                                        
 * 修改历史: (主要历史变动原因及说明)		
 * YYYY-MM-DD |    Author      |	 Change Description		      
 * 2014年1月14日    |    孙成启     |     Created 
 */
package org.pyf.developer.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.*;

/**
 *Description: <加密解密工具类>. <br>
 *<p>
	<使用说明>
 </p>
 *Makedate:2014年1月14日 下午6:13:43 
 * @author 孙成启  
 * @version V1.0                             
 */
public class CP_CryptUtils {
	
	static Logger logger = LoggerFactory.getLogger(CP_CryptUtils.class);
	
	/**
	 * 
	* 描述 : <进行MD5加密>. <br> 
	*<p> 
		<使用方法说明>  
	 </p>                                                                                                                                                                                                                                                
	* @param info 要加密的信息
	* @return String 加密后的字符串
	 */
	public static String encryptToMD5(String info) {
		byte[] digesta = null;
		try {
			// 得到一个md5的消息摘要
			MessageDigest alga = MessageDigest.getInstance("MD5");
			// 添加要进行计算摘要的信息
			alga.update(info.getBytes());
			// 得到该摘要
			digesta = alga.digest();
		} catch (NoSuchAlgorithmException e) {
			logger.error("CP_CryptUtils-->encryptToMD5异常:",e);
		}
		// 将摘要转为字符串
		String rs = byte2hex(digesta);
		return rs;
	}
	
	/**
	 * 
	* 描述 : <进行SHA加密>. <br> 
	*<p> 
		<使用方法说明>  
	 </p> 
	 * 
	 * @param info
	 *            要加密的信息
	 * @return String 加密后的字符串
	 */
	public static String encryptToSHA(String info) {
		byte[] digesta = null;
		try {
			// 得到一个SHA-1的消息摘要
			MessageDigest alga = MessageDigest.getInstance("SHA-1");
			// 添加要进行计算摘要的信息
			alga.update(info.getBytes());
			// 得到该摘要
			digesta = alga.digest();
		} catch (NoSuchAlgorithmException e) {
			logger.error("CP_CryptUtils-->encryptToSHA异常:",e);
		}
		// 将摘要转为字符串
		String rs = byte2hex(digesta);
		return rs;
	}
	/**
	 * 
	* 描述 : <创建密钥>. <br> 
	*<p> 
		<使用方法说明>  
	 </p> 
	 * @param algorithm
	 *            加密算法,可用 DES,DESede,Blowfish
	 * @return SecretKey 秘密（对称）密钥
	 */
	public static SecretKey createSecretKey(String algorithm) {
		// 声明KeyGenerator对象
		KeyGenerator keygen;
		// 声明 密钥对象
		SecretKey deskey = null;
		try {
			// 返回生成指定算法的秘密密钥的 KeyGenerator 对象
			keygen = KeyGenerator.getInstance(algorithm);
			// 生成一个密钥
			deskey = keygen.generateKey();
		} catch (NoSuchAlgorithmException e) {
			logger.error("CP_CryptUtils-->createSecretKey异常:",e);
		}
		// 返回密匙
		return deskey;
	}
	/**
	 * 
	* 描述 : <根据密匙进行DES加密>. <br> 
	*<p> 
		<使用方法说明>  
	 </p> 
	 * @param key
	 *            密匙
	 * @param info
	 *            要加密的信息
	 * @return String 加密后的信息
	 */
	@Deprecated
	public static String encryptToDES(SecretKey key, String info) {
		// 定义 加密算法,可用 DES,DESede,Blowfish
		String Algorithm = "DES";
		// 加密随机数生成器 (RNG),(可以不写)
		SecureRandom sr = new SecureRandom();
		// 定义要生成的密文
		byte[] cipherByte = null;
		try {
			// 得到加密/解密器
			Cipher c1 = Cipher.getInstance(Algorithm);
			// 用指定的密钥和模式初始化Cipher对象
			// 参数:(ENCRYPT_MODE, DECRYPT_MODE, WRAP_MODE,UNWRAP_MODE)
			c1.init(Cipher.ENCRYPT_MODE, key, sr);
			// 对要加密的内容进行编码处理,
			cipherByte = c1.doFinal(info.getBytes());
		} catch (Exception e) {
			logger.error("CP_CryptUtils-->encryptToDES异常:",e);;
		}
		// 返回密文的十六进制形式
		return byte2hex(cipherByte);
	}
	/**
	 * 
	* 描述 : <根据密匙进行DES解密>. <br> 
	*<p> 
		<使用方法说明>  
	 </p> 
	 * @param key
	 *            密匙
	 * @param sInfo
	 *            要解密的密文
	 * @return String 返回解密后信息
	 */
	@Deprecated
	public static String decryptByDES(SecretKey key, String sInfo) {
		// 定义 加密算法,
		String Algorithm = "DES";
		// 加密随机数生成器 (RNG)
		SecureRandom sr = new SecureRandom();
		byte[] cipherByte = null;
		try {
			// 得到加密/解密器
			Cipher c1 = Cipher.getInstance(Algorithm);
			// 用指定的密钥和模式初始化Cipher对象
			c1.init(Cipher.DECRYPT_MODE, key, sr);
			// 对要解密的内容进行编码处理
			cipherByte = c1.doFinal(hex2byte(sInfo));
		} catch (Exception e) {
			logger.error("CP_CryptUtils-->decryptByDES异常:",e);;
		}
		// return byte2hex(cipherByte);
		return new String(cipherByte);
	}
	/**
	 * 
	* 描述 : <创建密匙组，并将公匙，私匙放入到指定文件中>. <br> 
	*<p> 
	 </p>
	 * @param keySavefilePath  保存密钥的文件路径,包含文件名  为空的话 默认放在当前类路径下的mykeys.bat文件中
	 */
	public static void createPairKey(String keySavefilePath) {
		if(keySavefilePath==null||keySavefilePath==""){
			keySavefilePath="mykeys.bat";
		}
		try {
			// 根据特定的算法一个密钥对生成器
			KeyPairGenerator keygen = KeyPairGenerator.getInstance("DSA");
			// 加密随机数生成器 (RNG)
			SecureRandom random = new SecureRandom();
			// 重新设置此随机对象的种子
			random.setSeed(1000);
			// 使用给定的随机源（和默认的参数集合）初始化确定密钥大小的密钥对生成器
			keygen.initialize(512, random);// keygen.initialize(512);
			// 生成密钥组
			KeyPair keys = keygen.generateKeyPair();
			// 得到公匙
			PublicKey pubkey = keys.getPublic();
			// 得到私匙
			PrivateKey prikey = keys.getPrivate();
			// 将公匙私匙写入到文件当中
			doObjToFile(keySavefilePath, new Object[] { prikey, pubkey });
		} catch (NoSuchAlgorithmException e) {
			logger.error("CP_CryptUtils-->createPairKey异常:",e);;
		}
	}
	/**
	 * 
	* 描述 : <利用私匙对信息进行签名 把签名后的信息放入到指定的文件中>. <br> 
	*<p> 
	 </p>
	 * @param info
	 *            要签名的信息
	 * @param signfile
	 *            存入的文件
	 */
	public static void signToInfo(String info, String signfile,String keySavefilePath) {
		if(keySavefilePath==null||keySavefilePath==""){
			keySavefilePath="mykeys.bat";
		}
		if(signfile==null||signfile==""){
			signfile="mysign.bat";
		}
		// 从文件当中读取私匙
		PrivateKey myprikey = (PrivateKey) getObjFromFile(keySavefilePath, 1);
		// 从文件中读取公匙
		PublicKey mypubkey = (PublicKey) getObjFromFile(keySavefilePath, 2);
		try {
			// Signature 对象可用来生成和验证数字签名
			Signature signet = Signature.getInstance("DSA");
			// 初始化签署签名的私钥
			signet.initSign(myprikey);
			// 更新要由字节签名或验证的数据
			signet.update(info.getBytes());
			// 签署或验证所有更新字节的签名，返回签名
			byte[] signed = signet.sign();
			// 将数字签名,公匙,信息放入文件中
			doObjToFile(signfile, new Object[] { signed, mypubkey, info });
		} catch (Exception e) {
			logger.error("CP_CryptUtils-->signToInfo异常:",e);;
		}
	}
	/**
	 * 
	* 描述 : <读取数字签名文件 根据公匙，签名，信息验证信息的合法性>. <br> 
	*<p> 
		<使用方法说明>  
	 </p>                                                                                                                                                                                                                                                
	* @param signfile
	* @return true 验证成功 false 验证失败
	 */
	public static boolean validateSign(String signfile) {
		// 读取公匙
		PublicKey mypubkey = (PublicKey) getObjFromFile(signfile, 2);
		// 读取签名
		byte[] signed = (byte[]) getObjFromFile(signfile, 1);
		// 读取信息
		String info = (String) getObjFromFile(signfile, 3);
		try {
			// 初始一个Signature对象,并用公钥和签名进行验证
			Signature signetcheck = Signature.getInstance("DSA");
			// 初始化验证签名的公钥
			signetcheck.initVerify(mypubkey);
			// 使用指定的 byte 数组更新要签名或验证的数据
			signetcheck.update(info.getBytes());
			System.out.println(info);
			// 验证传入的签名
			return signetcheck.verify(signed);
		} catch (Exception e) {
			logger.error("CP_CryptUtils-->validateSign异常:",e);;
			return false;
		}
	}
	/**
	 * 
	* 描述 : <将二进制转化为16进制字符串>. <br> 
	*<p> 
		<使用方法说明>  
	 </p>                                                                                                                                                                                                                                                
	* @param b
	* @return
	 */
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}
	/**
	 * 
	* 描述 : <十六进制字符串转化为2进制>. <br> 
	*<p> 
		<使用方法说明>  
	 </p>                                                                                                                                                                                                                                                
	* @param hex
	* @return
	 */
	public static byte[] hex2byte(String hex) {
		byte[] ret = new byte[8];
		byte[] tmp = hex.getBytes();
		for (int i = 0; i < 8; i++) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}
	/**
	 * 
	* 描述 : <将两个ASCII字符合成一个字节； 如："EF"--> 0xEF>. <br> 
	*<p> 
		<使用方法说明>  
	 </p>                                                                                                                                                                                                                                                
	* @param src0
	* @param src1
	* @return
	 */
	public static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
				.byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
				.byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}
	/**
	 * 
	* 描述 : <将指定的对象写入指定的文件>. <br> 
	*<p> 
		<使用方法说明>  
	 </p>                                                                                                                                                                                                                                                
	* @param file 指定写入的文件
	* @param objs 要写入的对象
	 */
	public static void doObjToFile(String file, Object[] objs) {
		ObjectOutputStream oos = null;
		try {
			FileOutputStream fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);
			for (int i = 0; i < objs.length; i++) {
				oos.writeObject(objs[i]);
			}
		} catch (Exception e) {
			logger.error("CP_CryptUtils-->doObjToFile异常:",e);;
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				logger.error("CP_CryptUtils-->doObjToFile异常:",e);;
			}
		}
	}
	/**
	 * 
	* 描述 : <返回在文件中指定位置的对象>. <br> 
	*<p> 
		<使用方法说明>  
	 </p>                                                                                                                                                                                                                                                
	* @param file 指定的文件
	* @param i 从1开始
	* @return
	 */
	public static Object getObjFromFile(String file, int i) {
		ObjectInputStream ois = null;
		Object obj = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			for (int j = 0; j < i; j++) {
				obj = ois.readObject();
			}
		} catch (Exception e) {
			logger.error("CP_CryptUtils-->getObjFromFile异常:",e);;
		} finally {
			try {
				ois.close();
			} catch (IOException e) {
				logger.error("CP_CryptUtils-->getObjFromFile异常:",e);;
			}
		}
		return obj;
	}
	/**
	 * 测试
	 * 
	 * @param args
	 */
	//public static void main(String[] args) {
	//	CP_CryptUtils jiami = new CP_CryptUtils();
	//	// 执行MD5加密"Hello world!"
	//	System.out.println("Hello经过MD5:" + jiami.encryptToMD5("Hello"));
	//	// 生成一个DES算法的密匙
	//	SecretKey key = jiami.createSecretKey("DES");
	//	// 用密匙加密信息"Hello world!"
	//	String str1 = jiami.encryptToDES(key, "Hello");
	//	System.out.println("使用des加密信息Hello为:" + str1);
	//	// 使用这个密匙解密
	//	String str2 = jiami.decryptByDES(key, str1);
	//	System.out.println("解密后为：" + str2);
	//	// 创建公匙和私匙
	//	jiami.createPairKey("src/test/resources/mykeys.bat");
	//	// 对Hello world!使用私匙进行签名
	//	jiami.signToInfo("Hello", "src/test/resources/mysign.bat","src/test/resources/mykeys.bat");
	//	// 利用公匙对签名进行验证。
	//	if (jiami.validateSign("src/test/resources/mysign.bat")) {
	//		System.out.println("Success!");
	//	} else {
	//		System.out.println("Fail!");
	//	}
	//}
}