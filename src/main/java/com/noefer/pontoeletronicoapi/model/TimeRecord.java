package com.noefer.pontoeletronicoapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.noefer.pontoeletronicoapi.model.dto.TimeRecordRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeRecord {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss", timezone = "GMT-3")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TimeRecordType type;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private WorkDay workDay;

    public TimeRecord(TimeRecordRequest request) {
        this.date = request.getDate();
        this.type = request.getType();
    }
}