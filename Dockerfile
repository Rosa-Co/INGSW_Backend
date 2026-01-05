# Fase 1: Costruzione dell'applicazione
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /app

# Copia l'eseguibile maven e il file pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Scarica le dipendenze
RUN ./mvnw dependency:go-offline -B

# Copia il codice sorgente
COPY src src

# Pacchettizza l'applicazione (salta i test per velocità)
RUN ./mvnw package -DskipTests

# Fase 2: Esecuzione dell'applicazione
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copia il jar dalla fase di costruzione
COPY --from=build /app/target/bugboard_backend-0.0.1-SNAPSHOT.jar app.jar

# Espone la porta
EXPOSE 8080

# Esegue il jar
ENTRYPOINT ["java", "-jar", "app.jar"]
