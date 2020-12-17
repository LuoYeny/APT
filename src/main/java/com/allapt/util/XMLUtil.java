package com.allapt.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 罗叶妮
 * @version 1.0
 * @date 2020/7/28 21:18
 */
public class XMLUtil {
    public static Element readJsonFromClassPath(String path ) throws IOException, DocumentException {


        SAXReader reader = new SAXReader();
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);

        Document doc = reader.read(is);
        Element rootElement = doc.getRootElement();
        return rootElement;




    }
}
