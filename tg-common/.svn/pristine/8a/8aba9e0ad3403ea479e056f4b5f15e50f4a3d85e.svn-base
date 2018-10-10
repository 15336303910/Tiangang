package cn.plou.common.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {

	public static byte[] AESDncode(String encodeRules,String content,String iv){
		try {
			byte[] bencodeRules = StringUtil.HexString2BytesNormal(encodeRules);
			byte[] bcontent = StringUtil.HexString2BytesNormal(content);

			// 1.构造密钥生成器，指定为AES算法,不区分大小写
			KeyGenerator keygen = KeyGenerator.getInstance("AES");
			// 2.根据ecnodeRules规则初始化密钥生成器
			// 生成一个128位的随机源,根据传入的字节数组
			// keygen.init(128, new SecureRandom(cc.getBytes()));
			keygen.init(128, new SecureRandom(bencodeRules));
			// 3.产生原始对称密钥
			SecretKey original_key = keygen.generateKey();
			// 4.获得原始对称密钥的字节数组
			byte[] raw = original_key.getEncoded();
			// 5.根据字节数组生成AES密钥
			SecretKey key = new SecretKeySpec(bencodeRules, "AES");
			// 6.根据指定算法AES自成密码器
			// Cipher cipher=Cipher.getInstance("AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			// 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
			byte[] biv = StringUtil.HexString2BytesNormal(iv);
			cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(biv));
			byte[] byte_decode = cipher.doFinal(bcontent);
//			return StringUtil.Bytes2HexString(byte_decode);
			return byte_decode;
		} catch (Exception e) {
			e.printStackTrace();

		}

		// 如果有错就返加nulll
		return null;
    }
}
