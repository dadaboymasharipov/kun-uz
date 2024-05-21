package com.example.kunuz.controller;

import com.example.kunuz.dto.TypesCreateDTO;
import com.example.kunuz.dto.TypesDto;
import com.example.kunuz.enums.LanguageEnum;
import com.example.kunuz.service.TypesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/types")
public class TypesController {

    @Autowired
    private TypesService typesService;

    @PostMapping("/create")
    public ResponseEntity<TypesDto> create(@Valid @RequestBody TypesCreateDTO typesCreateDTO) {
        TypesDto type = typesService.create(typesCreateDTO);
        return ResponseEntity.ok(type);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<TypesDto>> getAll() {
        List<TypesDto> list = typesService.getAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/lang")
    public ResponseEntity<List<TypesDto>> getAllByLang(
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") LanguageEnum lang) {
        List<TypesDto> list = typesService.getAllByLang(lang);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id,
            @Valid @RequestBody TypesCreateDTO typesCreateDTO) {
        Boolean result = typesService.update(id, typesCreateDTO);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(typesService.delete(id));
    }

}
