package com.example.kunuz.repository;

import com.example.kunuz.entity.TypesEntity;
import com.example.kunuz.mapper.TypeMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TypesRepository extends JpaRepository<TypesEntity, Integer> {

    @Query(value = "SELECT id," +
            " CASE :lang " +
            "     WHEN 'UZ' THEN name_uz " +
            "     WHEN 'RU' THEN name_ru " +
            "     WHEN 'EN' THEN name_en " +
            "     END as name " +
            " FROM type order by order_number desc;", nativeQuery = true)
    List<TypeMapper> findAllByLang(@Param("lang") String lang);
}
