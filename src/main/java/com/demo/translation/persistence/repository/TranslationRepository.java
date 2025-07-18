package com.demo.translation.persistence.repository;

import com.demo.translation.persistence.dto.TranslationExportDTO;
import com.demo.translation.persistence.entity.Translation;
import com.demo.translation.persistence.enums.Locale;
import com.demo.translation.persistence.enums.Tag;
import com.demo.translation.persistence.projection.TranslationView;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TranslationRepository extends JpaRepository<Translation, Long> {
    List<Translation> findByLocale(Locale locale);

    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")
    })
    @Query(
        "SELECT t.id AS id, t.locale AS locale, t.content AS content, t.tags AS tags " +
        "FROM Translation t " +
        "WHERE (:keys IS NULL OR t.id IN :keys) " +
        "AND (:tags IS NULL OR EXISTS (SELECT tag FROM t.tags tag WHERE tag IN :tags)) " +
        "AND (:content IS NULL OR t.content ILIKE :content)"
    )
    Set<TranslationView> findTranslations(Set<Long> keys, String content, Set<Tag> tags);

    @QueryHints({
            @QueryHint(name = "org.hibernate.cacheable", value = "true")
    })
    @Query("SELECT t.id AS id, t.locale AS locale, t.content AS content, t.tags AS tags " +
            "FROM Translation t")
    Set<TranslationView> findAllTranslations();

}
