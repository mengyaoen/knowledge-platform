package com.mye.knowledgeplatform.mapper;

import com.mye.knowledgeplatform.entity.Knowledge;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface KnowledgeMapper {

    // 查询所有知识点（按时间倒序）
    @Select("SELECT * FROM knowledge ORDER BY create_time DESC")
    List<Knowledge> findAll();

    // 新增知识点
    @Insert("INSERT INTO knowledge(title, content) VALUES(#{title}, #{content})")
    int insertKnowledge(Knowledge knowledge);

    // 删除知识点
    @Delete("DELETE FROM knowledge WHERE id = #{id}")
    int deleteById(Integer id);
}