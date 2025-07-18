package com.demo.translation.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranslationExportDTO {
    private String content;
    private Set<String> tags;
}
