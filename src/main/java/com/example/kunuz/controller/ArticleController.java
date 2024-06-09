package com.example.kunuz.controller;

import com.example.kunuz.dto.article.ArticleCreateDTO;
import com.example.kunuz.dto.article.ArticleDTO;
import com.example.kunuz.dto.article.ArticleShortInfo;
import com.example.kunuz.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping("/moderator")
    public HttpEntity<ArticleDTO> create(@RequestBody ArticleCreateDTO dto) {
        ArticleDTO articleDTO = articleService.create(dto);
        return ResponseEntity.ok(articleDTO);
    }

    @PreAuthorize("hasAnyRole('MODERATOR', 'PUBLISHER')")
    @PutMapping("/moderator/{id}")
    public HttpEntity<Boolean> update(@RequestBody ArticleCreateDTO dto,
                                      @PathVariable("id") String id) {
        Boolean updated = articleService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @DeleteMapping("/moderator/{id}")
    public HttpEntity<Boolean> delete(@PathVariable String id) {
        Boolean updated = articleService.delete(id);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasAnyRole('PUBLISHER', 'PUBLISHER')")
    @PutMapping("/status/{id}")
    public HttpEntity<Boolean> changeStatus(@PathVariable("id") String id,
                                            @RequestBody ArticleDTO articleDTO) {
        Boolean updated = articleService.changeStatus(id, articleDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/last5ByTypes/{typeId}")
    public ResponseEntity<List<ArticleShortInfo>> getLast5ByTypes(@PathVariable("typeId") Integer typeId) {
        List<ArticleShortInfo> result = articleService.getLastNArticleByTypes(typeId, 5);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/last3ByTypes/{typeId}")
    public ResponseEntity<List<ArticleShortInfo>> getLast3ByTypes(@PathVariable("typeId") Integer typeId) {
        List<ArticleShortInfo> result = articleService.getLastNArticleByTypes(typeId, 3);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/last8NotIncludeTypes")
    public ResponseEntity<List<ArticleShortInfo>> getLast8ByTypesNotIncluded(@RequestBody ArticleDTO dto) {
        List<ArticleShortInfo> result = articleService.getLast5ByTypesNotIncluded(dto);
        return ResponseEntity.ok(result);
    }



}
