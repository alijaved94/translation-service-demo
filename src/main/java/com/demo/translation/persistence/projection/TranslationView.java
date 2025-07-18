package com.demo.translation.persistence.projection;

import com.demo.translation.persistence.enums.Locale;
import com.demo.translation.persistence.enums.Tag;

import java.util.Set;

public interface TranslationView {
    Long getId();
    Locale getLocale();
    String getContent();
    Set<Tag> getTags();
}
