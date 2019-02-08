package com.izi.interpreter.utils;

import com.izi.interpreter.dtos.History;
import com.izi.interpreter.repositories.HistoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SessionListenerTest {

    @InjectMocks
    private SessionListener sessionListener;

    @Mock
    private HistoryRepository historyRepository;

    private HttpSessionEvent sessionEvent;
    private HttpSession session;
    private History history;

    @Before
    public void setup() {
        session = new MockHttpSession();
        history = new History("", session.getId());
        sessionEvent = new HttpSessionEvent(session);
    }

    @Test
    public void sessionCreated() {
        sessionListener.sessionCreated(sessionEvent);
        verify(historyRepository, times(1)).save(history);
    }

    @Test
    public void sessionDestroyed() {
        sessionListener.sessionDestroyed(sessionEvent);
        verify(historyRepository, times(1)).delete(session.getId());
    }
}