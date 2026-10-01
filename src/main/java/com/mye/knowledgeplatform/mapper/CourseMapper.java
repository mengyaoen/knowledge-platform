package com.mye.knowledgeplatform.mapper;

import com.mye.knowledgeplatform.entity.Course;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper // 告诉SpringBoot这是一个操作数据库的接口
public interface CourseMapper {

    // 查询所有课程
    @Select("SELECT * FROM course")
    List<Course> findAll();

    // 新增课程
    @Insert("INSERT INTO course(name, description) VALUES(#{name}, #{description})")
    int insertCourse(Course course);
}