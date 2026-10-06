package com.mye.knowledgeplatform.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Comment {
    private Integer id;
    private Integer knowledgeId; // 知识点ID
    private Integer userId;      // 用户ID
    private String content;      // 评论内容
    private LocalDateTime createTime; // 评论时间

    // 【重点】这不是数据库字段，而是为了多表查询时，携带评论人的用户名给前端
    private String username;
}