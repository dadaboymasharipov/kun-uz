package com.example.kunuz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class FilterResponseDTO<T> {
    private List<T> content;
    private long totalCount;
}
