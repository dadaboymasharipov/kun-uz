package com.example.kunuz.repository;

import com.example.kunuz.dto.FilterResponseDTO;
import com.example.kunuz.dto.profile.ProfileFilterDTO;
import com.example.kunuz.entity.ProfileEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProfileCustomRepository {

    @Autowired
    private EntityManager entityManager;

    public FilterResponseDTO<ProfileEntity> filter(ProfileFilterDTO dto, int page, int size) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder query = new StringBuilder();
        if (dto.getName() != null) {
            query.append(" and s.name=:name");
            params.put("name", dto.getName());
        }
        if (dto.getSurname() != null) {
            query.append(" and s.surname=:surname");
            params.put("surname", dto.getSurname());
        }
        if (dto.getPhone() != null) {
            query.append(" and s.phone=:phone");
            params.put("phone", dto.getPhone());
        }
        if (dto.getRole() != null) {
            query.append(" and s.role=:role");
            params.put("role", dto.getRole());
        }
        if (dto.getCreatedDateFrom() != null && dto.getCreatedDateTo() != null) {
            query.append(" and s.created_date between :created_date_from and :created_date_to");
            params.put("created_date_from", dto.getCreatedDateFrom());
            params.put("created_date_to", dto.getCreatedDateTo());
        }
        if (dto.getCreatedDateFrom() != null && dto.getCreatedDateTo() == null) {
            query.append(" and s.created_date=:created_date_from");
            params.put("created_date_from", dto.getCreatedDateFrom());
        }

        StringBuilder selectSql = new StringBuilder("From ProfileEntity s where s.visible = true");
        StringBuilder countSql = new StringBuilder("Select count(s) From ProfileEntity s where s.visible = true");

        selectSql.append(query);
        countSql.append(query);

        Query selectQuery = entityManager.createQuery(selectSql.toString());
        Query countQuery = entityManager.createQuery(countSql.toString());

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            selectQuery.setParameter(entry.getKey(), entry.getValue());
            countQuery.setParameter(entry.getKey(), entry.getValue());
        }

        selectQuery.setFirstResult(page * size);
        selectQuery.setMaxResults(size);

        List<ProfileEntity> profileList = selectQuery.getResultList();
        Long totalCount = (Long) countQuery.getSingleResult();
        return new FilterResponseDTO<>(profileList, totalCount);
    }
}
