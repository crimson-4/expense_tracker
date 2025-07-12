# --------- STAGE 1: Build the JAR ----------
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN ./mvnw dependency:go-offline

COPY src ./src

RUN ./mvnw clean package -DskipTests

# --------- STAGE 2: Run the JAR ----------
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/target/expense-backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
