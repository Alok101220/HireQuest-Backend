FROM maven:3.8.5-openjdk-17 as build
<<<<<<< HEAD
COPY . . 
=======
COPY . .
>>>>>>> e37889efa70a34b913be9e6a45fa8eb7b1de4d87
RUN mvn clean package -DskipTests


FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/gethired-0.0.1-SNAPSHOT.jar gethired.jar
EXPOSE 8080
<<<<<<< HEAD
ENTRYPOINT ["java","-jar","gethired.jar"] 
=======
ENTRYPOINT ["java","-jar","gethired.jar"]
>>>>>>> e37889efa70a34b913be9e6a45fa8eb7b1de4d87
