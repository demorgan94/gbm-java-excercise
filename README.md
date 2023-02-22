# GBM Code Challenge

*On this repository you will find a Java/Springboot REST API that was developed following the code challenge send by GBM
as an interview process.*

To verify the API running you should clone this repo into your local machine and run the project (IntelliJ), or if you
prefer, you can always run `mvn spring-boot:run` on your Git Bash terminal.

After that you can verify the functionality of the API with POSTMAN making requests and check the H2 DB on 
`http://localhost:8080/h2-console`, the properties to connect to DB are inside `application.properties`.

To run the tests you can run `mvn test`.