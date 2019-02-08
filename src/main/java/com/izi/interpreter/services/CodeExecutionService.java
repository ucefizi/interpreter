package com.izi.interpreter.services;

import com.izi.interpreter.dtos.History;
import com.izi.interpreter.dtos.LanguageDto;
import com.izi.interpreter.dtos.ResultDto;
import com.izi.interpreter.repositories.HistoryRepository;
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
    private final HistoryRepository historyRepository;

    @Autowired
    public CodeExecutionService(LanguageRepository languageRepository, HistoryRepository historyRepository) {
        this.languageRepository = languageRepository;
        this.historyRepository = historyRepository;
    }

    public ResultDto executeCode(String languageName, String code, String sessionId) {

        if (languageName == null || code == null) return null;

        LanguageDto language = languageRepository.findLanguageByName(languageName);
        History history = historyRepository.findOne(sessionId);

        history.addInstruction(code);
        historyRepository.save(history);
        String[] command = {language.getCmd(), language.getInlineOption(), history.getInstructions()};
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdErr = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String output = "";
            String error = "";
            String s;
            while ((s = stdIn.readLine()) != null) output = s;
            while ((s = stdErr.readLine()) != null) error = s;
            process.destroy();
            return new ResultDto(output, error);
        } catch (IOException e) {
            log.error("Exception encountered: {}", e);
            return null;
        }

    }
}
