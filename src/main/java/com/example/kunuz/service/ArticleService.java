package com.example.kunuz.service;

import com.example.kunuz.dto.article.ArticleCreateDTO;
import com.example.kunuz.dto.article.ArticleDTO;
import com.example.kunuz.entity.*;
import com.example.kunuz.enums.ArticleStatus;
import com.example.kunuz.exception.AppBadException;
import com.example.kunuz.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static com.example.kunuz.enums.ArticleStatus.NOT_PUBLISHED;
import static com.example.kunuz.enums.ArticleStatus.PUBLISHED;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TypesRepository typesRepository;
    @Autowired
    private ProfileRepository profileRepository;

    public ArticleDTO create(ArticleCreateDTO createDTO, String moderatorId) {
        RegionEntity regionEntity = regionRepository.findById(createDTO.getRegionId())
                .orElseThrow(() -> new AppBadException("Region is not found"));
        CategoryEntity categoryEntity = categoryRepository.findById(createDTO.getCategoryId())
                .orElseThrow(() -> new AppBadException("Category is not found"));
        ProfileEntity moderator = profileRepository.findById(moderatorId).get();
        //TODO: check if image exists
        List<TypesEntity> types = typesRepository.findAllByIdIsInAndVisibleTrue(Arrays.asList(createDTO.getTypes()));

        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setTitle(createDTO.getTitle());
        articleEntity.setDescription(createDTO.getDescription());
        articleEntity.setContent(createDTO.getContent());
        articleEntity.setSharedCount(0);
        articleEntity.setRegion(regionEntity);
        articleEntity.setCategory(categoryEntity);
        articleEntity.setModerator(moderator);
        //TODO: setPublisher(), setImage()
        articleEntity.setStatus(PUBLISHED);
        articleEntity.setViewCount(0);
        articleEntity.setTypes(types);

        articleRepository.save(articleEntity);
        return mapToDto(articleEntity);
    }

    public Boolean update(String id, ArticleCreateDTO dto) {

        RegionEntity regionEntity = regionRepository.findById(dto.getRegionId())
                .orElseThrow(() -> new AppBadException("Region is not found"));
        CategoryEntity categoryEntity = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new AppBadException("Category is not found"));


        ArticleEntity articleEntity = get(id);
        articleEntity.setTitle(dto.getTitle());
        articleEntity.setContent(dto.getContent());
        articleEntity.setDescription(dto.getDescription());
        articleEntity.setSharedCount(dto.getSharedCount());
        // TODO: remove old image -> setImage()
        articleEntity.setRegion(regionEntity);
        articleEntity.setCategory(categoryEntity);
        articleEntity.setStatus(NOT_PUBLISHED);

        articleRepository.save(articleEntity);
        return true;
    }

    public Boolean delete(String id) {
        ArticleEntity articleEntity = get(id);
        articleRepository.delete(articleEntity);
        return true;
    }

    public Boolean changeStatus(String id, ArticleDTO dto) {
        ArticleEntity articleEntity = get(id);
        articleEntity.setStatus(dto.getStatus());//TODO: check if it works with directly getting Article Status
        articleRepository.save(articleEntity);
        return true;
    }

    public ArticleDTO mapToDto(ArticleEntity entity) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setSharedCount(entity.getSharedCount());
        dto.setRegion(entity.getRegion());
        dto.setCategory(entity.getCategory());
        //TODO: setModerator(), setPublisher(), setImage()
        dto.setStatus(PUBLISHED);
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setViewCount(entity.getViewCount());
        dto.setTypes(entity.getTypes());

        return dto;
    }

    public ArticleEntity get(String id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new AppBadException("Article is not found"));
    }

}
