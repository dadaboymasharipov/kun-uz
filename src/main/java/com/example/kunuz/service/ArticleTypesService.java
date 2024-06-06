package com.example.kunuz.service;

import com.example.kunuz.entity.ArticleTypesEntity;
import com.example.kunuz.repository.ArticleTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleTypesService {

    @Autowired
    private ArticleTypesRepository articleTypesRepository;


    public void create(String articleId, List<Integer> typesList) {
        typesList.forEach(typeId -> create(articleId, typeId));
    }

    public void create(String articleId, Integer typesId) {
        ArticleTypesEntity entity = new ArticleTypesEntity();
        entity.setArticleId(articleId);
        entity.setTypesId(typesId);
        articleTypesRepository.save(entity);
    }


}
