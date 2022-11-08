FROM openjdk:11

#ARG JAR_FILE

#RUN [mvn package]

COPY "./target/social-media-0.0.1-SNAPSHOT.jar" app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]