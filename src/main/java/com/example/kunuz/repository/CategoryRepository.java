package com.example.kunuz.repository;

import com.example.kunuz.entity.CategoryEntity;
import com.example.kunuz.mapper.CategoryMapper;
import com.example.kunuz.mapper.TypeMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    @Query(value = "SELECT id," +
            " CASE :lang " +
            "     WHEN 'UZ' THEN name_uz " +
            "     WHEN 'RU' THEN name_ru " +
            "     WHEN 'EN' THEN name_en " +
            "     END as name " +
            " FROM category order by order_number desc;", nativeQuery = true)
    List<CategoryMapper> findAllByLang(@Param("lang") String lang);

    @Query(value = "from CategoryEntity order by orderNumber")
    List<CategoryEntity> findAllOrderByOrderNumber();
}
