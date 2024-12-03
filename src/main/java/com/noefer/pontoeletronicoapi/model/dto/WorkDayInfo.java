package com.noefer.pontoeletronicoapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkDayInfo {
    private Long id;
    private Long workedTime;
    private Long remainingTime;
    private Long exceededTime;
}
