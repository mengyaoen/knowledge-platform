package com.mye.knowledgeplatform.mapper;

import com.mye.knowledgeplatform.entity.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {

    // 查询某个知识点下的所有评论（包含评论人用户名）
    // 使用了多表 JOIN：c代表comment表，u代表user表
    @Select("SELECT c.*, u.username FROM comment c JOIN user u ON c.user_id = u.id WHERE c.knowledge_id = #{knowledgeId} ORDER BY c.create_time DESC")
    List<Comment> findByKnowledgeId(Integer knowledgeId);

    // 插入评论
    @Insert("INSERT INTO comment(knowledge_id, user_id, content) VALUES(#{knowledgeId}, #{userId}, #{content})")
    int insertComment(Comment comment);
}