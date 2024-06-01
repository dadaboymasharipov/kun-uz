package com.example.kunuz.service;

import com.example.kunuz.dto.region.RegionCreateDTO;
import com.example.kunuz.dto.region.RegionDTO;
import com.example.kunuz.entity.RegionEntity;
import com.example.kunuz.enums.LanguageEnum;
import com.example.kunuz.exception.AppBadException;
import com.example.kunuz.mapper.RegionMapper;
import com.example.kunuz.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public RegionDTO create(RegionCreateDTO dto) {
        RegionEntity entity = new RegionEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());

        regionRepository.save(entity);

        return mapToDto(entity);
    }


    public List<RegionDTO> getAll() {
        Iterable<RegionEntity> entityList = regionRepository.findAll();
        List<RegionDTO> dtoList = new LinkedList<>();
        entityList.forEach((entity) -> dtoList.add(mapToDto(entity)));
        return dtoList;
    }

    public List<RegionDTO> getAllByLang(LanguageEnum lang) {
        Iterable<RegionMapper> iterable = regionRepository.findAll(lang.name());
        List<RegionDTO> dtoList = new LinkedList<>();
        for (RegionMapper entity : iterable) {
            RegionDTO dto = new RegionDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dtoList.add(dto);
        }
        return dtoList;
    }

    public Boolean update(Integer id, RegionCreateDTO region) {
        RegionEntity regionEntity = get(id);
        regionEntity.setOrderNumber(regionEntity.getOrderNumber());
        regionEntity.setNameUz(region.getNameUz());
        regionEntity.setNameEn(region.getNameEn());
        regionEntity.setNameRu(region.getNameRu());
        regionRepository.save(regionEntity);
        return true;
    }

    public Boolean delete(Integer id) {
        RegionEntity regionEntity = get(id);
        regionRepository.delete(regionEntity);
        return true;
    }

    private RegionEntity get(Integer id) {
        return regionRepository.findById(id).orElseThrow(
                () -> new AppBadException("Region is not found by id " + id));
    }

    private RegionDTO mapToDto(RegionEntity entity) {
        RegionDTO dto = new RegionDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameEn(entity.getNameEn());
        dto.setNameRu(entity.getNameRu());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }
}
