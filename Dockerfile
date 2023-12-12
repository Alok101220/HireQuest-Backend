FROM maven:3-openjdk-16 as build
COPY . .
RUN mvn clean package -DskipTests


FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/gethired-0.0.1-SNAPSHOT.jar gethired.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","gethired.jar"]
