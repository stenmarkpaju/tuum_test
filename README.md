# Tuum test project by Sten-Mark Paju

### To run the project:

- Run 'docker-compose build' in the console
- After that is finished, run 'docker-compose up' to launch the project.
- Go to website 'http://localhost:8080/swagger-ui/index.html' and the API documentation is available there.

### Project is ready(not finishing the rest, gave my explanation in the two points below)

- Has no working integration tests
-- Couldn't get MyBatis working with integration tests, says my accountMapper is null, had the same issue with regular coding and took me hours upon hours to finally fix the actual issue at hand, giving up. First time not working with Hibernate or such and not very familiar and don't know what to look for when doing mistakes with the tool. 
- Also did not manage to make it onto RabbitMQ, haven't worked with it and since I was sick for the latter part of the last week, I just don't have the time to study it, but I will fork out of this project and start learning it as I am quite interested in it.