package com.example.kunuz.mapper;

import com.example.kunuz.dto.RegionDTO;
import com.example.kunuz.entity.RegionEntity;

import java.util.function.Function;

public class MapRegionEntityToRegionDto implements Function<RegionEntity, RegionDTO> {
    @Override
    public RegionDTO apply(RegionEntity entity) {
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
