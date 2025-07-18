package com.demo.translation.service;

import com.demo.translation.persistence.dto.TranslationExportDTO;
import com.demo.translation.persistence.entity.Translation;
import com.demo.translation.persistence.enums.Tag;
import com.demo.translation.persistence.projection.TranslationView;
import com.demo.translation.persistence.repository.TranslationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TranslationService {
    private final TranslationRepository translationRepository;

    /**
     * Creates a new translation in the database.
     *
     * @param translation the Translation entity to be created
     * @return the created Translation entity
     */
    @Transactional
    public Translation createTranslation(Translation translation) {
        return translationRepository.save(translation);
    }

    /**
     * Updates an existing translation in the database.
     *
     * @param id the ID of the translation to be updated
     * @param update the Translation entity containing updated values
     * @return the updated Translation entity
     */
    @Transactional
    public Translation updateTranslation(Long id, Translation update) {
        Translation translation = translationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Translation not found"));
        translation.setLocale(update.getLocale());
        translation.setContent(update.getContent());
        translation.setTags(update.getTags());

        return translationRepository.save(translation);
    }

    /**
     * Searches for translations based on content, tag, and locale.
     *
     * @param keys the IDs of the translation (optional)
     * @param content the content to search for (optional)
     * @param tags the tags to filter by (optional)
     * @return a set of TranslationView objects matching the search criteria
     */
    public Set<TranslationView> searchTranslations(
        Set<Long> keys,
        String content,
        Set<Tag> tags
    ) {
        validateSearchParams( keys, content, tags );

        return translationRepository.findTranslations(
            keys,
            prepareSearchContent( content ),
            tags
        );
    }

    /**
     * Exports all translations grouped by locale.
     *
     * @return a map where the key is the locale name and the value is another map
     *         with translation IDs as keys and TranslationExportDTO as values
     */
    public Map<String, Map<String, TranslationExportDTO>> exportTranslations() {
        Set<TranslationView> translations = translationRepository.findAllTranslations();

        return translations.parallelStream()
            .collect(Collectors.groupingBy(
                translation -> translation.getLocale().name(),
                Collectors.toMap(
                    translation -> translation.getId().toString(),
                    translation -> new TranslationExportDTO(
                        translation.getContent(),
                        translation.getTags().stream()
                            .map(Enum::name)
                            .collect(Collectors.toSet())
                    )
                )
            ));
    }

    /**
     * Validates the search parameters to ensure at least one parameter is provided.
     *
     * @param keys the IDs of the translation (optional)
     * @param content the content to search for (optional)
     * @param tags the tags to filter by (optional)
     */
    private void validateSearchParams(Set<Long> keys, String content, Set<Tag> tags) {
        if ((keys == null || keys.isEmpty()) && (content == null || content.isBlank()) && (tags == null || tags.isEmpty())) {
            throw new IllegalArgumentException("At least one search parameter must be provided: id, content, or tags.");
        }
    }

    /**
     * Prepares the content for a case-insensitive search by wrapping it with wildcards.
     *
     * @param content the content to prepare
     * @return the prepared content with wildcards, or null if the input is null
     */
    private String prepareSearchContent(String content) {
        return Optional.ofNullable(content)
                .map(c -> "%" + c + "%")
                .orElse(null);
    }
}
