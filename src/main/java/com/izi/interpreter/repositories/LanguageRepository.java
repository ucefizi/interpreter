package com.izi.interpreter.repositories;

import com.izi.interpreter.dtos.LanguageDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties("interpreter")
@Data
@NoArgsConstructor
public class LanguageRepository {
    private Map<String, LanguageDto> languages;

    public LanguageDto findLanguageByName(String languageName) {
        return languages.get(languageName);
    }
}
