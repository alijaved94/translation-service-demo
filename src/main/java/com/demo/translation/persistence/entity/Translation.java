package com.demo.translation.persistence.entity;

import com.demo.translation.persistence.enums.Locale;
import com.demo.translation.persistence.enums.Tag;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Set;

@Entity
@Table(name = "translations",
    indexes = {
        @Index(name = "idx_locale", columnList = "locale")
    })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class Translation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Locale locale;

    @Column(nullable = false, length = 1000)
    private String content;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(
        name = "translation_tags",
        joinColumns = @JoinColumn(name = "translation_id"),
        indexes = @Index(name = "idx_translation_tags", columnList = "tag")
    )
    @Column(name = "tag")
    private Set<Tag> tags;
}
