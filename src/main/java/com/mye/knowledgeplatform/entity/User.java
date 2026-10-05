package com.mye.knowledgeplatform.entity;

import lombok.Data;

/**
 * 用户实体类，对应数据库的 user 表
 */
@Data // Lombok注解：自动生成 getter、setter、toString 等方法，省去手写
public class User {
    private Integer id;        // 用户ID（主键）
    private String username;   // 用户名
    private String password;   // 密码（注意：测试用明文，以后要加密）
}