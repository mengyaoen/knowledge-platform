package com.mye.knowledgeplatform.controller;

import com.mye.knowledgeplatform.common.Result;
import com.mye.knowledgeplatform.entity.Comment;
import com.mye.knowledgeplatform.mapper.CommentMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;

    // 查询评论列表
    @GetMapping("/list/{knowledgeId}")
    public Result<List<Comment>> list(@PathVariable Integer knowledgeId) {
        return Result.success("查询成功", commentMapper.findByKnowledgeId(knowledgeId));
    }

    // 新增评论
    @PostMapping("/add")
    public Result<Void> add(@RequestBody Comment comment, HttpServletRequest request) {
        // 从拦截器存入的 request 域中直接拿 userId（安全！前端伪造不了）
        Integer userId = (Integer) request.getAttribute("userId");
        comment.setUserId(userId);
        commentMapper.insertComment(comment);
        return Result.success("评论成功");
    }
}