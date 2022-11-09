# Build

FROM maven:3.8.6-jdk-11

RUN mvn build



# Package and Run

FROM openjdk:11

COPY "./target/social-media-0.0.1-SNAPSHOT.jar" app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]



# To build: docker build --progress=plain --no-cache .

# To run: docker run -p 8080:8080 cf9