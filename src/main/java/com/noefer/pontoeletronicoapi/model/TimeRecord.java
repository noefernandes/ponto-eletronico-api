package com.noefer.pontoeletronicoapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.noefer.pontoeletronicoapi.model.dto.TimeRecordRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "time_record_seq")
    @SequenceGenerator(name = "time_record_seq", sequenceName = "time_record_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss", timezone = "GMT-3")
    private LocalDateTime timestamp;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TimeRecordType type;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private WorkDay workDay;

    public TimeRecord(TimeRecordRequest request) {
        this.timestamp = request.getTimestamp();
        this.type = request.getType();
    }
}
