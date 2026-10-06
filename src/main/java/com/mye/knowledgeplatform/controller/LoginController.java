package com.mye.knowledgeplatform.controller;

import com.mye.knowledgeplatform.common.Result;
import com.mye.knowledgeplatform.entity.User;
import com.mye.knowledgeplatform.mapper.UserMapper;
import com.mye.knowledgeplatform.utils.JwtUtil; // 导入 JwtUtil
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class LoginController {

    @Autowired
    private UserMapper userMapper;

    @Autowired // 注入 JwtUtil
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Result<String> login(@RequestBody User user) {
        User dbUser = userMapper.findByUsername(user.getUsername());
        if (dbUser == null) {
            return Result.error("用户不存在");
        }
        if (!dbUser.getPassword().equals(user.getPassword())) {
            return Result.error("密码错误");
        }

        // 【核心修改】账号密码正确，调用工具类生成真正的 JWT Token
        String token = jwtUtil.generateToken(dbUser.getId(), dbUser.getUsername());

        // 返回真 Token
        return Result.success("登录成功", token);
    }
}