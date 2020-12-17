package com.allapt.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.Feature;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;


/**
 * @author 罗叶妮
 * @version 1.0
 * @date 2020/7/25 22:14
 */
public class JsonUtil  {
    public static  <T> T  readJsonFromClassPath(String path, Type type) throws IOException {

               ClassPathResource resource = new ClassPathResource(path);

        if (resource.exists()) {
            return JSON.parseObject(resource.getInputStream(), StandardCharsets.UTF_8, type,
                    // 自动关闭流path
                    Feature.AutoCloseSource,
                    // 允许注释
                    Feature.AllowComment,
                    // 允许单引号
                    Feature.AllowSingleQuotes,
                    // 使用 Big decimal
                    Feature.UseBigDecimal);
        } else {
            System.out.println("resource not exists");
            throw new IOException();
        }




    }

}
