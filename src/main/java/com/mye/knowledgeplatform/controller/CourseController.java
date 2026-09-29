package com.mye.knowledgeplatform.controller;

import com.mye.knowledgeplatform.entity.Course;
import com.mye.knowledgeplatform.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/course") // 统一前缀
@CrossOrigin // 加上这一行，允许前端跨域访问
public class CourseController {

    @Autowired
    private CourseMapper courseMapper;

    @GetMapping("/list")
    public List<Course> list() {
        // 调用Mapper去数据库查数据
        return courseMapper.findAll();
    }
}