# ATM Backend Service

The ATM-Machine is a project that intends to simulate cash withdrawal.

As a persistence solution for the customer accounts, MongoDB was the chosen technology, because of the fast integration with Spring Boot applications.

# Getting Started
In order to build and set up the project, it first must be compiled with the Maven command that should be run at the root-level:

```shell
mvn clean install
```
If MongoDB is already running on your machine, with the default settings (answering at the port 27017), then it suffices either to run an application configuration on Intellij IDEA if that the IDE that you prefer, or to run the following command in a command line in the folder containing the jar.

 The Java version which was used in this project was Java 11.

```shell
java -jar atm-machine-1.0.1.jar
```

For the case where the application must be run in a docker container, to create the image the following command must be used:
```shell
docker build . -t  zinkworks/atm:latest
```
Additionally, if running the application in docker containers is required, as a consequence, a mongo container must be also coupled to the Java application container as a sidecar.

To get both containers running, the command must be run at the root folder level in a command prompt or shell.
```shell
docker build . -t  zinkworks/atm:latest
```




### Topics to improve/explore in the future
 - Fix the integration test, by tuning the configuration of the embedded mongo instance.
 - Develop a userdetails service with encrypted passwords (at the moment the services are called with a basic Authentication of "username:password").
 - Integrate with Thymeleaf.
 - Incorporate an API to add accounts, refill the machine slots, ...
