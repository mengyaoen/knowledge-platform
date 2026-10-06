package com.mye.knowledgeplatform.controller;

import com.mye.knowledgeplatform.common.Result;
import com.mye.knowledgeplatform.entity.Knowledge;
import com.mye.knowledgeplatform.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/knowledge")
@CrossOrigin
public class KnowledgeController {

    @Autowired
    private KnowledgeService knowledgeService;

    // 查询列表（需要 Token 才能访问）
    @GetMapping("/list")
    public Result<List<Knowledge>> list() {
        return Result.success("查询成功", knowledgeService.getAllKnowledge());
    }

    // 新增知识点（需要 Token）
    @PostMapping("/add")
    public Result<Void> add(@RequestBody Knowledge knowledge) {
        knowledgeService.addKnowledge(knowledge);
        return Result.success("新增成功");
    }

    // 删除知识点（需要 Token）
    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Integer id) {
        knowledgeService.deleteKnowledge(id);
        return Result.success("删除成功");
    }
}