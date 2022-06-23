# Spring Boot CRUD RESTful API Using Kotlin

This is a sample Kotlin / Gradle / Spring Boot (version 2.7.0) application that can be used as a starter for creating a restful api using kotlin with MySQL database built-in products. I hope it helps you.

## How to Run 

This application is packaged as a war which has Tomcat 9 embedded. No Tomcat or JBoss installation is necessary. You run it using the ```java -jar``` command.

* Clone this repository 
* Make sure you are using JDK 1.8 and Gradle 7.4
* You can build the project and run the tests by running ```gradle clean package```
* Once successfully built, you can run the service by one of these two methods:
```
        java -jar -Dspring.profiles.active=test target/spring-boot-rest-example-0.5.0.war
or
        mvn spring-boot:run -Drun.arguments="spring.profiles.active=test"
```
* Check the stdout or boot_example.log file to make sure no exceptions are thrown

Once the application runs you should see something like this

```
2017-08-29 17:31:23.091  INFO 19387 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8000 (http)
2017-08-29 17:31:23.097  INFO 19387 --- [           main] com.khoubyari.example.Application        : Started KotlinRestfulApiApplicationKt in 5.593 seconds (JVM running for 6.209)
```

## About the Service

The service is just a simple hotel review REST service. It uses an in-memory database (H2) to store the data. You can also do with a relational database like MySQL or PostgreSQL. If your database connection properties work, you can call some REST endpoints defined in ```com.khoubyari.example.api.rest.hotelController``` on **port 8000**. (see below)

More interestingly, you can start calling some of the operational endpoints (see full list below) like ```/metrics``` and ```/health``` (these are available on **port 8000**)

You can use this sample service to understand the conventions and configurations that allow you to create a DB-backed RESTful service. Once you understand and get comfortable with the sample app you can add your own services following the same patterns as the sample service.
 
Here is what this little application demonstrates: 

* Full integration with the latest **Spring** Framework: inversion of control, dependency injection, etc.
* Packaging as a single war with embedded container (tomcat 8): No need to install a container separately on the host just run using the ``java -jar`` command
* Demonstrates how to set up healthcheck, metrics, info, environment, etc. endpoints automatically on a configured port. Inject your own health / metrics info with a few lines of code.
* Writing a RESTful service using annotation: supports both XML and JSON request / response; simply use desired ``Accept`` header in your request
* Exception mapping from application exceptions to the right HTTP response with exception details in the body
* *Spring Data* Integration with JPA/Hibernate with just a few lines of configuration and familiar annotations. 
* Automatic CRUD functionality against the data source using Spring *Repository* pattern
* Demonstrates MockMVC test framework with associated libraries
* All APIs are "self-documented" by Swagger2 using annotations 

Here are some endpoints you can call:

### Get information about product.

```
http://localhost:8000/products
```

### Create a hotel resource

```
POST /api/v1/products
Accept: application/json
Content-Type: application/json

{
"title": "title 4",
"description": "description 5",
"image": "http://loremipsum.com/200/200?33",
"price": 27
}

RESPONSE: HTTP 201 (Created)
Location header: http://localhost:8090/api/v1/products/1
```

### Retrieve a paginated list of hotels

```
http://localhost:8000/api/v1/products?page=8

Response: HTTP 200
Content: paginated list 

{
    "data": [
        {
            "id": 143,
            "title": "title 37",
            "description": "description 38",
            "image": "http://loremipsum.com/200/200?710",
            "price": 52
        },
        {
            "id": 144,
            "title": "title 38",
            "description": "description 39",
            "image": "http://loremipsum.com/200/200?576",
            "price": 51
        },
        {
            "id": 145,
            "title": "title 39",
            "description": "description 40",
            "image": "http://loremipsum.com/200/200?577",
            "price": 18
        },
        {
            "id": 146,
            "title": "title 40",
            "description": "description 41",
            "image": "http://loremipsum.com/200/200?740",
            "price": 14
        },
        {
            "id": 147,
            "title": "title 41",
            "description": "description 42",
            "image": "http://loremipsum.com/200/200?700",
            "price": 82
        }
    ],
    "total": 80,
    "page": 8,
    "lastPage": 17
}

```

### Update a product resource

```
PUT /api/v1/product/1
Accept: application/json
Content-Type: application/json

{
    "title" : "updated title",
    "description" : "description updated",
    "image" : "image updated",
    "price" : 20
}

RESPONSE: HTTP 204 (No Content)
```

# About Spring Boot

Spring Boot is an "opinionated" application bootstrapping framework that makes it easy to create new RESTful services (among other types of applications). It provides many of the usual Spring facilities that can be configured easily usually without any XML. In addition to easy set up of Spring Controllers, Spring Data, etc. Spring Boot comes with the Actuator module that gives the application the following endpoints helpful in monitoring and operating the service:

**/metrics** Shows “metrics” information for the current application.

**/health** Shows application health information.

**/info** Displays arbitrary application info.

**/configprops** Displays a collated list of all @ConfigurationProperties.

**/mappings** Displays a collated list of all @RequestMapping paths.

**/beans** Displays a complete list of all the Spring Beans in your application.

**/env** Exposes properties from Spring’s ConfigurableEnvironment.

**/trace** Displays trace information (by default the last few HTTP requests).

### To view your H2 in-memory datbase

The 'test' profile runs on H2 in-memory database. To view and query the database you can browse to http://localhost:8090/h2-console. Default username is 'sa' with a blank password. Make sure you disable this in your production profiles. For more, see https://goo.gl/U8m62X

# Running the project with MySQL

This project uses an in-memory database so that you don't have to install a database in order to run it. However, converting it to run with another relational database such as MySQL or PostgreSQL is very easy. Since the project uses Spring Data and the Repository pattern, it's even fairly easy to back the same service with MongoDB. 

Here is what you would do to back the services with MySQL, for example: 

### In build.gradle.kts add: 

```
           runtimeOnly("mysql:mysql-connector-java")
```

### Append this to the end of application.properties: 

```
---
server.port=8000
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/kotlin-rest-api
spring.datasource.username=root
spring.datasource.password=
spring.jpa.generate-ddl=true
spring.jpa.show-sql=true
```

# Questions and Comments: alianhakim9@gmail.com
