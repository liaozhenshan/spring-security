package com.lzs.springsecurity.util;

import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author liaozhenshan
 * @version 1.0
 * @date 2024/5/27 9:19
 */
public class ResponseUtils {
    public static final Logger logger = LoggerFactory.getLogger(ResponseUtils.class);

    /**
     * 发送文本，使用UTF-8编码
     *
     * @param response
     * @param text
     */
    public static void renderText(HttpServletResponse response, String text) {
        render(response, "text/plain;charset=UTF-8", text);
    }

    /**
     * 发送json
     *
     * @param response
     * @param object
     */
    public static void renderJson(HttpServletResponse response, Object object) {
        render(response, "application/json;charset=UTF-8", JSONObject.toJSONString(object));
    }

    /**
     * 发送xml
     *
     * @param response
     * @param text
     */
    public static void renderXml(HttpServletResponse response, String text) {
        render(response, "text/xml;charset=UTF-8", text);
    }


    /**
     * 发送内容
     *
     * @param response
     * @param contentType
     * @param text
     */
    public static void render(HttpServletResponse response, String contentType, String text) {
        response.setContentType(contentType);
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        PrintWriter writer = null;

        try {
            writer = response.getWriter();
            writer.write(text);
            writer.flush();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }

}
