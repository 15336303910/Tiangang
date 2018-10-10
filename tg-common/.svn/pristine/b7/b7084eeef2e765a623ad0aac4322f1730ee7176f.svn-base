package cn.plou.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ConcurrentHashMap;
import cn.plou.common.utils.Tools;

/**
 * @Project : tg-micro
 * @File : EsScript.java
 * @Author : WangJiWei
 * @Date : 2018年5月12日下午4:45:37
 *
 * @Comments :
 * 
 */
public class EsScript {

    private static final ConcurrentHashMap<String, String> CACHE = new ConcurrentHashMap<String, String>();

    private /* synchronized */ static String readFile(String filePath) {
	String content = CACHE.get(filePath);
	if (content != null) {
	    return content;
	} else {
	    StringBuffer sb = new StringBuffer();
	    try (BufferedReader reader = new BufferedReader(
		    new InputStreamReader(EsScript.class.getResourceAsStream(filePath)))) {
		String tempString = null;
		/** one line */
		while ((tempString = reader.readLine()) != null) {
		    sb.append(tempString.trim());
		}
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    CACHE.put(filePath, sb.toString());
	    return sb.toString();
	}
    }

    public static String read(String fileName) {
	return Tools.isNull(fileName) ? "{}" : readFile("/es_query/" + fileName + ".json");
    }

}
