## About
Mancala (a.k.a Kalah) is a turn-based board game played by two player. The main goal is capturing opponent's stones.
This project is a java implementation of six-stone mancala game.

- [Wikipedia](https://en.wikipedia.org/wiki/Kalah) 
- [Youtube Video - How to play Mancala](https://www.youtube.com/watch?v=jGM_ynt0NsE)

## Architecture
- Project structure is built upon hexagonal architecture.
- There are three main package in abstract way:
    - <b>Application</b> package: Real world contact of the project with rest controllers, requests and responses
    - <b>Domain</b> package: Main business logic holder with some set of layers
    - <b>Infrastructure</b> package: Base configurations and interceptors
- All objects in the project are <b>```strictly immutable```</b> to avoid any concurrency inconveniences.
- Exception handling is solved by ```Spring```'s global handling interceptor ```@RestControllerAdvice```. 
- Localization in english and dutch is provided to get error messages in requested locale. ```?locale=en``` or ```?locale=nl``` query parameters give the ability.
- There are 85 tests in project to be sure everything is working. Four main testing type is used:
    - <b>Rest Integration Tests</b>: To initialize ```spring application context``` and to hit the endpoint
    - <b>Functional Tests</b>: To initialize ```spring application context``` and to test the service and its deeper layers
    - <b>Concurrent Functional Tests</b>: To initialize ```spring application context``` and to test the service and its deeper layers with concurrent requests 
    - <b>Unit Tests</b>: To test if component under-test is working

## How to run
- Run command: ```git clone https://github.com/kahramani/mancala.git```
- Go to project's path
- Run command: ```./mvnw clean install ```
- Run command: ```java -jar target/mancala-1.0.0.jar```
- Project will listen requests from port ```8080```

## How to play
- Be sure project is up and running.
- Then you can run ```curl --header "Content-Type: application/json" \ --request POST \ http://localhost:8080/v1/games``` to create a game. Creation endpoint will return ```HTTP code: 201, ResponseBody: {"id": {GAME_ID},"uri": "http://localhost:8080/games/{GAME_ID","playerId": {STARTER_PLAYER_ID}```
- Save returned ```{GAME_ID}``` and ```{STARTER_PLAYER_ID}``` which will be required parameter to make a move. Share ```{GAME_ID}``` with your friend.
- To be a participant, your friend can run ```curl --header "Content-Type: application/json" \ --request PUT \ http://localhost:8080/v1/games/{GAME_ID}``` and then will get ```HTTP code: 200, ResponseBody: {"uri": "http://localhost:8080/games/{GAME_ID","playerId": {PARTICIPANT_PLAYER_ID}``` 
- Then you can make a move with ```curl --header "Content-Type: application/json" \ --request PUT \ http://localhost:8080/v1/games/{GAME_ID}/players/{PLAYER_ID_TO_MAKE_MOVE}/pits/{PIT_ID}```. Make a move endpoint will return ```HTTP code: 200, ResponseBody:{"id": "DVLYFVIHY845V406","uri": "http://localhost:8080/games/DVLYFVIHY845V406","status": {"1": "0","2": "7","3": "7","4": "7","5": "7","6": "7","7": "1","8": "6","9": "6","10": "6","11": "6","12": "6","13": "6","14": "0"},"turnRepeat": false}```

 - #### Predefined Rules:
    - Starter player will be the first move maker.
    - ```{PIT_ID}``` from 1 to 7 will be game starter player's pits with kalah (id: 7).
    - ```{PIT_ID}``` from 8 to 14 will be game participant player's pits with kalah (id: 14).
    - ```status``` gives you current status of the mancala board (where keys are {PIT_ID}s, values are stone count).
    - ```turnRepeat``` in response tells you if you get another turn to make a move.
    - In case of error, api will return ```errorCode``` and ```errorMessage``` with proper Http status code.

## Tech Stack
Tech|Version
---|---
JDK|1.8
Spring Boot|2.0.0.RELEASE