package com.mye.knowledgeplatform.controller;

import com.mye.knowledgeplatform.common.Result;
import com.mye.knowledgeplatform.entity.Course;
import com.mye.knowledgeplatform.service.CourseService; // 注意：这里换成了 Service
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 课程控制层 (Controller Layer)
 * 作用：只负责接收前端请求、校验基本参数、调用 Service 层，并返回结果。
 * 保持 Controller 的“极度瘦身”，不写任何复杂的业务逻辑。
 */
@RestController
@RequestMapping("/api/course")
@CrossOrigin // 允许跨域
public class CourseController {

    // 注入 Service 层，而不是 Mapper 层
    @Autowired
    private CourseService courseService;
    // 查询列表
    @GetMapping("/list")
    public Result<List<Course>> list() {
        return Result.success("查询成功", courseService.getAllCourses());
    }
    // 将前端传来的 JSON 转换成 Course 对象，交给 Service 处理
    // 新增课程
    @PostMapping("/add")
    public Result<Void> add(@RequestBody Course course) {
        courseService.addCourse(course);
        return Result.success("新增成功");
    }// 从 URL 路径中提取 id，交给 Service 删除
    // 删除课程
    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Integer id) {
        courseService.deleteCourse(id);
        return Result.success("删除成功");
    }
    // 将前端传来的 JSON 转换成 Course 对象，交给 Service 修改
    // 修改课程
    @PutMapping("/update")
    public Result<Void> update(@RequestBody Course course) {
        courseService.updateCourse(course);
        return Result.success("修改成功");
    }
}