package com.example.kunuz.service;

import com.example.kunuz.dto.ProfileCreateDTO;
import com.example.kunuz.dto.ProfileDTO;
import com.example.kunuz.dto.ProfileFilterDTO;
import com.example.kunuz.entity.ProfileEntity;
import com.example.kunuz.exception.AppBadException;
import com.example.kunuz.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    //TODO: receive photo here and save it with user
    public ProfileDTO create(ProfileCreateDTO dto) {
//        if (dto.getRole().equals(ProfileRole.ROLE_ADMIN)) {
//            throw new AppBadException("Admins cannot be created");
//        }TODO: check if fields appropriate
        if (profileRepository.existsByEmailAndVisibleTrue(dto.getEmail())) {
            throw new AppBadException("This email already exist");
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setRole(dto.getRole());
        entity.setPassword(dto.getPassword());
//        entity.setPhoto(dto.getPhoto());/TODO: include photo
        entity.setStatus(dto.getStatus());
        profileRepository.save(entity);
        return mapToDto(entity);

    }

    private ProfileDTO mapToDto(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setRole(entity.getRole());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setPhoto(entity.getPhoto());
        return dto;
    }

    public ProfileEntity get(String id) {
        return profileRepository.findById(id).orElseThrow(
                () -> new AppBadException("Profile is not found by id " + id));
    }

    public PageImpl<ProfileDTO> getAllByPage(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<ProfileEntity> pageObj = profileRepository.findAll(pageable);
        List<ProfileDTO> profileList = new LinkedList<>();
        for (ProfileEntity entity : pageObj) {
            profileList.add(mapToDto(entity));
        }

        long totalCount = pageObj.getTotalElements();

        return new PageImpl<>(profileList, pageable, totalCount);
    }

    public Boolean update(String id, ProfileCreateDTO dto) {

        if (profileRepository.existsByEmailAndVisibleTrue(dto.getEmail())){
            throw new AppBadException("This email is already registered");
        }

        ProfileEntity entity = get(id);
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setRole(dto.getRole());
        entity.setPassword(dto.getPassword());
        entity.setStatus(dto.getStatus());
        profileRepository.save(entity);
        return true;
    }

    public Boolean delete(String id) {
        ProfileEntity entity = get(id);
        profileRepository.delete(entity);
        return true;
    }

    public PageImpl<ProfileDTO> filter(ProfileFilterDTO dto) {
        return null;
    }
}
