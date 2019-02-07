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
        try {
            if (languageMatcher.find()) {
                language = languageMatcher.group(1).trim();
                log.info("Found language name: {}", language);
            }
        } catch (java.lang.IllegalStateException e) {
            log.error("Exception encountered: {}", e);
            return new ResponseEntity<>(new ResultDto("", "Couldn't get the language from your request."), HttpStatus.BAD_REQUEST);
        }
        try {
            if (codeMatcher.find()) {
                codeToExecute = codeMatcher.group().trim();
                log.info("Found code to execute: {}", codeToExecute);
            }
        } catch (java.lang.IllegalStateException e) {
            log.error("Exception encountered: {}", e);
            return new ResponseEntity<>(new ResultDto("", "Couldn't get any instructions from your request."), HttpStatus.BAD_REQUEST);
        }
        ResultDto result = codeExecutionService.executeCode(language, codeToExecute, sessionId);
        if (result == null) return new ResponseEntity<>(new ResultDto("", "The language you provided is not yet supported."), HttpStatus.BAD_REQUEST);
        log.info("/execute returned: {}", result);
        return ResponseEntity.ok(result);
    }
}
