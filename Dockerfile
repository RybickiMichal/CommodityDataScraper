FROM amazoncorretto:11-alpine-jdk

COPY target/DataScraper-0.0.1-SNAPSHOT.jar DataScraper-0.0.1.jar
ENTRYPOINT ["java","-jar","/DataScraper-0.0.1.jar"]