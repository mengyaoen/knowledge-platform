package com.mye.knowledgeplatform.mapper;

import com.mye.knowledgeplatform.entity.User;
import org.apache.ibatis.annotations.*;

/**
 * 用户数据库操作接口
 */
@Mapper // 告诉SpringBoot这是操作数据库的接口，自动生成实现类
public interface UserMapper {

    // 根据用户名查询用户信息
    // #{} 是预编译，防止SQL注入；SQL 对应 MySQL 里的 user 表
    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(String username);
    // 1. 注册（新增用户）
    @Insert("INSERT INTO user(username, password, role) VALUES(#{username}, #{password}, #{role})")
    int insertUser(User user);

    // 2. 根据ID查询用户（为了修改密码时校验原密码）
    @Select("SELECT * FROM user WHERE id = #{id}")
    User selectById(Integer id);

    // 3. 修改密码
    @Update("UPDATE user SET password = #{password} WHERE id = #{id}")
    int updatePassword(@Param("id") Integer id, @Param("password") String password);

}