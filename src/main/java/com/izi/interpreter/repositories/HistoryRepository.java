package com.izi.interpreter.repositories;

import com.izi.interpreter.dtos.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, String> {
    History findBySessionId(String sessionId);
}
