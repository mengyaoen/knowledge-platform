package com.mye.knowledgeplatform.service;

import com.mye.knowledgeplatform.entity.Course;
import java.util.List;

/**
 * 课程业务逻辑接口层 (Service Layer)
 * 作用：定义业务契约。Controller 只依赖这个接口，不关心具体的实现细节。
 * 这是面向接口编程的核心思想，方便未来替换实现类（比如换成 Redis 缓存实现）。
 */
public interface CourseService {

    // 查询所有课程
    List<Course> getAllCourses();

    // 新增课程
    void addCourse(Course course);

    // 删除课程
    void deleteCourse(Integer id);

    // 修改课程
    void updateCourse(Course course);
}