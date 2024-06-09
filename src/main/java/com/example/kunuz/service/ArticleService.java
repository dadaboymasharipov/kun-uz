package com.example.kunuz.service;

import com.example.kunuz.dto.article.ArticleCreateDTO;
import com.example.kunuz.dto.article.ArticleDTO;
import com.example.kunuz.dto.article.ArticleShortInfo;
import com.example.kunuz.entity.ArticleEntity;
import com.example.kunuz.exception.AppBadException;
import com.example.kunuz.repository.ArticleRepository;
import com.example.kunuz.repository.CategoryRepository;
import com.example.kunuz.repository.RegionRepository;
import com.example.kunuz.repository.TypesRepository;
import com.example.kunuz.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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
    @Autowired
    private TypesRepository typesRepository;

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
//        articleEntity.setImageId(dto.getImageId());
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
//        articleEntity.setImageId(dto.getImageId());
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
        Objects.nonNull(dto.getStatus());
        articleEntity.setStatus(dto.getStatus());//TODO: check if it works with directly getting Article Status
        articleRepository.save(articleEntity);
        return true;
    }

    public List<ArticleShortInfo> getLastNArticleByTypes(Integer typeId, int limit) {

        if (!typesRepository.existsById(typeId)) {
            throw new AppBadException("Types with id: " + typeId + " is not found");
        }

        List<ArticleEntity> top5ByTypesId = articleRepository.getTop5ByTypesId(typeId, limit);
        List<ArticleShortInfo> result = new LinkedList<>();

        top5ByTypesId.forEach(entity -> result.add(mapToShortDto(entity)));

        return result;
    }

    public List<ArticleShortInfo> getLast5ByTypesNotIncluded(ArticleDTO dto) {
        List<ArticleEntity> top8Articles = articleRepository.getTop8NotIncludeTypes(dto.getTypes());

        List<ArticleShortInfo> result = new LinkedList<>();
        top8Articles.forEach(entity -> result.add(mapToShortDto(entity)));

        return result;
    }

    public ArticleDTO mapToDto(ArticleEntity entity) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setSharedCount(entity.getSharedCount());
        dto.setPublishedDate(entity.getPublishedDate());
//        dto.setRegion(entity.getRegion());
//        dto.setCategory(entity.getCategory());
        //TODO: setModerator(), setPublisher(), setImage()
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setViewCount(entity.getViewCount());

        return dto;
    }

    public ArticleShortInfo mapToShortDto(ArticleEntity entity) {
        ArticleShortInfo dto = new ArticleShortInfo();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setPublishDate(entity.getPublishedDate());
//        dto.setImage(entity.getImage());
//        TODO: setImageId()
        return dto;
    }

    public ArticleEntity get(String id) {
        return articleRepository.findByIdAndVisibleTrue(id)
                .orElseThrow(() -> new AppBadException("Article is not found"));
    }


}
