package com.noefer.pontoeletronicoapi.model;

import lombok.Getter;

@Getter
public enum WorkLoad {
    SIXHOURS(6),
    EIGHTHOURS(8);

    private final int hours;

    WorkLoad(int hours) {
        this.hours = hours;
    }
}
