FROM adoptopenjdk/openjdk11-openj9

COPY /target/atm-machine-1.0.1.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]