package com.example.kunuz.service;

import com.example.kunuz.dto.RegionDTO;
import com.example.kunuz.dto.TypesCreateDTO;
import com.example.kunuz.dto.TypesDto;
import com.example.kunuz.entity.RegionEntity;
import com.example.kunuz.entity.TypesEntity;
import com.example.kunuz.enums.LanguageEnum;
import com.example.kunuz.exception.AppBadException;
import com.example.kunuz.mapper.TypeMapper;
import com.example.kunuz.repository.TypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class TypesService {

    @Autowired
    private TypesRepository typesRepository;

    public TypesDto create(TypesCreateDTO dto) {
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

    private TypesDto mapToDto(TypesEntity entity) {
        TypesDto dto = new TypesDto();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameEn(entity.getNameEn());
        dto.setNameRu(entity.getNameRu());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public List<TypesDto> getAll() {
        List<TypesEntity> types = typesRepository.findAll();
        List<TypesDto> result = new LinkedList<>();

        types.forEach(entity -> result.add(mapToDto(entity)));
        return result;
    }

    public List<TypesDto> getAllByLang(LanguageEnum lang) {
        List<TypeMapper> types = typesRepository.findAllByLang(lang.name());
        List<TypesDto> result = new LinkedList<>();
        for (TypeMapper type : types) {
            TypesDto typesDto = new TypesDto();
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
