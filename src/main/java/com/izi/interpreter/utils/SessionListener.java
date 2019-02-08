package com.izi.interpreter.utils;

import com.izi.interpreter.dtos.History;
import com.izi.interpreter.repositories.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import static com.izi.interpreter.conf.Constants.HISTORY;

@Component
public class SessionListener implements HttpSessionListener {

    @Autowired
    private HistoryRepository historyRepository;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        History history = new History("", se.getSession().getId());
        historyRepository.save(history);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        History history = (History) se.getSession().getAttribute(HISTORY);
        historyRepository.delete(history);
    }
}
