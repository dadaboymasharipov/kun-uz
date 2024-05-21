package com.example.kunuz.controller;

import com.example.kunuz.dto.CategoryCreateDTO;
import com.example.kunuz.dto.CategoryDTO;
import com.example.kunuz.enums.LanguageEnum;
import com.example.kunuz.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryCreateDTO category) {
        CategoryDTO response = categoryService.create(category);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDTO>> all() {
        return ResponseEntity.ok().body(categoryService.getAll());
    }

    @GetMapping("/lang")
    public ResponseEntity<List<CategoryDTO>> getAllByLang(
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") LanguageEnum lang) {
        List<CategoryDTO> regionDTOList = categoryService.getAllByLang(lang);
        return ResponseEntity.ok().body(regionDTOList);
    }

    @PutMapping("/update/{id}")
    public HttpEntity<Boolean> update(@PathVariable("id") Integer id,
                                      @RequestBody @Valid CategoryCreateDTO region) {
        return ResponseEntity.ok(categoryService.update(id, region));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(categoryService.delete(id));
    }

}

