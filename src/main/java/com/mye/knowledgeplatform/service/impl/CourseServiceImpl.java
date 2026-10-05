package com.mye.knowledgeplatform.service.impl;

import com.mye.knowledgeplatform.entity.Course;
import com.mye.knowledgeplatform.mapper.CourseMapper;
import com.mye.knowledgeplatform.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 课程业务逻辑实现类
 * @Service 注解：告诉 SpringBoot 这是一个业务层的 Bean，交由 Spring 容器管理。
 */
@Service
public class CourseServiceImpl implements CourseService {

    /**
     * @Autowired 依赖注入：
     * Spring 会自动把 CourseMapper 的代理对象注入进来，我们无需手动 new。
     */
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> getAllCourses() {
        // 目前只是简单调用 Mapper，后续可以在这里加 Redis 缓存逻辑
        return courseMapper.findAll();
    }

    @Override
    public void addCourse(Course course) {
        // 【企业级预留】：以后可以在这里加业务校验，比如：检查课程名是否重复、敏感词过滤等
        // if (courseMapper.findByName(course.getName()) != null) {
        //     throw new RuntimeException("课程名已存在");
        // }
        courseMapper.insertCourse(course);
    }

    @Override
    public void deleteCourse(Integer id) {
        // 【企业级预留】：可以在这里加判断，比如：如果课程下有知识点，不允许删除
        courseMapper.deleteById(id);
    }

    @Override
    public void updateCourse(Course course) {
        courseMapper.updateCourse(course);
    }
}