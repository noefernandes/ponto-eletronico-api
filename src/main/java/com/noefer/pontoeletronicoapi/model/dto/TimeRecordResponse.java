package com.noefer.pontoeletronicoapi.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.noefer.pontoeletronicoapi.model.TimeRecord;
import com.noefer.pontoeletronicoapi.model.TimeRecordType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeRecordResponse {
    private Long id;
    private TimeRecordType type;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss", timezone = "GMT-3")
    private LocalDateTime timestamp;

    public TimeRecordResponse(TimeRecord timeRecord) {
        this.id = timeRecord.getId();
        this.type = timeRecord.getType();
        this.timestamp = timeRecord.getTimestamp();
    }
}
