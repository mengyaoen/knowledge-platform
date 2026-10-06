package com.mye.knowledgeplatform.controller;

import com.mye.knowledgeplatform.common.Result;
import com.mye.knowledgeplatform.entity.User;
import com.mye.knowledgeplatform.mapper.UserMapper;
import com.mye.knowledgeplatform.utils.JwtUtil; // 导入 JwtUtil
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class LoginController {

    @Autowired
    private UserMapper userMapper;

    @Autowired // 注入 JwtUtil
    private JwtUtil jwtUtil;
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody User user) { // 返回值改为 Result<Map>
        // 1. 根据用户名查询用户
        User dbUser = userMapper.findByUsername(user.getUsername());
        if (dbUser == null) {
            return Result.error("用户不存在");
        }
        // 2. 校验密码
        if (!dbUser.getPassword().equals(user.getPassword())) {
            return Result.error("密码错误");
        }

        // 3. 账号密码正确，生成 JWT Token
        String token = jwtUtil.generateToken(dbUser.getId(), dbUser.getUsername());

        // 4. 打包返回数据（把 Token 和角色一起发给前端）
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);           // 身份令牌
        map.put("role", dbUser.getRole()); // 角色（admin 或 user）

        // 5. 返回统一响应体
        return Result.success("登录成功", map);
    }
    // ================= 注册接口 =================
    @PostMapping("/register")
    public Result<Void> register(@RequestBody User user) {
        // 1. 校验用户名是否已存在
        User existUser = userMapper.findByUsername(user.getUsername());
        if (existUser != null) {
            return Result.error("用户名已被注册，请更换");
        }
        // 2. 设置默认角色为普通学生 'user'
        user.setRole("user");

        // 【面试话术预留】实际开发中这里应该用 BCrypt 或 MD5 加密，我们目前暂存明文
        // 3. 写入数据库
        userMapper.insertUser(user);
        return Result.success("注册成功");
    }

    // ================= 修改密码接口 =================
    @PutMapping("/updatePassword")
    public Result<Void> updatePassword(@RequestBody Map<String, String> params, HttpServletRequest request) {
        // 1. 从拦截器存入的 request 域中直接拿 userId（安全！前端伪造不了）
        Integer userId = (Integer) request.getAttribute("userId");

        // 2. 获取前端传来的原密码和新密码
        String oldPassword = params.get("oldPassword");
        String newPassword = params.get("newPassword");

        // 3. 查数据库校验原密码是否正确
        User dbUser = userMapper.selectById(userId);
        if (dbUser == null || !dbUser.getPassword().equals(oldPassword)) {
            return Result.error("原密码错误！");
        }

        // 4. 校验通过，更新密码
        userMapper.updatePassword(userId, newPassword);
        return Result.success("密码修改成功，请重新登录！");
    }
    // ================= 忘记密码（重置密码）接口 =================
    @PostMapping("/resetPassword")
    public Result<Void> resetPassword(@RequestBody Map<String, String> params) {
        // 1. 获取前端传来的用户名和新密码
        String username = params.get("username");
        String newPassword = params.get("newPassword");

        // 2. 校验该用户名是否存在
        User dbUser = userMapper.findByUsername(username);
        if (dbUser == null) {
            return Result.error("该用户名不存在，请检查拼写");
        }

        // 3. 直接覆盖原密码（注意：实际生产环境必须加短信/邮箱验证码！）
        userMapper.updatePassword(dbUser.getId(), newPassword);
        return Result.success("密码重置成功，请登录");
    }

}
