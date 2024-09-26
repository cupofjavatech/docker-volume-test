# Docker Volume Example 

Project is created with aim to understand Docker Volume

## Project Description
Project exposes two end points 


/games/all  - To get All games


/games/add - To add new games

Games can be stored in file system anywhere on computer. 
Path can be set with configurable properties under /src/main/resources/application.properites

- game.file.path
- game.file.name

Application port can be updated with property
- server.port

### Dockerfile
Dockerfile is being added to build Docker Image

### dc-volume 
dc-volume.yaml docker-compose is added

## Some useful Docker command

### Building Docker Compose
docker build -t cupofjava/volume-readwrite:v1 .

### Creating Volume
docker volume create games-volume

### Deleting Docker Volume
docker volume rm games-volume

### Inspecting Docker image
docker volume inspect games-volume

### Running Docker Image with Command 
docker run -d --name game-test -v games-volume:/app/games -e GAME_FILE_PATH=/app/games -p8081:8081 dhirenchauhan/volume-readwrite:v1 -d

### Running Docker image with Docker Compose

Running all Services at once : docker-compose -f dc-volume.yaml up -d 

Running Individual Services : 
e.g : docker-compose -f dc-volume.yaml up -d volume-filepath-rw
