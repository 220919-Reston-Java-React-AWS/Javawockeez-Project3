# 
# Build Stage
#

FROM maven:3.8.6-jdk-11
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package



#
# Package Stage 
#

FROM openjdk:11
COPY --from=build /home/app/target/social-media-0.0.1-SNAPSHOT.jar /usr/local/lib/spring-app.jar
ENTRYPOINT ["java", "-jar", "spring-app.jar"]
# COPY "./target/social-media-0.0.1-SNAPSHOT.jar" app.jar
# ENTRYPOINT ["java", "-jar", "app.jar"]



# To build: docker build --progress=plain --no-cache .
# To run: docker run -p 8080:8080 cf9