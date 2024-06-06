package com.example.kunuz.controller;

import com.example.kunuz.dto.profile.ProfileCreateDTO;
import com.example.kunuz.dto.profile.ProfileDTO;
import com.example.kunuz.dto.profile.ProfileFilterDTO;
import com.example.kunuz.dto.profile.ProfileUpdateDTO;
import com.example.kunuz.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/adm/create")
    public ResponseEntity<ProfileDTO> create(@Valid @RequestBody ProfileCreateDTO dto) {
        ProfileDTO profileDTO = profileService.create(dto);
        return ResponseEntity.ok(profileDTO);
    }

    @GetMapping("/adm/pagination")
    public ResponseEntity<PageImpl<ProfileDTO>> getAllByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                             @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageImpl<ProfileDTO> profilePage = profileService.getAllByPage(page - 1, size);
        return ResponseEntity.ok(profilePage);
    }

    @PutMapping("/adm/update/{id}")
    public ResponseEntity<Boolean> updateForAdmin(@RequestBody @Valid ProfileCreateDTO dto,
                                                  @PathVariable("id") String id) {
        return ResponseEntity.ok(profileService.updateAny(id, dto));
    }

    @PutMapping("/current/{id}")
    public ResponseEntity<Boolean> updateUser(@Valid @RequestBody ProfileUpdateDTO profile,
                                              @PathVariable("id") String id) {
        return ResponseEntity.ok().body(profileService.updateCurrent(id, profile));
    }

    @DeleteMapping("/adm/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") String id) {
        return ResponseEntity.ok(profileService.delete(id));
    }

    @PostMapping("/filter")
    public ResponseEntity<PageImpl<ProfileDTO>> filter(@RequestBody ProfileFilterDTO dto,
                                                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageImpl<ProfileDTO> profilePage = profileService.filter(dto, page-1, size);
        return ResponseEntity.ok(profilePage);
    }


}
