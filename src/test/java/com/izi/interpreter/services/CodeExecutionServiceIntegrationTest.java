package com.izi.interpreter.services;

import com.izi.interpreter.dtos.ResultDto;
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

    private ResultDto resultDto = new ResultDto("hello", "");

    @Test
    public void executeCodeTest() {
        ResultDto result = codeExecutionService.executeCode("python", "print 'hello'", "ID");
        assertEquals(resultDto, result);
    }
}
