package com.example.kunuz.repository;

import com.example.kunuz.dto.ProfileDTO;
import com.example.kunuz.dto.ProfileFilterDTO;
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

    public List<ProfileDTO> filter(ProfileFilterDTO dto) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder query = new StringBuilder("From ProfileEntity where visible = true ");
        if (dto.getName() != null) {
            query.append("and name=:name ");
            params.put("name", dto.getName());
        }
        if (dto.getSurname() != null) {
            query.append("and surname=:surname ");
            params.put("surname", dto.getSurname());
        }
        if (dto.getPhone() != null) {
            query.append("and phone=:phone ");
            params.put("phone", dto.getPhone());
        }
        if (dto.getRole() != null) {
            query.append("and role=:role ");
            params.put("role", dto.getRole());
        }
        if (dto.getCreatedDateFrom() != null && dto.getCreatedDateTo() != null) {
            query.append("and created_date between :created_date_from and :created_date_to ");
            params.put("created_date_from", dto.getCreatedDateFrom());
            params.put("created_date_to", dto.getCreatedDateTo());
        }
        if (dto.getCreatedDateFrom() != null && dto.getCreatedDateTo() == null) {
            query.append("and created_date=:created_date_from");
            params.put("created_date_from", dto.getCreatedDateFrom());
        }
        Query selectQuery = entityManager.createQuery(query.toString());
        for (Map.Entry<String, Object> entry: params.entrySet()) {
            selectQuery.setParameter(entry.getKey(), entry.getValue());
        }
        return null;
    }
}
