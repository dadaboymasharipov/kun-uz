package com.example.kunuz.service;

import com.example.kunuz.dto.article.ArticleCreateDTO;
import com.example.kunuz.dto.article.ArticleDTO;
import com.example.kunuz.entity.ArticleEntity;
import com.example.kunuz.entity.CategoryEntity;
import com.example.kunuz.entity.RegionEntity;
import com.example.kunuz.exception.AppBadException;
import com.example.kunuz.repository.*;
import com.example.kunuz.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.kunuz.enums.ArticleStatus.NOT_PUBLISHED;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ArticleTypesService articleTypesService;

    public ArticleDTO create(ArticleCreateDTO dto) {
        String moderatorId = SecurityUtil.getProfileId();
        //TODO: check if image exists
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setTitle(dto.getTitle());
        articleEntity.setDescription(dto.getDescription());
        articleEntity.setContent(dto.getContent());
        articleEntity.setSharedCount(0);
        articleEntity.setRegionId(dto.getRegionId());
        articleEntity.setCategoryId(dto.getCategoryId());
        articleEntity.setModeratorId(moderatorId);
        //TODO: setImage()
        articleEntity.setViewCount(0);

        articleRepository.save(articleEntity);
        articleTypesService.create(articleEntity.getId(), dto.getTypes());
        return mapToDto(articleEntity);
    }

    public Boolean update(String id, ArticleCreateDTO dto) {

        ArticleEntity articleEntity = get(id);
        articleEntity.setTitle(dto.getTitle());
        articleEntity.setContent(dto.getContent());
        articleEntity.setDescription(dto.getDescription());
        articleEntity.setSharedCount(dto.getSharedCount());
        // TODO: remove old image -> setImage()
        articleEntity.setRegionId(dto.getRegionId());
        articleEntity.setCategoryId(dto.getCategoryId());
        articleEntity.setStatus(NOT_PUBLISHED);

        articleRepository.save(articleEntity);
        articleTypesService.merge(articleEntity.getId(), dto.getTypes());

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
//        dto.setRegion(entity.getRegion());
//        dto.setCategory(entity.getCategory());
        //TODO: setModerator(), setPublisher(), setImage()
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setViewCount(entity.getViewCount());

        return dto;
    }

    public ArticleEntity get(String id) {
        return articleRepository.findByIdAndVisibleTrue(id)
                .orElseThrow(() -> new AppBadException("Article is not found"));
    }

}
