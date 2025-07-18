package com.demo.common.config;

import com.demo.translation.persistence.entity.Translation;
import com.demo.translation.persistence.enums.Locale;
import com.demo.translation.persistence.enums.Tag;
import com.demo.translation.persistence.repository.TranslationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DataLoaderCommandLineRunner implements CommandLineRunner {
    private final TranslationRepository translationRepository;

    @Override
    public void run(String... args) {
//        List<Translation> translations = new ArrayList<>();
//        Random random = new Random();
//        Locale[] locales = Locale.values();
//        Tag[] tags = Tag.values();
//
//        for (int i = 0; i < 100_000; i++) {
//            Translation translation = new Translation();
//            translation.setLocale(locales[random.nextInt(locales.length)]);
//            translation.setContent("Content " + i);
//            translation.setTags(EnumSet.of(tags[random.nextInt(tags.length)]));
//            translations.add(translation);
//
//            if (translations.size() >= 1000) {
//                translationRepository.saveAll(translations);
//                translations.clear();
//            }
//        }
//
//        if (!translations.isEmpty()) {
//            translationRepository.saveAll(translations);
//        }
    }
}
