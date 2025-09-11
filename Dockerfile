# Etapa 1: build
FROM gradle:8.10-jdk17 AS builder
WORKDIR /app

COPY . .

RUN gradle clean build --no-daemon

# Etapa 2: imagem final
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copia o jar gerado
COPY --from=builder /app/build/libs/*.jar app.jar

# Porta exposta (Render detecta automaticamente)
EXPOSE 8080

# Executa a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]