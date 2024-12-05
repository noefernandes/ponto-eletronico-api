# Etapa de Build
FROM ubuntu:latest AS build

# Atualiza o sistema e instala dependências
RUN apt-get update && apt-get install -y \
    openjdk-21-jdk \
    maven \
    && apt-get clean

# Define o diretório de trabalho
WORKDIR /app

# Copia o código-fonte para o contêiner
COPY . .

# Executa o build com Maven
RUN mvn clean install

# Etapa de Runtime
FROM openjdk:21-jdk-slim

# Define a porta de exposição da aplicação
EXPOSE 8080

# Copia o JAR gerado no estágio anterior
COPY --from=build /app/target/*.jar app.jar

# Define o comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]
