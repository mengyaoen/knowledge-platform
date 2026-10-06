package com.mye.knowledgeplatform.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Knowledge {
    private Integer id;
    private String title;       // 标题
    private String content;     // 内容
    private LocalDateTime createTime; // 创建时间（注意驼峰命名）
}