package com.izi.interpreter.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.izi.interpreter.dtos.CodeDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CodeExecutionResourceIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext applicationContext;

    @Autowired
    private CodeExecutionResource codeExecutionResource;

    @Autowired
    private ObjectMapper mapper;


    private CodeDto code;

    @Before
    public void setup() {
        mockMvc = standaloneSetup(codeExecutionResource).build();
    }

    @Test
    public void executeShouldReturnCorrectResultWhenCodeIsCorrect() throws Exception {
        code = new CodeDto("%python print 'hello'");
        mockMvc.perform(
                post("/execute")
                        .content(mapper.writeValueAsBytes(code))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is("hello")));
    }

    @Test
    public void executeShouldReturnErrorResultWhenInterpreterCannotExecuteCode() throws Exception {
        code = new CodeDto("%python print hello");
        mockMvc.perform(
                post("/execute")
                        .content(mapper.writeValueAsBytes(code))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error", is("NameError: name 'hello' is not defined")));
    }

    @Test
    public void executeShouldReturnErrorResultWhenNoLanguageCanBeFound() throws Exception {
        code = new CodeDto("python print hello");
        mockMvc.perform(
                post("/execute")
                        .content(mapper.writeValueAsBytes(code))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("Couldn't detect language from your request.")));
    }

    @Test
    public void executeShouldReturnErrorResultWhenNoCodeToExecuteCanBeFound() throws Exception {
        code = new CodeDto("%python ");
        mockMvc.perform(
                post("/execute")
                        .content(mapper.writeValueAsBytes(code))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("Couldn't detect any code from your request.")));
    }

}
