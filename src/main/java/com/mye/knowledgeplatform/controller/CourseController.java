package com.mye.knowledgeplatform.controller;
import org.springframework.web.bind.annotation.*;
import com.mye.knowledgeplatform.entity.Course;
import com.mye.knowledgeplatform.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;

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
    // 删除课程
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        // 1. 接收受影响的行数
        int rows = courseMapper.deleteById(id);

        // 2. 判断是否真的删除了
        if (rows > 0) {
            return "删除成功";
        } else {
            return "删除失败：该课程不存在或已被删除";
        }
    }

    // 修改课程
    @PutMapping("/update")
    public String update(@RequestBody Course course) {
        courseMapper.updateCourse(course);
        return "修改成功";
    }
}