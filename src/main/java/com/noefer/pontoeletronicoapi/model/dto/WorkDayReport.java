package com.noefer.pontoeletronicoapi.model.dto;

import com.noefer.pontoeletronicoapi.model.WorkDay;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkDayReport extends WorkDayResponse {
    Long workedTime;
    Long remainingTime;
    Long exceededTime;

    public WorkDayReport(WorkDay workDay) {
        super(workDay);
    }
}
