package com.izi.interpreter.resources;

import com.izi.interpreter.dtos.CodeDto;
import com.izi.interpreter.dtos.ResultDto;
import com.izi.interpreter.services.CodeExecutionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping
@Slf4j
public class CodeExecutionResource {



    private final CodeExecutionService codeExecutionService;

    private Pattern languagePattern = Pattern.compile("%(\\w+) ");
    private Pattern codePatter = Pattern.compile("( .+)+");


    @Autowired
    public CodeExecutionResource(CodeExecutionService codeExecutionService) {
        this.codeExecutionService = codeExecutionService;
    }

    @PostMapping("/execute")
    public ResponseEntity<ResultDto> execute(@RequestBody CodeDto code) {

        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        log.info("/execute called from a session with Id={}", sessionId);

        Matcher languageMatcher = languagePattern.matcher(code.getCode());
        Matcher codeMatcher = codePatter.matcher(code.getCode());
        String codeToExecute = null;
        String language = null;
        if (languageMatcher.find()) {
            language = languageMatcher.group(1).trim();
            log.info("Found language name: {}", language);
        }

        if (codeMatcher.find()) {
            codeToExecute = codeMatcher.group().trim();
            log.info("Found code to execute: {}", codeToExecute);
        }
        ResultDto result = codeExecutionService.executeCode(language, codeToExecute, sessionId);
        if (language == null) return new ResponseEntity<>(new ResultDto("", "Couldn't detect language from your request."), HttpStatus.BAD_REQUEST);
        if (codeToExecute == null) return new ResponseEntity<>(new ResultDto("", "Couldn't detect any code from your request."), HttpStatus.BAD_REQUEST);
        log.info("/execute returned: {}", result);
        return ResponseEntity.ok(result);
    }
}
