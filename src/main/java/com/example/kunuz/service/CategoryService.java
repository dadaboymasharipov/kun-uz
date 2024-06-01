package com.example.kunuz.service;

import com.example.kunuz.dto.category.CategoryDTO;
import com.example.kunuz.dto.category.CategoryCreateDTO;
import com.example.kunuz.entity.CategoryEntity;
import com.example.kunuz.enums.LanguageEnum;
import com.example.kunuz.exception.AppBadException;
import com.example.kunuz.mapper.CategoryMapper;
import com.example.kunuz.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class CategoryService {


    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO create(CategoryCreateDTO dto) {
        CategoryEntity entity = new CategoryEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());

        categoryRepository.save(entity);
        return mapToDto(entity);
    }

    private CategoryEntity get(Integer id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new AppBadException("Category is not found by id " + id));
    }

    private CategoryDTO mapToDto(CategoryEntity entity) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameEn(entity.getNameEn());
        dto.setNameRu(entity.getNameRu());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public List<CategoryDTO> getAll() {
        List<CategoryEntity> types = categoryRepository.findAllOrderByOrderNumber();
        List<CategoryDTO> result = new LinkedList<>();

        types.forEach(entity -> result.add(mapToDto(entity)));
        return result;
    }

    public List<CategoryDTO> getAllByLang(LanguageEnum lang) {
        List<CategoryMapper> types = categoryRepository.findAllByLang(lang.name());
        List<CategoryDTO> result = new LinkedList<>();
        for (CategoryMapper type : types) {
            CategoryDTO typesDto = new CategoryDTO();
            typesDto.setId(type.getId());
            typesDto.setName(type.getName());
            result.add(typesDto);
        }
        return result;
    }


    public Boolean update(Integer id, CategoryCreateDTO dto) {
        CategoryEntity entity = get(id);
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        categoryRepository.save(entity);
        return true;
    }

    public Boolean delete(Integer id) {
        CategoryEntity entity = get(id);
        categoryRepository.delete(entity);
        return true;
    }
}
