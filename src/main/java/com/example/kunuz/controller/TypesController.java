package com.example.kunuz.controller;

import com.example.kunuz.dto.category.CategoryDTO;
import com.example.kunuz.dto.types.TypesCreateDTO;
import com.example.kunuz.enums.LanguageEnum;
import com.example.kunuz.service.TypesService;
import com.example.kunuz.util.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.kunuz.enums.ProfileRole.ROLE_ADMIN;
import static com.example.kunuz.enums.ProfileRole.ROLE_USER;

@RestController
@RequestMapping("/types")
public class TypesController {

    @Autowired
    private TypesService typesService;

    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody TypesCreateDTO typesCreateDTO,
                                              @RequestHeader("Authorization") String token) {
        SecurityUtil.getJwtDTO(token, ROLE_ADMIN);
        CategoryDTO type = typesService.create(typesCreateDTO);
        return ResponseEntity.ok(type);
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<CategoryDTO>> getAllByPage(@RequestParam("page") Integer page,
                                                          @RequestParam("size") Integer size,
                                                          @RequestHeader("Authorization") String token) {
        SecurityUtil.getJwtDTO(token, ROLE_ADMIN);
        Page<CategoryDTO> list = typesService.getAllByPagination(page, size);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/lang")
    public ResponseEntity<List<CategoryDTO>> getAllByLang(
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") LanguageEnum lang,
            @RequestHeader("Authorization") String token) {
        SecurityUtil.getJwtDTO(token, ROLE_USER);
        List<CategoryDTO> list = typesService.getAllByLang(lang);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id,
                                          @Valid @RequestBody TypesCreateDTO typesCreateDTO,
                                          @RequestHeader("Authorization") String token) {
        SecurityUtil.getJwtDTO(token, ROLE_ADMIN);
        Boolean result = typesService.update(id, typesCreateDTO);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id,
                                          @RequestHeader("Authorization") String token) {
        SecurityUtil.getJwtDTO(token, ROLE_ADMIN);
        return ResponseEntity.ok(typesService.delete(id));
    }

}
