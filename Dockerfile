FROM maven:3-openjdk-16 as build
COPY . .
RUN mvn clean package -DskipTests


FROM openjdk:11.0.16-jdk-slim-buster
COPY --from=build /target/gethired-0.0.1-SNAPSHOT.jar gethired.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","gethired.jar"]
