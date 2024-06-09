package com.example.kunuz.repository;

import com.example.kunuz.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, String> {
    Optional<ArticleEntity> findByIdAndVisibleTrue(String id);

    @Query(value = "select * from article as a" +
            " inner join article_types as at" +
            " on a.id = at.article_id where at.types_id=2" +
            " order by created_date desc limit 3", nativeQuery = true)
    List<ArticleEntity> getTop5ByTypesId(Integer typeId, Integer limit);

    @Query(value = "select * from article as a" +
            " inner join article_types as at on a.id = at.article_id" +
            " where at.types_id<>2 order by created_date desc limit 8;", nativeQuery = true)
    List<ArticleEntity> getTop8NotIncludeTypes(List<Integer> types);
}