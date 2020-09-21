package com.withwiz.plankton.network.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * HTTP utility
 */
public class HttpUtil {
    /**
     * logger
     */
    static Logger log = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * get HTTP request for method
     *
     * @param uri    request URI
     * @param method request method
     * @return HttpRequest
     */
    public static HttpRequestBase getHttpRequest(String uri, String method) {
        HttpRequestBase httpMethod = null;
        switch (method) {
            case HttpGet.METHOD_NAME:
                httpMethod = new HttpGet(uri);
                break;
            case HttpPost.METHOD_NAME:
                httpMethod = new HttpPost(uri);
                break;
            case HttpPut.METHOD_NAME:
                httpMethod = new HttpPut(uri);
                break;
            case HttpDelete.METHOD_NAME:
                httpMethod = new HttpDelete(uri);
                break;
            default:
                log.error("The request method NOT supported.");
                break;
        }
        return httpMethod;
    }

    /**
     * get request HTTP message
     *
     * @param uri       request URI
     * @param method    request method
     * @param headerMap request header list
     * @return HttpRequestBase
     */
    public static HttpRequestBase getHttpRequest(String uri, String method, Map headerMap) {
        HttpRequestBase request = (HttpRequestBase) getHttpRequest(uri, method);
        if (headerMap != null)
            headerMap.forEach((k, v) -> request.addHeader((String) k, (String) v));
        return request;
    }

    /**
     * request HTTP message
     *
     * @param request HttpRequestBase
     * @return HttpResponse
     * @throws IOException
     */
    public static HttpResponse request(HttpUriRequest request) throws IOException {
        log.debug("request:\n{}", request);
        HttpResponse response = HttpClients.createDefault().execute(request);
        log.debug("response:\n{}", response);
        return response;
    }

    /**
     * request HTTP message
     * method: GET
     *
     * @param uri request URI
     * @return HttpResponse
     * @throws IOException
     */
    public static HttpResponse request(String uri) throws IOException {
        HttpUriRequest httpGet = getHttpRequest(uri, HttpGet.METHOD_NAME);
        return request(httpGet);
    }

    /**
     * request HTTP message
     *
     * @param uri request URI
     * @param method HTTP method
     * @param headerMap request header list
     * @return HttpResponse
     * @throws IOException
     */
    public static HttpResponse request(String uri, String method, Map headerMap) throws IOException {
        HttpUriRequest httpMethod = getHttpRequest(uri, method, headerMap);
        return request(httpMethod);
    }


    /**
     * request HTTP message
     *
     * @param uri        request URI
     * @param method     request method
     * @param jsonString JSON string
     * @return HttpResponse
     * @throws IOException
     */
    public static HttpResponse requestJson(String uri, String method, Map headerMap, String jsonString, Charset charset) throws IOException {
        return requestString(uri, method, headerMap, jsonString, charset);
    }

    /**
     * request HTTP message
     *
     * @param uri    request URI
     * @param method request method
     * @param text   string text
     * @return HttpResponse
     * @throws IOException
     */
    public static HttpResponse requestString(String uri, String method, Map headerMap, String text, Charset charset) throws IOException {
        HttpEntityEnclosingRequestBase request = (HttpEntityEnclosingRequestBase)getHttpRequest(uri, method, headerMap);
        StringEntity entity = new StringEntity(text, charset);
        request.setEntity(entity);
        return request(request);
    }

    public static void main(String[] args) {
        try {
            HttpUtil.request("http://www.naver.com");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
