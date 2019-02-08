package com.izi.interpreter.services;

import com.izi.interpreter.dtos.History;
import com.izi.interpreter.dtos.ResultDto;
import com.izi.interpreter.repositories.HistoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CodeExecutionServiceIntegrationTest {

    @Autowired
    private CodeExecutionService codeExecutionService;

    @Autowired
    private HistoryRepository historyRepository;

    private ResultDto resultDto;
    private History history;

    @Before
    public void setup() {
        resultDto = new ResultDto("hello", "");
        history = new History("", "ID");
        historyRepository.save(history);
    }

    @Test
    public void executeCodeTest() {
        ResultDto result = codeExecutionService.executeCode("python", "print 'hello'", "ID");
        assertEquals(resultDto, result);
    }
}
