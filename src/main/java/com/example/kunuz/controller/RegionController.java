package com.example.kunuz.controller;

import com.example.kunuz.dto.region.RegionCreateDTO;
import com.example.kunuz.dto.region.RegionDTO;
import com.example.kunuz.enums.LanguageEnum;
import com.example.kunuz.service.RegionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @PostMapping("/adm/create")
    public ResponseEntity<RegionDTO> create(@Valid @RequestBody RegionCreateDTO region) {
        RegionDTO response = regionService.create(region);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/adm/all")
    public ResponseEntity<List<RegionDTO>> all() {
        return ResponseEntity.ok().body(regionService.getAll());
    }

    @GetMapping("/lang")
    public ResponseEntity<List<RegionDTO>> getAllByLang(
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") LanguageEnum lang) {
        List<RegionDTO> regionDTOList = regionService.getAllByLang(lang);
        return ResponseEntity.ok().body(regionDTOList);
    }

    @PutMapping("/adm/update/{id}")
    public HttpEntity<Boolean> update(@PathVariable("id") Integer id,
                                      @RequestBody @Valid RegionCreateDTO region) {
        return ResponseEntity.ok(regionService.update(id, region));
    }

    @DeleteMapping("/adm/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(regionService.delete(id));
    }

}
