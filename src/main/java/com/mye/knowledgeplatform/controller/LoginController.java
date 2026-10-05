package com.mye.knowledgeplatform.controller;

import com.mye.knowledgeplatform.common.Result;
import com.mye.knowledgeplatform.entity.User;
import com.mye.knowledgeplatform.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 登录/用户控制层
 */
@RestController // 声明这是一个 Controller，返回的内容自动转为 JSON
@RequestMapping("/api/user") // 统一接口前缀，以后访问都要带上 /api/user
@CrossOrigin // 允许前端跨域访问（解决5173和8080端口跨域问题）
public class LoginController {

    @Autowired // 依赖注入：Spring自动把 UserMapper 对象塞进来
    private UserMapper userMapper;

    /**
     * 登录接口
     * 请求方式：POST
     * 请求URL：http://localhost:8080/api/user/login
     * 请求参数：JSON格式的 username 和 password
     */
    @PostMapping("/login")
    public Result<String> login(@RequestBody User user) { // @RequestBody 把前端JSON转成User对象

        // 1. 拿前端传来的用户名去数据库查
        User dbUser = userMapper.findByUsername(user.getUsername());

        // 2. 如果数据库里没这个人，返回错误提示
        if (dbUser == null) {
            return Result.error("用户不存在");
        }

        // 3. 如果查到了，比对密码是否一致（注意！目前是明文比对，企业里要用加密后的比对）
        if (!dbUser.getPassword().equals(user.getPassword())) {
            return Result.error("密码错误");
        }

        // 4. 账号密码都对，登录成功，返回一个临时Token（明天引入JWT后替换为真实Token）
        return Result.success("登录成功（明日替换为JWT Token）", "fake_token_" + dbUser.getId());
    }
}