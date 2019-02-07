package com.izi.interpreter.repositories;

import com.izi.interpreter.dtos.History;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class HistoryRepositoryTest {

    @Autowired
    private HistoryRepository historyRepository;

    private History history = new History("inst", "ID");

    @Test
    public void saveTest() {
        History result = historyRepository.save(history);
        assertEquals(history, result);
    }

    @Test
    public void getByIdTest() {
        historyRepository.save(history);
        History result = historyRepository.findOne("ID");
        assertEquals(history, result);
    }

    @Test
    public void deleteTest() {
        historyRepository.save(history);
        historyRepository.delete("ID");
        History result = historyRepository.findOne("ID");
        assertNull(result);
    }

}
