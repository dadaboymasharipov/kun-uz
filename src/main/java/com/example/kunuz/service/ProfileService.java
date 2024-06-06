package com.example.kunuz.service;

import com.example.kunuz.dto.FilterResponseDTO;
import com.example.kunuz.dto.profile.ProfileCreateDTO;
import com.example.kunuz.dto.profile.ProfileDTO;
import com.example.kunuz.dto.profile.ProfileFilterDTO;
import com.example.kunuz.dto.profile.ProfileUpdateDTO;
import com.example.kunuz.entity.ProfileEntity;
import com.example.kunuz.exception.AppBadException;
import com.example.kunuz.repository.ProfileCustomRepository;
import com.example.kunuz.repository.ProfileRepository;
import com.example.kunuz.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileCustomRepository profileCustomRepository;

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
        entity.setPassword(MD5Util.getMD5(dto.getPassword()));
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

    public Boolean updateAny(String id, ProfileCreateDTO dto) {

        ProfileEntity entity = get(id);
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
//        entity.setEmail(dto.getEmail());//TODO: They need extra step verification
//        entity.setPhone(dto.getPhone());
        entity.setRole(dto.getRole());
//        entity.setPassword(MD5Util.getMD5(dto.getPassword()));
        entity.setStatus(dto.getStatus());
        profileRepository.save(entity);
        return true;
    }

    public Boolean updateCurrent(String id, ProfileUpdateDTO dto) {
        ProfileEntity entity = get(id);
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        profileRepository.save(entity);
        return true;
    }

    public Boolean delete(String id) {
        ProfileEntity entity = get(id);
        profileRepository.delete(entity);
        return true;
    }

    public PageImpl<ProfileDTO> filter(ProfileFilterDTO dto, Integer page, Integer size) {
        FilterResponseDTO<ProfileEntity> filterResponseDTO =
                profileCustomRepository.filter(dto, page, size);

        List<ProfileDTO> profileDTOList = new LinkedList<>();
        for (ProfileEntity profileEntity : filterResponseDTO.getContent()) {
            profileDTOList.add(mapToDto(profileEntity));
        }
        return new PageImpl<>(profileDTOList,
                PageRequest.of(page, size),
                filterResponseDTO.getTotalCount());

    }

}
