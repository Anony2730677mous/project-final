FROM maven:3.8.6-openjdk-18-slim

LABEL description="JiraRush application"
LABEL version="1.0"
LABEL profiles="prod (for run with postgresql in compose)"
LABEL build="docker build -t jira-prod-img ."
LABEL run="docker-compose up -d"

WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY resources ./resources
COPY lombok.config ./lombok.config
COPY config/_application-prod.yaml ./src/main/resources/application.yaml
RUN mvn clean package -DskipTests
RUN mkdir -p /app && mv ./target/*.jar /app/project-final.jar
RUN rm -rf ./target
RUN rm -rf ./src
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/project-final.jar", "--spring.profiles.active=prod"]
