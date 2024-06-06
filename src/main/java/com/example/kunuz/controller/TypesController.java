package com.example.kunuz.controller;

import com.example.kunuz.dto.category.CategoryDTO;
import com.example.kunuz.dto.types.TypesCreateDTO;
import com.example.kunuz.enums.LanguageEnum;
import com.example.kunuz.service.TypesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/types")
public class TypesController {

    @Autowired
    private TypesService typesService;

    @PostMapping("/adm/create")
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody TypesCreateDTO typesCreateDTO) {
        CategoryDTO type = typesService.create(typesCreateDTO);
        return ResponseEntity.ok(type);
    }

    @GetMapping("/adm/getAll")
    public ResponseEntity<Page<CategoryDTO>> getAllByPage(@RequestParam("page") Integer page,
                                                          @RequestParam("size") Integer size) {
        Page<CategoryDTO> list = typesService.getAllByPagination(page - 1, size);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/lang")
    public ResponseEntity<List<CategoryDTO>> getAllByLang(
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") LanguageEnum lang) {
        List<CategoryDTO> list = typesService.getAllByLang(lang);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/adm/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id,
                                          @Valid @RequestBody TypesCreateDTO typesCreateDTO) {
        Boolean result = typesService.update(id, typesCreateDTO);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/adm/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(typesService.delete(id));
    }

}
