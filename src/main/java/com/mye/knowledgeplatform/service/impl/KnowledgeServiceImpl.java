package com.mye.knowledgeplatform.service.impl;

import com.mye.knowledgeplatform.entity.Knowledge;
import com.mye.knowledgeplatform.mapper.KnowledgeMapper;
import com.mye.knowledgeplatform.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class KnowledgeServiceImpl implements KnowledgeService {

    @Autowired
    private KnowledgeMapper knowledgeMapper;

    @Override
    public List<Knowledge> getAllKnowledge() {
        return knowledgeMapper.findAll();
    }

    @Override
    public void addKnowledge(Knowledge knowledge) {
        knowledgeMapper.insertKnowledge(knowledge);
    }

    @Override
    public void deleteKnowledge(Integer id) {
        knowledgeMapper.deleteById(id);
    }
}