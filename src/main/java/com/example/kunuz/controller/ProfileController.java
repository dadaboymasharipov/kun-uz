package com.example.kunuz.controller;

import com.example.kunuz.dto.ProfileCreateDTO;
import com.example.kunuz.dto.ProfileDTO;
import com.example.kunuz.dto.ProfileFilterDTO;
import com.example.kunuz.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/create")
    public ResponseEntity<ProfileDTO> create(@Valid @RequestBody ProfileCreateDTO dto) {
        ProfileDTO profileDTO = profileService.create(dto);
        return ResponseEntity.ok(profileDTO);
    }

    @GetMapping("/pagination")
    public ResponseEntity<PageImpl<ProfileDTO>> getAllByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                             @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageImpl<ProfileDTO> profilePage = profileService.getAllByPage(page - 1, size);
        return ResponseEntity.ok(profilePage);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> update(@RequestBody @Valid ProfileCreateDTO dto,
                                          @PathVariable("id") String id) {
        return ResponseEntity.ok(profileService.update(id, dto));
    }

//    @PutMapping("/update")
//    public ResponseEntity<Boolean> update(@RequestBody @Valid ProfileCreateDTO dto,
//                                          @PathVariable("id") String id) {
//        return ResponseEntity.ok(profileService.update(id, dto));
//    }TODO: when security is added correct this method

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") String id) {
        return ResponseEntity.ok(profileService.delete(id));
    }

    @PostMapping("/filter")
    public ResponseEntity<PageImpl<ProfileDTO>> filter(@RequestBody ProfileFilterDTO dto,
                                                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageImpl<ProfileDTO> profilePage = profileService.filter(dto);
        return ResponseEntity.ok(profilePage);
    }



}
