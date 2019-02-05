package com.izi.interpreter.services;

import com.izi.interpreter.dtos.LanguageDto;
import com.izi.interpreter.dtos.ResultDto;
import com.izi.interpreter.repositories.LanguageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
@Slf4j
public class CodeExecutionService {

    private final LanguageRepository languageRepository;

    @Autowired
    public CodeExecutionService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public ResultDto executeCode(String languageName, String code) {

        if (languageName == null || code == null) return null;

        LanguageDto language = languageRepository.findLanguageByName(languageName);
        if (language == null) return new ResultDto("", "The language you provided couldn't be found.");
        String[] command = {language.getCmd(), language.getInlineOption(), code};
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdErr = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder output = new StringBuilder();
            StringBuilder error = new StringBuilder();
            String s;
            while ((s = stdIn.readLine()) != null) output.append(" ").append(s);
            while ((s = stdErr.readLine()) != null) error.append(" ").append(s);
            process.destroy();
            return new ResultDto(output.toString().trim(), error.toString().trim());
        } catch (IOException e) {
            log.error("Exception encountered: {}", e);
            return null;
        }

    }
}
