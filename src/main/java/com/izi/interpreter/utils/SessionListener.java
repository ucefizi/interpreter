package com.izi.interpreter.utils;

import com.izi.interpreter.dtos.History;
import com.izi.interpreter.repositories.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Component
public class SessionListener implements HttpSessionListener {

    private final HistoryRepository historyRepository;

    @Autowired
    public SessionListener(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        History history = new History("", se.getSession().getId());
        historyRepository.save(history);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        String sessionId = se.getSession().getId();
        historyRepository.delete(sessionId);
    }
}
