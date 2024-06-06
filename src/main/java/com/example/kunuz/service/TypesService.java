package com.example.kunuz.service;

import com.example.kunuz.dto.category.CategoryDTO;
import com.example.kunuz.dto.types.TypesCreateDTO;
import com.example.kunuz.entity.TypesEntity;
import com.example.kunuz.enums.LanguageEnum;
import com.example.kunuz.exception.AppBadException;
import com.example.kunuz.mapper.TypeMapper;
import com.example.kunuz.repository.TypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class TypesService {

    @Autowired
    private TypesRepository typesRepository;

    public CategoryDTO create(TypesCreateDTO dto) {
        TypesEntity entity = new TypesEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());

        typesRepository.save(entity);
        return mapToDto(entity);
    }

    private TypesEntity get(Integer id) {
        return typesRepository.findById(id).orElseThrow(
                () -> new AppBadException("Type is not found by id " + id));
    }

    private CategoryDTO mapToDto(TypesEntity entity) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameEn(entity.getNameEn());
        dto.setNameRu(entity.getNameRu());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public Page<CategoryDTO> getAllByPagination(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<TypesEntity> entities = typesRepository.findAll(pageable);
        List<CategoryDTO> result = new LinkedList<>();

        for (TypesEntity entity : entities.getContent()) {
            result.add(mapToDto(entity));
        }
        long totalElements = entities.getTotalElements();

        return new PageImpl<>(result, pageable, totalElements);
    }

    public List<CategoryDTO> getAllByLang(LanguageEnum lang) {
        List<TypeMapper> types = typesRepository.findAllByLang(lang.name());
        List<CategoryDTO> result = new LinkedList<>();
        for (TypeMapper type : types) {
            CategoryDTO typesDto = new CategoryDTO();
            typesDto.setId(type.getId());
            typesDto.setName(type.getName());
            result.add(typesDto);
        }
        return result;
    }


    public Boolean update(Integer id, TypesCreateDTO dto) {
        TypesEntity entity = get(id);
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        typesRepository.save(entity);
        return true;
    }

    public Boolean delete(Integer id) {
        TypesEntity entity = get(id);
        typesRepository.delete(entity);
        return true;
    }
}
