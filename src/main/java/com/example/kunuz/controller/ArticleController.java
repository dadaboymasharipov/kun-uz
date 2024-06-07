package com.example.kunuz.controller;

import com.example.kunuz.dto.article.ArticleCreateDTO;
import com.example.kunuz.dto.article.ArticleDTO;
import com.example.kunuz.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PreAuthorize("hasRole('PUBLISHER')")
    @PutMapping("/status/{id}")
    public HttpEntity<Boolean> changeStatus(@PathVariable("id") String id,
                                            @RequestBody ArticleDTO articleDTO) {
        Boolean updated = articleService.changeStatus(id, articleDTO);
        return ResponseEntity.ok(updated);
    }
}
