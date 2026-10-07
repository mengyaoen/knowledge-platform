package com.mye.knowledgeplatform.controller;

import com.mye.knowledgeplatform.common.Result;
import com.mye.knowledgeplatform.entity.User;
import com.mye.knowledgeplatform.mapper.UserMapper;
import com.mye.knowledgeplatform.utils.JwtUtil; // 导入 JwtUtil
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
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
    @Autowired
    private StringRedisTemplate stringRedisTemplate; // 操作Redis的工具
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
        String username = params.get("username");
        String code = params.get("code"); // 前端传来的验证码
        String newPassword = params.get("newPassword");

        // 从Redis获取验证码
        String redisCode = stringRedisTemplate.opsForValue().get("code_" + username);

        if (redisCode == null) return Result.error("验证码已过期，请重新发送");
        if (!redisCode.equals(code)) return Result.error("验证码错误！");

        // 验证通过，修改密码
        User dbUser = userMapper.findByUsername(username);
        userMapper.updatePassword(dbUser.getId(), newPassword);

        // 【重要】删除Redis中的验证码，防止重复使用
        stringRedisTemplate.delete("code_" + username);

        return Result.success("密码重置成功，请登录");
    }
    // ================= 发送验证码接口 =================
    @PostMapping("/sendCode")
    public Result<Void> sendCode(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        User dbUser = userMapper.findByUsername(username);
        if (dbUser == null) return Result.error("该用户名不存在");

        // 生成6位随机数字
        String code = String.valueOf((int)((Math.random() * 9 + 1) * 100000));

        // 存入Redis，设置5分钟过期（核心考点！）
        stringRedisTemplate.opsForValue().set("code_" + username, code, 5, java.util.concurrent.TimeUnit.MINUTES);

        // 模拟发送短信：在IDEA控制台打印
        System.out.println("====== 短信验证码 ======");
        System.out.println("给用户 [" + username + "] 发送验证码：" + code);
        System.out.println("=======================");

        return Result.success("验证码已发送，请查看控制台");
    }

}
