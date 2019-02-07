package com.izi.interpreter.resources;

import com.izi.interpreter.dtos.CodeDto;
import com.izi.interpreter.dtos.ResultDto;
import com.izi.interpreter.services.CodeExecutionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CodeExecutionResourceTest {

    @Mock
    private CodeExecutionService codeExecutionService;

    @InjectMocks
    private CodeExecutionResource codeExecutionResource;

    private ResultDto result = new ResultDto("hello", "");
    private CodeDto code = new CodeDto("%bash echo hello");

    @Test
    public void executeShouldReturnCorrectResult() {
        when(codeExecutionService.executeCode(eq("bash"), eq("echo hello"), anyString())).thenReturn(result);
        ResultDto resultDto = codeExecutionResource.execute(code).getBody();
        assertEquals(result, resultDto);
    }

}