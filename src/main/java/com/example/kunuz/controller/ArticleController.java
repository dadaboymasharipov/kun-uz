package com.example.kunuz.controller;

import com.example.kunuz.dto.article.ArticleCreateDTO;
import com.example.kunuz.dto.article.ArticleDTO;
import com.example.kunuz.dto.jwt.JwtDTO;
import com.example.kunuz.enums.ArticleStatus;
import com.example.kunuz.service.ArticleService;
import com.example.kunuz.util.HttpRequestUtil;
import com.example.kunuz.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.kunuz.enums.ProfileRole.ROLE_ADMIN;
import static com.example.kunuz.enums.ProfileRole.ROLE_MODERATOR;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/create")
    public HttpEntity<ArticleDTO> create(@RequestBody ArticleCreateDTO dto,
                               HttpServletRequest request) {
        JwtDTO jwtDTO = HttpRequestUtil.getJwtDTO(request, ROLE_MODERATOR);
        ArticleDTO articleDTO = articleService.create(dto, jwtDTO.getId());
        return ResponseEntity.ok(articleDTO);
    }

    @PutMapping("/update/{id}")
    public HttpEntity<Boolean> update(@RequestBody ArticleCreateDTO dto,
                                    @PathVariable("id") String id,
                                    HttpServletRequest request) {
        HttpRequestUtil.getJwtDTO(request, ROLE_MODERATOR);
        Boolean updated = articleService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public HttpEntity<Boolean> delete(@PathVariable String id,
                                      HttpServletRequest request) {
        HttpRequestUtil.getJwtDTO(request, ROLE_MODERATOR);
        Boolean updated = articleService.delete(id);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/status/{id}")
    public HttpEntity<Boolean> changeStatus(@PathVariable("id") String id,
                                      @RequestBody ArticleDTO articleDTO,
                                      HttpServletRequest request) {
        HttpRequestUtil.getJwtDTO(request, ROLE_MODERATOR);
        Boolean updated = articleService.changeStatus(id, articleDTO);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/getLastFive")
    public HttpEntity<Boolean> getLast5ArticleByType()

}
