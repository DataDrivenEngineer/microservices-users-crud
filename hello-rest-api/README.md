### Sample RESTful API App for User Management

#### Description

This app represents secure RESTful API endpoints for basic operations with application users: Create, Read, Update & Delete.

#### Authentication & Authorization Process

In order to get access to API endpoints, it is necessary to sign up & login to acquire Authorization JWT token. Please refer to the steps below:

* You can sign up via `http://localhost:8080/hello-rest-api/users` endpoint by sending JSON or XML payload, for example:



 	`{
     "firstName": "Jon",
     "lastName": "Doe",
     "email": "test111@test.com",
     "password": "123"
     }`
 
 Successfull response should look like below:
 
    {
     "userId": "SbKLHl8H04zZtW9ao8N9hwGjOiFd19",
     "firstName": "Jon",
     "lastName": "Doe",
     "email": "test111@test.com"
    }
    
* You can login by sending credentials in JSON or XML payload to `http://localhost:8080/hello-rest-api/users/login` like below:


    `{
	 "email": "test111@test.com",
	 "password": "123"
    }`

Successful response should contain the JWT token in response header which can then be used for accessing protected endpoints:

`Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MTExQHRlc3QuY29tIiwiZXhwIjoxNTc2MTk2NTEzfQ.65A0wXJv7NkHSJtL3HePB-RwFgOqPMs0FRyJPVn79fiHYQ1kapSKsZxesKEVrOfiww8aNPvj0l6qgkvCpQWJRA`

* Now you can access API endpoint for managing users of your application:
    * Get all users: Send HTTP GET request to endpoint = `/hello-rest-api/users?page=1&limit=50`
    * Update user: Send HTTP PUT request to endpoint `/hello-rest-api/users/WPcJ8BqHS8b5ItOX2g2OCJAOUM9YNG`, where `WPcJ8BqHS8b5ItOX2g2OCJAOUM9YNG` is a public user ID which can be found in server response upon successfull user creation at step 1 above
    * Delete user: Send HTTP DELETE request to endpoint `/hello-rest-api/users/WPcJ8BqHS8b5ItOX2g2OCJAOUM9YNG`, where `WPcJ8BqHS8b5ItOX2g2OCJAOUM9YNG` is a public user ID

#### Tech Stack

-  Spring Boot 2
-  Spring Security 5
-  Spring MVC 5
-  jwt.io for generation of JWT tokens
-  JUnit 5 for user testing
-  MySQL running in Docker
-  Tomcat web server

#### Future Plans

- Deploy to Amazon Web Services
- Implement password reset feature
- Implement email verification feature
- Increase test coverage

Thank you for your time!

