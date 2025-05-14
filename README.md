# Flight Booking Backend Application - IDATA 2306

Flight Booking Backend repo by group 3

## Important information about DB connection

This application is running the MySQL database in a Docker Container. Before starting the 
application you need to have Docker Desktop installed and running. It is important the .env with the 
MYSQL_PASSWORD file is located in the docker folder. This is not available in git. When this is 
done, do the following steps in the IDE terminal:

First time:
1. Navigate to the "docker" folder in the project with `cd ./docker/`.
2. To build/rebuild the docker image use the commands `docker-compose up --build`.
3. If you encounter any issues with the database, you can remove the container and image with the commands: `docker-compose down -v` and rerun the `docker-compose up --build` command.

General:
* To stop the container, run `docker-compose down` (stops and removes containers, but not the images) or `docker-compose stop` (preserves the containers for a quicker restart). 
* To start the containers again, run `docker-compose up -d` (-d runs it in detached mode/background) `docker-compose start` (may only be used after `docker-compose start`). 
* To see what is running, use `docker ps` (add -a to see stopped containers) to see the running containers.
* Alternatively, use the Docker Desktop GUI to manage the containers.

