package com.example.kunuz.controller;

import com.example.kunuz.dto.jwt.JwtDTO;
import com.example.kunuz.dto.profile.ProfileCreateDTO;
import com.example.kunuz.dto.profile.ProfileDTO;
import com.example.kunuz.dto.profile.ProfileFilterDTO;
import com.example.kunuz.service.ProfileService;
import com.example.kunuz.util.HttpRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.kunuz.enums.ProfileRole.ROLE_ADMIN;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/create")
    public ResponseEntity<ProfileDTO> create(@Valid @RequestBody ProfileCreateDTO dto,
                                             HttpServletRequest request) {
//        SecurityUtil.getJwtDTO(token, ROLE_ADMIN);
        HttpRequestUtil.getJwtDTO(request, ROLE_ADMIN);
        ProfileDTO profileDTO = profileService.create(dto);
        return ResponseEntity.ok(profileDTO);
    }

    @GetMapping("/pagination")
    public ResponseEntity<PageImpl<ProfileDTO>> getAllByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                             HttpServletRequest request) {
//        SecurityUtil.getJwtDTO(token, ROLE_ADMIN);
        HttpRequestUtil.getJwtDTO(request, ROLE_ADMIN);
        PageImpl<ProfileDTO> profilePage = profileService.getAllByPage(page - 1, size);
        return ResponseEntity.ok(profilePage);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> updateForAdmin(@RequestBody @Valid ProfileCreateDTO dto,
                                                  @PathVariable("id") String id,
                                                  HttpServletRequest request) {
//        SecurityUtil.getJwtDTO(token, ROLE_ADMIN);
        HttpRequestUtil.getJwtDTO(request, ROLE_ADMIN);
        return ResponseEntity.ok(profileService.update(id, dto));
    }

    @PutMapping("/current")
    public ResponseEntity<Boolean> updateUser(@Valid @RequestBody ProfileCreateDTO profile,
                                              HttpServletRequest request) {
//        SecurityUtil.getJwtDTO(token, ROLE_ADMIN);
        JwtDTO dto = HttpRequestUtil.getJwtDTO(request);//TODO: check the id with jwtDto.getId() for equality
        profileService.update(dto.getId(), profile);
        return ResponseEntity.ok().body(true);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") String id,
                                          HttpServletRequest request) {
//        SecurityUtil.getJwtDTO(token, ROLE_ADMIN);
        HttpRequestUtil.getJwtDTO(request, ROLE_ADMIN);
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
