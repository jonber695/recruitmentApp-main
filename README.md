# recruitmentApp
The recruitment webapp is hosted on Azure and can be found at [url]. Along with the webapp is a Postgres database which can be found at iv1201server.postgres.database.azure.com.
The following is an overview of the database:
![Overview of the database](https://github.com/dwyer95/recruitmentApp/blob/main/RecruitmentAppDBimage.jpg)

## Layers
The recruiter webapp is written in Spring Framework (Spring Boot, Spring MVC) and uses Thymeleaf.
The application consists of layers inspired by domain-driven design. The layers are:
-	Presentation
-	Application
-	Domain
-	Repository
-	Config 
	
The presentation layer contains Controller classes responsible for determining when each view (html-page) should be displayed and which Model object should be sent to which html-page. In the same layer there are different Form classes, used for handling data submitted by users through html-forms. In the same layer there is also a presentation.error package, which contains the ErrorHandlers class. This is a Controller class that handles various errors, both HTTP errors and exceptions.

The application layer contains various Service classes which connect the repository layer and the presentation layer. Controllers do not make direct database or repository calls, but instead call methods in the Service classes. The Service classes make sure to keep transactions atomic and to roll back transactions if an exception is triggered.

The domain layer contains classes representing the various entities in the database and DTOs of those entities. The entity classes, for example Application.java or Competence.java, are used in combination with JPA and thus reflect the database tables and columns. The two classes which are not directly connected to JPA in the domain layer are UserPrincipal, used by Spring Security, and HandleApplicationException, an exception that is thrown when two recruiters are trying to change the status of an application simultaneously.

The repository layer contains methods for reading from and writing to the database. There are four repositories: one for applicants, one for applications, one for competences, and one for recruiters. For example, if the goal is to find an applicant in the database, the method for that would be found in the ApplicantAccountRepository.

The config layer contains configurations, both for Spring Security and otherwise. For general configurations, the class ServerProperties loads properties from the file application.properties, and the class RecruitmentConfig contains configurations for the Recruitment webapp, including template resolvers for Thymeleaf. The configurations for Spring Security includes SecurityConfig, which configures which login page should be used, who has the right to view which page, and such things. The CustomAuthFilter and CustomAuthProvider handle authentication specifically for the recruiter and applicant roles. There are also two handlers, one for login failure and one for login success.

## HTML pages
The following pages exist in the webapp:
-	login.html: The custom login page.
-	create.html: Where an applicant creates an application
-	search.html: Where a recruiter searches applicants.
-	searchresults.html: Where the recruiter search results show up.
-	viewapplication.html: Where a recruiter selects an application to view from the search results.
-	accepted.html: Shown when a recruiter accepts (hires) an applicant.
-	rejected.html: Shown when a recruiter rejects an applicant.
-	failure.html: Shown when an error or exception occurs. The message displayed depends on the type of error/exception.

## Other files - resources
The application.properties file found under src/main/resources sets up the connection to the Azure postgres database (provides a datasource for the Spring network). The file also specifies a path to the error site "failure.html" as well as enables custom errors by disabling whitelabel.

The files found under src/main/resources/i18n are localization and message files, used in the html pages. Messages.properties contain the strings displayed by the application. ValidationMessages.properties contain strings displayed in association with validation.

## Testing
Tests can be found in the test package. There are two help classes for testing found in test/presentation/accounts. These are PresentationTestHelper and HtmlMatchers. PresentationTestHelper has methods for mocking sending HTTP GET and POST requests, while HtmlMatchers provides matchers (and accompanying help methods for the matchers) to see whether a document contains or does not contain an element. 



