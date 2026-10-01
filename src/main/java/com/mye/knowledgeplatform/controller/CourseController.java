package com.mye.knowledgeplatform.controller;
import com.mye.knowledgeplatform.entity.Course;
import com.mye.knowledgeplatform.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@RestController
@RequestMapping("/api/course") // 统一前缀
@CrossOrigin // 允许前端跨域访问
public class CourseController {

    @Autowired
    private CourseMapper courseMapper;

    // 查询课程列表
    @GetMapping("/list")
    public List<Course> list() {
        return courseMapper.findAll();
    }

    // 新增课程
    @PostMapping("/add")
    public String add(@RequestBody Course course) {
        courseMapper.insertCourse(course);
        return "新增成功";
    }
}