package com.mye.knowledgeplatform.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * JWT 工具类：负责生成 Token 和解析 Token
 */
@Component // 告诉 SpringBoot 这是一个工具类 Bean，可以在别处 @Autowired 注入
public class JwtUtil {

    // 1. 设置 JWT 签名密钥（必须是足够长的字符串，企业里会放在配置文件中）
    // 这里我们硬编码一个安全的密钥
    private static final String SECRET_KEY_STRING = "my_knowledge_platform_secret_key_must_be_long_enough_123456";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());

    // 2. 设置 Token 的有效期，比如 24 小时（单位：毫秒）
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000;

    /**
     * 生成 Token
     * @param userId 用户ID
     * @param username 用户名
     * @return 字符串 Token
     */
    public String generateToken(Integer userId, String username) {
        return Jwts.builder()
                .claim("userId", userId) // 自定义载荷：存入用户ID
                .claim("username", username) // 自定义载荷：存入用户名
                .setSubject(username) // 标准载荷：主题（通常是用户名）
                .setIssuedAt(new Date()) // 签发时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 过期时间
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // 签名算法，防止篡改
                .compact(); // 拼装成最终的 Token 字符串
    }

    /**
     * 解析 Token
     * @param token 前端传来的 Token
     * @return Claims 对象（包含 token 里的所有数据）
     */
    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY) // 设置签名密钥
                .build()
                .parseClaimsJws(token) // 解析 Token
                .getBody(); // 获取载荷（Payload）内容
    }
}