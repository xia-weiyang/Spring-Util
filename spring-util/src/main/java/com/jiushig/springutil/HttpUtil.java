package com.jiushig.springutil;

import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zkzmz on 2017/11/4.
 */
public class HttpUtil {

    private final static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static final RestTemplate restTemplate = new RestTemplate();

    /**
     * 获取请求头
     */
    private static HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        headers.add("Accpet-Encoding", "gzip");
        headers.add("Content-Encoding", "UTF-8");
        headers.add("Content-Type", "application/json; charset=UTF-8");
        return headers;
    }


    private static <T> T request(Builder builder, HttpMethod method, Class<T> tClass) {
        assert builder != null;
        assert builder.url != null;
        assert method != null;

        logger.info(LoggerBuilder.get("Request url").setDescribe(builder.url).build());
        logger.debug(LoggerBuilder.get("Request detail").setDescribe(builder).build());
        StringBuilder url = new StringBuilder(builder.url);
        if (builder.params != null) {
            url.append("?");
            builder.params.keySet().forEach(s -> url.append(s).append("=").append(builder.params.get(s)).append("&"));
        }
        ResponseEntity<T> response = restTemplate.exchange(url.toString(), method,
                new HttpEntity<>(builder.body, builder.headers), tClass);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new HttpException(String.valueOf(response.getStatusCode().value()) + "  " + builder.toString());
        }

        logger.info(LoggerBuilder.get("Response success").setDescribe(builder.url).build());
        return response.getBody();
    }

    private static String request(Builder builder, HttpMethod method) {
        String result = request(builder, method, String.class);
        if (result != null) {
            result = result.trim().replaceAll("\n", "");
        }
        logger.debug(LoggerBuilder.get("Response detail").setDescribe(result).build());
        return result;
    }

    /**
     * get请求
     * 请求失败时 抛出{@link HttpException}
     *
     * @param builder
     * @return
     */
    public static String doGet(Builder builder) {
        return request(builder, HttpMethod.GET);
    }

    /**
     * {@link #doGet(Builder)}
     *
     * @param builder
     * @param c
     * @param <T>     return T
     * @return
     */
    public static <T> T get(Builder builder, Class<T> c) {
        return result(doGet(builder), c);
    }

    /**
     * {@link #get(Builder, Class)}
     * If predicate is return true, This method will replay execute.
     *
     * @param builder
     * @param c
     * @param predicate
     * @param <T>
     * @return
     */
    public static <T> T get(Builder builder, Class<T> c, HttpPredicate<T> predicate) {
        T t = result(doGet(builder), c);
        if (predicate.predicate(t, builder)) {
            return result(doGet(builder), c);
        }
        return t;
    }

    public static <T> T get(Builder builder, TypeToken<T> typeToken, HttpPredicate<T> predicate) {
        T t = result(doGet(builder), typeToken);
        if (predicate.predicate(t, builder)) {
            return result(doGet(builder), typeToken);
        }
        return t;
    }

    public static <T> T get(Builder builder, TypeToken<T> typeToken) {
        return result(doGet(builder), typeToken);
    }

    public static Result get(Builder builder) {
        return get(builder, Result.class);
    }

    /**
     * 下载文件 get方法
     *
     * @param builder
     * @param filePathAndName 以/开头 下载文件路径,包含其文件名
     *                        其根目录为 {@link CommonUtil#getSystemPath()}
     */
    public static void download(Builder builder, String filePathAndName) {
        assert (!CommonUtil.isEmpty(filePathAndName));

        byte[] bytes = request(builder, HttpMethod.GET, byte[].class);
        if (bytes == null) throw new RuntimeException("Downloaded file is null.");

        ByteArrayInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = new ByteArrayInputStream(bytes);
            File file = CommonUtil.createFile(CommonUtil.getSystemPath() + filePathAndName);
            outputStream = new FileOutputStream(file);
            int len;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
                if (outputStream != null)
                    outputStream.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * post请求
     *
     * @param builder
     * @return
     */
    public static String doPost(Builder builder) {
        return request(builder, HttpMethod.POST);
    }

    /**
     * {@link #doPost(Builder)}
     *
     * @param builder
     * @param c
     * @param <T>
     * @return
     */
    public static <T> T post(Builder builder, Class<T> c) {
        return result(doPost(builder), c);
    }

    /**
     * {@link #post(Builder, Class)}
     * If predicate is return true, This method will replay execute.
     *
     * @param builder
     * @param c
     * @param predicate
     * @param <T>
     * @return
     */
    public static <T> T post(Builder builder, Class<T> c, HttpPredicate<T> predicate) {
        T t = result(doPost(builder), c);
        if (predicate.predicate(t, builder)) {
            return result(doPost(builder), c);
        }
        return t;
    }

    public static <T> T post(Builder builder, TypeToken<T> typeToken, HttpPredicate<T> predicate) {
        T t = result(doPost(builder), typeToken);
        if (predicate.predicate(t, builder)) {
            return result(doPost(builder), typeToken);
        }
        return t;
    }

    public static <T> T post(Builder builder, TypeToken<T> typeToken) {
        return result(doPost(builder), typeToken);
    }

    public static Result post(Builder builder) {
        return post(builder, Result.class);
    }

    private static <T> T result(String result, Class<T> c) {
        assert result != null;
        return GsonUtil.get().fromJson(result, c);
    }

    private static <T> T result(String result, TypeToken<T> typeToken) {
        assert result != null;
        assert typeToken != null;
        return GsonUtil.get().fromJson(result, typeToken.getType());
    }

    public static class Builder {
        // 请求头
        private HttpHeaders headers = getHttpHeaders();
        // 请求参数
        private Map<String, String> params;

        private String url;

        private String body;

        public Builder setHeaders(String name, String value) {
            headers.add(name, value);
            return this;
        }

        public Builder setParams(String name, Object value) {
            if (params == null) {
                params = new HashMap<>();
            }
            params.put(name, value.toString());
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url.startsWith("http") ? url : "http://localhost:" + ServerInfo.getPort() + url;
            return this;
        }

        public Builder setBody(Object body) {
            if (!(body instanceof String)) {
                this.body = GsonUtil.get().toJson(body);
            } else {
                this.body = (String) body;
            }
            return this;
        }

        public void refreshHttpHeaders() {
            this.headers = getHttpHeaders();
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "url='" + url + '\'' +
                    ", params=" + params +
                    ", body='" + body + '\'' +
                    ", headers=" + headers +
                    '}';
        }
    }

    /**
     * 请求异常
     */
    public static class HttpException extends RuntimeException {
        HttpException(String msg) {
            super(msg);
        }
    }
}
