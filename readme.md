# Questionnaire Portal
Java web application for collection data from users

## Table of contents
1. [General info](#General-info)
2. [Used technologies](#Used-technologies) 
3. [Installation](#Installation) 
4. [Main classes description](#Main-classes-description)

## General info
Questionnaire Portal is Java Web Application, that allows collect data from users.
It provides opportunity to dynamically creation, modifying, removing questions (a.k. fields) in form.

## Used technologies
- Java 17
- Spring Boot + Spring MVC
- Spring Security
- Websockets
- Spring Data, JPA, Hibernate
- DB: PostgreSQL
- ReactJS
- Twitter Bootstrap
- Maven

## Installation
1. ### Clone this repository
2. ### Configure *application.properties* file
    * spring.datasource.url=...
    * spring.datasource.username=...
    * spring.datasource.password=...
   
3. ### Load the project in IntelliJ IDEA
4. ### Click "Add Configuration..." >> 'Add New Configuration...' >> 'Spring Boot'.
5. ### Give the name to your configuration
6. ### Select Main class as file or com.softarex.domas.questionnaire_portal.QuestionnairePortalApplication
7. ### Run
8. ### Then you can run [Frontend](https://github.com/AlexDomas/questionnaire-portal-frontend) app 
9. ### When the frontend and backend are running, you can follow the url http://localhost:3000/ and work fully with the application

## Main classes description

* ### QuestionnaireResponseController.java
    Controller for operations, connected with responses.
* ### QuestionnaireController.java
    Controller for operations, connected with questionnaire of user.
* ### FieldController.java
    Controller for operations, connected with fields.
* ### RegistrationController.java
    Controller for user registration.
* ### LoginController.java
    Controller for user login.
* ### UserProfileController.java
    Controller for user data profile operations.
* ###
* ### FieldResponseService.java
    Provides methods for creating and reading responses.
* ### FieldService.java
  Provides CRUD and additional operations for fields.
* ### MailService.java
  Provides mail sending functional.
* ### UserService.java
  * Provides methods for creating, reading and modifying users data.
  * Implements *UserDetailsService.java* interface for Spring Security.

