# 
# Build Stage
#


#
# Package Stage 
#

FROM openjdk:11
COPY "social-media-0.0.1-SNAPSHOT.jar" app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 80
# To build: docker build --progress=plain --no-cache .
# To run: docker run -p 8080:8080 cf9