package com.noefer.pontoeletronicoapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.noefer.pontoeletronicoapi.model.dto.WorkDayRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkDay {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss", timezone = "GMT-3")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserProfile user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimeRecord> timeRecords = new ArrayList<>();

    public WorkDay(WorkDayRequest request) {
        this.date = request.getDate();
        request.getTimeRecords().forEach(
                timeRecord -> {
                    TimeRecord record = new TimeRecord(timeRecord);
                    record.setWorkDay(this);
                    timeRecords.add(record);
                }
        );
    }
}
