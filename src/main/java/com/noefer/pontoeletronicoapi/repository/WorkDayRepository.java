package com.noefer.pontoeletronicoapi.repository;

import com.noefer.pontoeletronicoapi.model.WorkDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkDayRepository extends JpaRepository<WorkDay, Long> {
}
