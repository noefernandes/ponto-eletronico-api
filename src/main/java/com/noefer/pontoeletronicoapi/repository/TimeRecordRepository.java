package com.noefer.pontoeletronicoapi.repository;

import com.noefer.pontoeletronicoapi.model.TimeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeRecordRepository extends JpaRepository<TimeRecord, Long> {
}
