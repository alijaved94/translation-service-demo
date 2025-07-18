package com.demo.translation;

import com.demo.translation.persistence.dto.TranslationExportDTO;
import com.demo.translation.persistence.entity.Translation;
import com.demo.translation.persistence.enums.Locale;
import com.demo.translation.persistence.enums.Tag;
import com.demo.translation.persistence.projection.TranslationView;
import com.demo.translation.persistence.repository.TranslationRepository;
import com.demo.translation.service.TranslationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TranslationServiceTest {

  private TranslationRepository translationRepository;
  private TranslationService translationService;

  @BeforeEach
  void setUp() {
    translationRepository = mock(TranslationRepository.class);
    translationService = new TranslationService(translationRepository);
  }

  @Test
  void createTranslation_ShouldSaveAndReturnTranslation() {
    // Given
    Translation translation = new Translation();
    when(translationRepository.save(translation)).thenReturn(translation);

    // When
    Translation result = translationService.createTranslation(translation);

    // Then
    assertEquals(translation, result);
    verify(translationRepository).save(translation);
  }

  @Test
  void updateTranslation_ShouldUpdateAndReturnTranslation() {
    // Given
    Long id = 1L;
    Translation existing = new Translation();
    existing.setId(id);
    Translation update = new Translation();
    update.setLocale(Locale.EN);
    update.setContent("Hello");
    update.setTags(Set.of(Tag.WEB));
    when(translationRepository.findById(id)).thenReturn(Optional.of(existing));
    when(translationRepository.save(existing)).thenReturn(existing);

    // When
    Translation result = translationService.updateTranslation(id, update);

    // Then
    assertEquals(existing, result);
    assertEquals(Locale.EN, existing.getLocale());
    assertEquals("Hello", existing.getContent());
    assertEquals(Set.of(Tag.WEB), existing.getTags());
    verify(translationRepository).findById(id);
    verify(translationRepository).save(existing);
  }

  @Test
  void updateTranslation_ShouldThrowIfNotFound() {
    // Given
    Long id = 2L;
    Translation update = new Translation();
    when(translationRepository.findById(id)).thenReturn(Optional.empty());

    // When / Then
    assertThrows(RuntimeException.class, () -> translationService.updateTranslation(id, update));
    verify(translationRepository).findById(id);
  }

  @Test
  void searchTranslations_ShouldReturnResults() {
    // Given
    Set<Long> keys = Set.of(1L, 2L);
    String content = "test";
    Set<Tag> tags = Set.of(Tag.DESKTOP);
    Set<TranslationView> expected = Set.of(mock(TranslationView.class));
    when(translationRepository.findTranslations(
            keys, "%test%", tags)).thenReturn(expected);

    // When
    Set<TranslationView> result = translationService.searchTranslations(keys, content, tags);

    // Then
    assertEquals(expected, result);
    verify(translationRepository).findTranslations(keys, "%test%", tags);
  }

  @Test
  void searchTranslations_ShouldThrowIfNoParams() {
    // Given
    Set<Long> keys = Collections.emptySet();
    String content = "";
    Set<Tag> tags = Collections.emptySet();

    // When / Then
    assertThrows(IllegalArgumentException.class, () ->
            translationService.searchTranslations(keys, content, tags));
  }

  @Test
  void exportTranslations_ShouldGroupByLocaleAndId() {
    // Given
    TranslationView view1 = mock(TranslationView.class);
    TranslationView view2 = mock(TranslationView.class);
    when(view1.getLocale()).thenReturn(Locale.EN);
    when(view1.getId()).thenReturn(1L);
    when(view1.getContent()).thenReturn("Hello");
    when(view1.getTags()).thenReturn(Set.of(Tag.WEB));
    when(view2.getLocale()).thenReturn(Locale.FR);
    when(view2.getId()).thenReturn(2L);
    when(view2.getContent()).thenReturn("Bonjour");
    when(view2.getTags()).thenReturn(Set.of(Tag.DESKTOP));
    Set<TranslationView> views = Set.of(view1, view2);
    when(translationRepository.findAllTranslations()).thenReturn(views);

    // When
    Map<String, Map<String, TranslationExportDTO>> result = translationService.exportTranslations();

    // Then
    assertTrue(result.containsKey("EN"));
    assertTrue(result.containsKey("FR"));
    assertEquals("Hello", result.get("EN").get("1").getContent());
    assertEquals("Bonjour", result.get("FR").get("2").getContent());
    verify(translationRepository).findAllTranslations();
  }
}
