# GBM Code Challenge

_On this repository you will find a Java/Springboot REST API that was developed following the code challenge send by GBM
as an interview process and React.js frontend as an extra._

To verify the API running you should clone this repo into your local machine and run the project (IntelliJ), or if you
prefer, you can always run `mvn spring-boot:run` on your Git Bash terminal.

After that you can verify the functionality of the API with POSTMAN making requests and check the H2 DB on
`http://localhost:8080/h2-console`, the properties to connect to DB are inside `application.properties`.

To run the tests you can run `mvn test`.

To run the frontend you should have installed `node` and `npm` on your computer, if you do not have node.js you should
download it and install it on your local machine, you can find more about installing node here:
[https://nodejs.org/en/download/](https://nodejs.org/en/download/)

If you already have `node` and `npm` installed and wish to test the react frontend, you should go inside the `gbm-exercise-frontend`
folder and run `npm start`, with that you will be able to go to `http://localhost:3000` and watch the react frontend.
