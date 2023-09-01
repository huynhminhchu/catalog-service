FROM eclipse-temurin:20-jdk

WORKDIR workspace

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} catalog-service.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar", "catalog-service.jar"]
