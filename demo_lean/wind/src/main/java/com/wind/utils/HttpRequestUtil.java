package com.wind.utils;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpStatus;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * HttpRequestUtil
 *
 * @author: GJK
 * @date: 2022/7/16 17:53
 * @description:
 */
@Slf4j
public final class HttpRequestUtil {
    private static final int CONNECT_REQUEST_TIMEOUT = 10000;
    private static final int CONNECT_TIMEOUT = 10000;
    private static final int SOCKET_TIMEOUT = 60000;
    private static final int MAX_RETRY = 2;
    private static final int MAX_PER_ROUTE = 40;
    private static final int MAX_TOTAL = 200;

    private static final String STRING_UNKNOWN = "unKnown";
    private static CloseableHttpClient httpClient = null;

    static {
        init();
    }

    private HttpRequestUtil() {

    }

    private static void init() {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        //设置每个路由的默认最大连接数
        connectionManager.setDefaultMaxPerRoute(MAX_PER_ROUTE);
        //设置连接池的最大连接数
        connectionManager.setMaxTotal(MAX_TOTAL);
        RequestConfig requestConfig = RequestConfig.custom()
                //设置连接超时时间
                .setConnectTimeout(CONNECT_TIMEOUT)
                //设置socket超时时间
                .setSocketTimeout(SOCKET_TIMEOUT)
                //设置从连接池获取连接的超时时间
                .setConnectionRequestTimeout(CONNECT_REQUEST_TIMEOUT).build();
        httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                .setRetryHandler(getRetryHandler()).build();
        ShutDownUtil.addShutdownTask(httpClient);
    }

    private static HttpRequestRetryHandler getRetryHandler() {
        return (exception, executionCount, context) -> {
            //如果重试次数超过默认值则放弃
            if (executionCount >= MAX_RETRY) {
                return false;
            }
            HttpClientContext httpClientContext = HttpClientContext.adapt(context);
            // 如果服务器丢掉了连接，那么就重试
            if (exception instanceof NoHttpResponseException) {
                return true;
            }
            // 不要重试SSL握手异常
            if (exception instanceof SSLHandshakeException) {
                return false;
            }
            // 超时
            if (exception instanceof InterruptedIOException) {
                return false;
            }
            // 目标服务器不可达
            if (exception instanceof UnknownHostException) {
                return false;
            }
            // SSL握手异常
            if (exception instanceof SSLException) {
                return false;
            }
            HttpRequest httpRequest = httpClientContext.getRequest();
            // 如果请求被视为幂等，就再次尝试
            return !(httpRequest instanceof HttpEntityEnclosingRequest);
        };
    }


    public static String getForString(String url) {
        return baseGet(url, httpEntity -> EntityUtils.toString(httpEntity, StandardCharsets.UTF_8));
    }

    public static String postForString(String url, Object params, Header... headers) {
        return basePost(url, params, httpEntity -> EntityUtils.toString(httpEntity, StandardCharsets.UTF_8), headers);
    }


    /**
     * GET请求的基础方法
     */
    private static <T> T baseGet(String url, ExtFunction<HttpEntity, T> function, Header... headers) {
        HttpGet httpGet = new HttpGet(url);
        if (headers != null) {
            for (Header header : headers) {
                httpGet.addHeader(header);
            }
        }
        return baseProcess(httpGet, function, StringUtils.EMPTY);
    }

    /**
     * POST请求的基础方法
     */
    private static <T> T basePost(String url, Object params, ExtFunction<HttpEntity, T> function, Header... headers) {
        checkState(url);
        HttpPost httpPost = new HttpPost(url);
        if (headers != null) {
            for (Header header : headers) {
                httpPost.setHeader(header);
            }
        }
        String paramStr = StringUtils.EMPTY;
        if (params != null) {
            if (params instanceof HttpEntity) {
                httpPost.setEntity((HttpEntity) params);
            } else {
                paramStr = params instanceof JSON ? ((JSON) params).toJSONString() : JSON.toJSONString(params);
                httpPost.setEntity(new StringEntity(paramStr, ContentType.APPLICATION_JSON));
            }
        }
        return baseProcess(httpPost, function, paramStr);
    }

    /**
     * HTTP请求的基础方法
     */
    private static <T> T baseProcess(HttpUriRequest httpUriRequest, ExtFunction<HttpEntity, T> function, String paramStr) {
        T result = null;
        int statusCode = -1;
        try (CloseableHttpResponse httpResponse = httpClient.execute(httpUriRequest)) {
            statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                result = function.apply(httpResponse.getEntity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        logBaseInfo(httpUriRequest.getURI().toString(), paramStr, result, statusCode);
        return result;
    }


    private static void checkState(String url) {
        if (httpClient == null) {
            throw new IllegalStateException("HttpClient连接池未创建");
        }
        if (StringUtils.isBlank(url)) {
            throw new IllegalArgumentException("参数错误");
        }
    }

    private static <T> void logBaseInfo(String url, String paramStr, T result, int statusCode) {
        if (result instanceof String) {
            log.info("调用url:{},参数为:{},相应code为:{},结果为:{}", url, paramStr, statusCode, result);
        } else {
            log.info("调用url:{},参数为:{},相应code为:{}", url, paramStr, statusCode);
        }
    }

    private interface ExtFunction<T, R> {
        /**
         * 对java.util.function的扩展，支持抛出IOException的情况
         *
         * @param t 入参
         * @return R 返回结构
         * @throws IOException http请求时返回的异常信息
         */
        R apply(T t) throws IOException;
    }
}
