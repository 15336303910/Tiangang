package cn.plou.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

/**
 * @Project : tg-services
 * @File : XMLUtils.java
 * @Author : WangJiWei
 * @Date : 2017年12月21日下午5:32:49
 *
 * @Comments : XML工具类 解析xml文件并转化为JavaBean对象 <br>
 * 
 */
public class XMLUtils {

    public static Document read(File file) throws DocumentException {
	if (file == null || !file.exists()) {
	    return null;
	}
	SAXReader READER = new SAXReader();
	READER.setEncoding("GBK");
	try (FileInputStream in = new FileInputStream(file)) {
	    READER.read(in);
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e1) {
	    e1.printStackTrace();
	}
	Document document = READER.read(file);
	return document;

    }

    /**
     * 
     * @Author : WangJiwei
     * @Date : 2017年12月22日上午8:13:29
     * @Comments :
     * @param DV
     * @return 转化为map
     *
     */
    public static Map<String, Object> readMap(Element elet) {
	Map<String, Object> j = null;
	List<Element> elements = elet.elements(); // elet = INF,D,V
	// Map<String, Object> re = null;

	if (elements != null && elements.size() != 0) {
	    j = new HashMap<>();
	    // re = new HashMap<>();
	    List<String> wus = new ArrayList<String>();
	    for (Element e : elements) {
		if ("wu".equals(e.getName())) {
		    wus.add(e.getData().toString());
		} else {
		    j.put(e.getName(), e.getData());
		}

	    }
	    if (wus.size() != 0) {
		j.put("wus", wus);
	    }
	    // re.put("type", elet.getName());
	    // re.put("data", j);
	}
	// return re;
	return j;
    }

    /**
     * 
     * @Author : WangJiwei
     * @Date : 2017年12月22日上午8:13:53
     * @Comments :
     * @param path
     * @return 获取xml中Element的迭代
     * @throws DocumentException
     *
     */
    public static Iterator<Element> elementIterator(String path) throws DocumentException {
	Document document = read(new File(path));
	Element rootElement = document.getRootElement();
	return rootElement.elementIterator();
    }

    /**
     * 
     * @Author : WangJiwei
     * @Date : 2018年6月15日上午11:16:49
     * @Comments : 将所有数据转换为List<Map<String, Object>>
     * @param file
     * @return
     * @throws DocumentException
     *
     */
    public static List<Map<String, Object>> toList(File file) throws DocumentException {
	Document doc = read(file);
	if (doc != null) {
	    Element root = doc.getRootElement();// MR
	    if (root != null) {
		List<Element> elements = root.elements();
		if (Tools.isNotEmpty(elements)) {
		    List<Map<String, Object>> datas = Lists.newArrayList();
		    for (Element e : elements) { // INF , D ,V ,E
			Map<String, Object> m = readMap(e);
			Map<String, Object> data = new HashMap<>();
			data.put("type", e.getName());
			// 没有子元素的过滤=> <INF>,<E>等
			data.put("data", null == m ? e.getData() : m);
			datas.add(data);
		    }
		    return datas;
		}
	    }
	}
	return null;
    }

    public static void main(String[] args) throws DocumentException {
	List<Map<String, Object>> list = toList(
		new File("S:/xmlw-test-xml-parse-1/M0080321318082308000231.xml"));
	list.forEach(m -> {

	    if (m.get("type").toString().equals("INF")) {
		String s = m.get("data").toString();
		String[] split = s.split("\n");
		for(String ssss : split){
		    if(StringUtils.isNotBlank(ssss)){
			System.out.println(ssss);
		    }
		}
	    }
	    // System.out.println(m.get("data"));
	});
    }

}
