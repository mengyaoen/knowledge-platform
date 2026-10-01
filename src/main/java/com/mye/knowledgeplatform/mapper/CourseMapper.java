package com.mye.knowledgeplatform.mapper;

import com.mye.knowledgeplatform.entity.Course;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface CourseMapper {

    // 1. 查
    @Select("SELECT * FROM course")
    List<Course> findAll();

    // 2. 增
    @Insert("INSERT INTO course(name, description) VALUES(#{name}, #{description})")
    int insertCourse(Course course);

    // 3. 删（新增的）
    @Delete("DELETE FROM course WHERE id = #{id}")
    int deleteById(Integer id);

    // 4. 改（新增的）
    @Update("UPDATE course SET name=#{name}, description=#{description} WHERE id=#{id}")
    int updateCourse(Course course);
}