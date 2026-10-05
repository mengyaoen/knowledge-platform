package com.mye.knowledgeplatform.mapper;

import com.mye.knowledgeplatform.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 用户数据库操作接口
 */
@Mapper // 告诉SpringBoot这是操作数据库的接口，自动生成实现类
public interface UserMapper {

    // 根据用户名查询用户信息
    // #{} 是预编译，防止SQL注入；SQL 对应 MySQL 里的 user 表
    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(String username);
}