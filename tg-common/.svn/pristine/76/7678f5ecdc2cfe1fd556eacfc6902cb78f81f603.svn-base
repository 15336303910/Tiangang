package cn.plou.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;

/**
 * @Project :
 * @File : SecureUtils.java
 * @Author : WangJiWei
 * @Date : 2017年11月24日下午1:26:04
 * 
 * @Comments :
 * 
 */
public class SecureUtils {

    private static final CRC32 CRC_32 = new CRC32(); //
    /** 盐 */
    private static final String SALT = "cmb$_";

    /** 加密算法 */
    public interface EncryptAlgorithm {
	public static final String MD5 = "MD5";
	public static final String SHA_256 = "SHA-256";
    }

    public static long crc32Code(byte[] bytes) {
	CRC_32.reset();
	CRC_32.update(bytes);
	return CRC_32.getValue();
    }

    private static MessageDigest getMessageDigest(String algorithm) {
	try {
	    return MessageDigest.getInstance(algorithm);
	} catch (NoSuchAlgorithmException e) {
	    throw new RuntimeException(e);
	}
    }

    public static String sha256(String password) {
	password = isSALT(password, false);
	return encrypt(getMessageDigest(EncryptAlgorithm.SHA_256), password);
    }

    public static String md5(String password) {
	password = isSALT(password, true);
	return encrypt(getMessageDigest(EncryptAlgorithm.MD5), password);
    }

    private static String isSALT(String password, boolean flag) {
	return password + (flag ? SALT : "");
    }

    private static byte[] StringToBytes(String password) {
	char[] charArray = password.toCharArray();
	byte[] byteArray = new byte[charArray.length];
	for (int i = 0; i < charArray.length; i++) {
	    byteArray[i] = (byte) charArray[i];
	}
	return byteArray;
    }

    private static String encrypt(MessageDigest md, String password) {
	byte[] md5Bytes = md.digest(StringToBytes(password));
	StringBuffer hexValue = new StringBuffer();
	for (int i = 0; i < md5Bytes.length; i++) {
	    int val = ((int) md5Bytes[i]) & 0xff;
	    if (val < 16) {
		hexValue.append("0");
	    }
	    hexValue.append(Integer.toHexString(val));
	}
	return hexValue.toString();
    }

    public static void main(String[] args) throws Exception {
	/** 文件内容的crc */
	FileInputStream in = new FileInputStream(new File("E://a.txt"));
	byte[] buf = new byte[100];
	ByteArrayOutputStream out = new ByteArrayOutputStream();
	int read = in.read(buf);
	out.write(buf, 0, read);
	System.out.println(crc32Code(out.toByteArray()));
	System.out.println(crc32Code("1 1 2".getBytes()));
	System.out.println(crc32Code("1 1 2".getBytes()));
	in.close();
	System.out.println(sha256("admin"));
    }

}
