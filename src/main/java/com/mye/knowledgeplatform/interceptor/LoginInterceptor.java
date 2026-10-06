package com.mye.knowledgeplatform.interceptor;

import com.mye.knowledgeplatform.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import io.jsonwebtoken.Claims;

/**
 * 登录拦截器：拦截所有需要登录才能访问的接口
 */
@Component // 交给 Spring 管理
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 在请求到达 Controller 之前执行
     * @return true 表示放行，false 表示拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1. 如果是 OPTIONS 请求（预检请求），直接放行，否则跨域会报错
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        // 2. 从请求头（Header）中获取 Authorization 字段
        String authHeader = request.getHeader("Authorization");

        // 3. 检查请求头是否存在，并且是否以 "Bearer " 开头
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // 【重要修改】为了让前端 Axios 能统一处理，HTTP 状态码依然返回 200，但业务 code 返回 401
            response.setStatus(200);
            response.setContentType("application/json;charset=utf-8");
// 返回 JSON 字符串，里面的 code 为 401，前端通过判断 code 来决定是否跳转登录页
            response.getWriter().write("{\"code\": 401, \"msg\": \"未登录，请先登录！\"}");
            return false;
        }

        // 4. 截取真正的 Token（去掉前7个字符 "Bearer "）
        String token = authHeader.substring(7);

        // 5. 解析 Token，验证是否合法（如果不合法或过期，会抛出异常）
        try {
            Claims claims = jwtUtil.parseToken(token);
            // 为了后续方便获取用户信息，将解析出来的 userId 和 username 存入 request 域中
            request.setAttribute("userId", claims.get("userId"));
            request.setAttribute("username", claims.get("username"));
            return true; // Token 合法，放行
        } catch (Exception e) {
            // Token 过期或伪造
            response.setStatus(401);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("{\"code\": 401, \"msg\": \"Token无效或已过期！\"}");
            return false; // 拦截请求
        }
    }
}