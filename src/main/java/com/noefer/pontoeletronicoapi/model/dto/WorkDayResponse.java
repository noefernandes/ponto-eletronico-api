package com.noefer.pontoeletronicoapi.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.noefer.pontoeletronicoapi.model.WorkDay;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkDayResponse {
    private Long id;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss", timezone = "GMT-3")
    private LocalDateTime timestamp;
    private List<TimeRecordResponse> timeRecords;

    public WorkDayResponse(WorkDay workDay) {
        this.id = workDay.getId();
        this.timestamp = workDay.getTimestamp();
        this.timeRecords = workDay.getTimeRecords().stream().map(TimeRecordResponse::new).toList();
    }
}
