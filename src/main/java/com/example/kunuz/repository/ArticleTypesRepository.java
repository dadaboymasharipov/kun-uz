package com.example.kunuz.repository;

import com.example.kunuz.entity.ArticleTypesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleTypesRepository extends CrudRepository<ArticleTypesEntity, Integer> {
}
