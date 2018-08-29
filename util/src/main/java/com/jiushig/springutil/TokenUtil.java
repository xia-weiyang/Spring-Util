package com.jiushig.springutil;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zkzmz on 2017/8/17.
 */
public class TokenUtil {

    private static final Logger logger = LoggerFactory.getLogger(TokenUtil.class);

    private static long intervalTime;  // 间隔时间
    private static String issuer;

    private static Algorithm algorithm;

    public static final String KEY_USER_ID = "userId";
    public static final String KEY_TIME = "time";
    public static final String KEY_IP = "ip";

    /**
     * 初始化Token
     *
     * @param secret
     * @param time   过期时间 单位m
     * @param issuer
     */
    public static void init(String secret, int time, String issuer) {
        TokenUtil.issuer = issuer;
        TokenUtil.intervalTime = time * 1000 * 60;
        try {
            algorithm = Algorithm.HMAC256(secret);
        } catch (Exception e) {
            throw new RuntimeException("Token 初始化失败");
        }
    }

    /**
     * 根据id生成token
     *
     * @param userId
     * @return
     */
    public static String create(int userId) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(KEY_USER_ID, String.valueOf(userId));
        return create(hashMap);
    }

    /**
     * 根据map生成token
     *
     * @param stringMap
     * @return
     */
    public static String create(Map<String, String> stringMap) {
        JWTCreator.Builder builder = JWT.create()
                .withIssuer(issuer);
        builder.withClaim(KEY_TIME, System.currentTimeMillis() + intervalTime);
        if (stringMap != null)
            stringMap.forEach(builder::withClaim);
        return builder.sign(algorithm);
    }

    /**
     * 获取并解析token
     *
     * @return 可能为null
     */
    public static DecodedJWT getToken() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attributes == null){
            logger.warn("ServletRequestAttributes is null");
            return null;
        }
        return getToken(attributes.getRequest().getHeader("Authorization"));
    }

    public static DecodedJWT getToken(String token) {
        if (CommonUtil.isEmpty(token))
            return null;

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .build();
        DecodedJWT verify = verifier.verify(token);
        if (verify.getClaim(KEY_TIME).asLong() < System.currentTimeMillis()) {
            logger.warn("Token已过期 " + token);
            return null;
        }
        return verify;
    }

    /**
     * 通过Key从token中获取值
     *
     * @param jwt
     * @param key
     * @return
     */
    public static String getByKey(DecodedJWT jwt, String key) {
        if (jwt == null) return null;
        Claim claim = jwt.getClaim(key);
        if (claim.isNull()) return null;
        return claim.asString();
    }


    /**
     * 通过Key从token中获取值
     *
     * @param key
     * @return
     */
    public static String getByKey(String key) {
        return getByKey(getToken(), key);
    }

    /**
     * 通过token获取用户id
     *
     * @param unLogin 未登录时将被调用
     * @return 如果token无效/失效  返回0
     */
    public static int getUserId(VoidCallback unLogin) {
        String userId = getByKey(KEY_USER_ID);
        if (CommonUtil.isEmpty(userId)) {
            if (unLogin != null) unLogin.done();
            return 0;
        }
        return Integer.parseInt(userId);
    }


    /**
     * 获取用户id
     * 从token中获取
     *
     * @return
     */
    public static int getUserId() {
        return getUserId(null);
    }
}
