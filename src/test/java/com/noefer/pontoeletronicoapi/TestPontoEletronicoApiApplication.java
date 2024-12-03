package com.noefer.pontoeletronicoapi;

import org.springframework.boot.SpringApplication;

public class TestPontoEletronicoApiApplication {

    public static void main(String[] args) {
        SpringApplication.from(PontoEletronicoApiApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
