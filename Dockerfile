FROM openjdk

EXPOSE 8081

ADD target/Container1-0.0.1-SNAPSHOT.jar myapp.jar

ENTRYPOINT ["java","-jar","/myapp.jar"]