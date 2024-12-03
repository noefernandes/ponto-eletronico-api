package com.noefer.pontoeletronicoapi.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.noefer.pontoeletronicoapi.model.TimeRecordType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeRecordRequest {
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss", timezone = "GMT-3")
    private Date date;
    private TimeRecordType type;
}
