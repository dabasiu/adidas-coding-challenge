# adidas-coding-challenge
Coding Challenge for Adidas Job Oppening

## Challenge
Develop a SUBSCRIPTION SYSTEM. Without UI system will be composed of three microservices:
- Public Service: Backend for Frontend microservice to be used by UI frontend.
- Subscription Service: microservice implementing subscription logic, including persistence of subscription  data in a database and email notification to confirm process is completed.
- Email Service: microservice implementing email notifications.

## Tech Stack

- Java 8
- Spring Boot 2.6.9 (last 2.7.2 version not compatible with my local maven version)
- Maven
- Hibernate with JPA
- Liquibase
- Postgresql
- Lombok
- Orika - used to work with dozer but decided to give this a try
- Docker
- JUnit and Mockito for unit tests

## Build and Run

from a shell or command prompt window run the `build_and_run.sh` file that will build the project and run in docker containers:
```bash
sh build_and_run.sh
```


Alternatively you can manually build:
```bash
mvn clean package
```

and run:
```bash
docker-compose up
```


API for Public Service can be access at `http://localhost:8094/swagger-ui.html` where API can be invoked and also the documentation is available

Subscription Service runs localhost:8095 and email service at localhost:8096 but both does not have public access allowed

## Final thoughts

- it was an interesting challenge that covers a lot of skills. A DDD aproach was used
- only made a few unit tests as in reality there is no much business logic present. Integration Tests were not made
- Decided to cancel and retrieve details of subscription by email instead of by id. In this case
- the get all subscriptions endpoint is a litteral get all. At this point theres no need to have pagination
- Subscription Service and Email Service are secured with spring security with basic authentication (hard coded username and password). A strong and secure approach would be use Oauth with clientId and client secrets
- Subscription Service invokes Email Service asynchronously. An alternative approach would be use frameworks such as Kafka
- The field newsletter Id corresponding to the campaign was not very clear. I kept it simple with no FKs to other entities but I imagine that makes sense another entity Newsletter/Campaign but than another endpoint to retrieve the data available would make sense. Decided to not overthink
- no CI/CD proposal neither config files for a kubernetes cluster were made
- persistence approach can be improved, no batch or statement management was used but on a larger scale it would be necessary
