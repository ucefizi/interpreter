package com.izi.interpreter.repositories;

import com.izi.interpreter.dtos.LanguageDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LanguageRepositoryTest {

    @Autowired
    private LanguageRepository languageRepository;

    private LanguageDto language = new LanguageDto("bash", "sh", "-c");

    @Test
    public void findLanguageByNameShouldReturnCorrectLanguage() {
        LanguageDto result = languageRepository.findLanguageByName("bash");
        assertEquals(language, result);
    }

    @Test
    public void findLanguageByNameShouldReturnNullIfLanguageDoesntExist() {
        LanguageDto result = languageRepository.findLanguageByName("php");
        assertNull(result);
    }

}
