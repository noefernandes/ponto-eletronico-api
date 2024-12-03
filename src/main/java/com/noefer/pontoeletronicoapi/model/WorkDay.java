package com.noefer.pontoeletronicoapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.noefer.pontoeletronicoapi.model.dto.WorkDayRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkDay {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "work_day_seq")
    @SequenceGenerator(name = "work_day_seq", sequenceName = "work_day_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss", timezone = "GMT-3")
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserProfile user;

    @OneToMany(mappedBy = "workDay", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimeRecord> timeRecords = new ArrayList<>();

    public WorkDay(WorkDayRequest request) {
        this.timestamp = request.getTimestamp();
    }
}
