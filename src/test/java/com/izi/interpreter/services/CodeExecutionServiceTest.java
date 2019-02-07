package com.izi.interpreter.services;

import com.izi.interpreter.dtos.History;
import com.izi.interpreter.dtos.LanguageDto;
import com.izi.interpreter.dtos.ResultDto;
import com.izi.interpreter.repositories.HistoryRepository;
import com.izi.interpreter.repositories.LanguageRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CodeExecutionServiceTest {

    @Mock
    private LanguageRepository languageRepository;
    @Mock
    private HistoryRepository historyRepository;

    @InjectMocks
    private CodeExecutionService codeExecutionService;

    private LanguageDto language = new LanguageDto("bash", "sh", "-c");
    private History history = new History("", "ID");
    private ResultDto result = new ResultDto("hello", "");
    private ResultDto error = new ResultDto("", "bash: ech: command not found");

    @Before
    public void setup() {
        when(languageRepository.findLanguageByName("bash")).thenReturn(language);
        when(historyRepository.findOne("ID")).thenReturn(history);
    }

    @Test
    public void executeCodeShouldReturnCorrectResult() {
        ResultDto resultDto = codeExecutionService.executeCode("bash", "echo hello", "ID");
        assertEquals(result, resultDto);
    }

    @Test
    public void executeCodeShouldReturnError() {
        ResultDto resultDto = codeExecutionService.executeCode("bash", "ech hello", "ID");
        assertEquals(error, resultDto);
    }
}
