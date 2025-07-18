package com.demo.translation.controller;

import com.demo.translation.persistence.dto.TranslationExportDTO;
import com.demo.translation.persistence.entity.Translation;
import com.demo.translation.persistence.enums.Locale;
import com.demo.translation.persistence.enums.Tag;
import com.demo.translation.persistence.projection.TranslationView;
import com.demo.translation.service.TranslationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

import static com.demo.common.util.TranslationUtil.parseKeys;
import static com.demo.common.util.TranslationUtil.parseTags;

@Slf4j
@RestController
@RequestMapping("/api/translations")
@RequiredArgsConstructor
public class TranslationController {
    private final TranslationService translationService;

    @PostMapping
    public ResponseEntity<Translation> createTranslation(@RequestBody Translation translation) {
        return ResponseEntity.ok(translationService.createTranslation(translation));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Translation> updateTranslation(
        @PathVariable Long id,
        @RequestBody Translation translation
    ) {
        return ResponseEntity.ok(translationService.updateTranslation(id, translation));
    }

    @GetMapping
    public ResponseEntity<Set<TranslationView>> searchTranslations(
        @RequestParam(required = false) String keys,
        @RequestParam(required = false) String content,
        @RequestParam(required = false) String tags

    ) {
        log.info("Request received to search translations with keys: {}, content: {}, tags: {}", keys, content, tags);
        return ResponseEntity.ok(
            translationService.searchTranslations(
                parseKeys( keys ),
                content,
                parseTags( tags )
            )
        );
    }

    @GetMapping("/export")
    public ResponseEntity<Map<String, Map<String, TranslationExportDTO>>> exportTranslations() {
        return ResponseEntity.ok()
            .header("Cache-Control", "public, max-age=3600")
            .body(translationService.exportTranslations());
    }
}
