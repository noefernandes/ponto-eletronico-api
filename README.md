# Aplicação de Ponto Eletrônico

Desenvolvida usando Java (21), Spring Boot (3.4.0) e React Js (18.3.1).

Link frontend: https://github.com/noefernandes/ponto-eletronico

Esta é uma aplicação para registro de ponto eletrônico de usuários.

## Arquitetura

A arquitetura final do sistema foi a seguinte:

<img width="400px" src="https://github.com/user-attachments/assets/9b5c00f9-7fb0-4c85-b8e4-cdc68a2b2df6" alt="image_name png" />

Um usuário, além de suas informações pessoais, tem informações sobre seu nível de permissão e carga horária de trabalho.
Já WorkDay é o dia de trabalho do usuário, que pode ter vários registros de tempo. Esses registros podem indicar a entrada do usuário, uma pausa, o retorno ou a saída.

## Funcionalidades

Um usuário pode:

- Logar-se no sistema
- Bater o ponto diário
- Fazer pausas
- Ver os logs de tempo tais como tempo trabalhado, tempo restante e tempo extrapolado, baseados em sua carga horária
- Adicionar novo usuário se tiver permissão de administrador

Foram desenvolvidos ainda testes unitários e de integração para testar a api e o algoritmo de cálculos de tempo.

## Funcionalidades futuras

- Login usando spring security
- Mudança de senha usando RabbitMQ ou Kafka
- Acompanhamento das estatísticas em tempo real
- Relatorios semanal, mensal, anual e desde o início
- Desabilitar usuários

## Acesso a aplicação

A aplicação pode ser acessada no seguinte link: 
https://ponto-eletronico-hkj3.onrender.com/

Obs: Existe um delay neste provedor de alguns segundos, então pode ser necessário experar um pouco antes de logar.

## Desenvolvido por Noé Fernandes
