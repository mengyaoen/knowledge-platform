package com.mye.knowledgeplatform.service;

import com.mye.knowledgeplatform.entity.Knowledge;
import java.util.List;

public interface KnowledgeService {
    List<Knowledge> getAllKnowledge();
    void addKnowledge(Knowledge knowledge);
    void deleteKnowledge(Integer id);
}