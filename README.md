# Flight Booking Backend Application - IDATA 2306 - Group 3

This is the back end application for the Flight Finder website. This application is built using
Spring Boot in Java and is responsible for handling the business logic and data access for the
front end application. It provides RESTful APIs for the front end application to interact with the
database.

When the application is started, it will automatically populate the database with data. To
disable this feature, remove all content, except `dataInitializer.initializeUsers();` in
`initializeDatabase` method in the `FlightBookingBackendApplication` class. This will ensure that an
admin user is created to be able to populate the database.

## How to run the project

This application is running the MySQL database in a Docker Container. Before starting the 
application you need to have Docker Desktop installed and running. It is recommended to stop MySQL
if it is already running on your system, to free the port for the docker container. This is not 
available in git. When this is done, do the following steps in the IDE terminal:

First time:
1. Navigate to the "docker" folder in the project with `cd ./docker/`.
2. To build/rebuild the docker image use the commands `docker-compose up --build`.
3. If you encounter any issues with the database, you can remove the container and image with the commands: `docker-compose down -v` and rerun the `docker-compose up --build` command.

General:
* To stop the container, run `docker-compose down` (stops and removes containers, but not the images) or `docker-compose stop` (preserves the containers for a quicker restart). 
* To start the containers again, run `docker-compose up -d` (-d runs it in detached mode/background) `docker-compose start` (may only be used after `docker-compose start`). 
* To see what is running, use `docker ps` (add -a to see stopped containers) to see the running containers.
* Alternatively, use the Docker Desktop GUI to manage the containers.

### Environment Variables

The application needs the environment variable `MYSQL_PASSWORD` in an `.env` file in the "docker"
folder. This variable is used to set the password for the MySQL database. The `.env` file will not
be included in this repository for security reasons, so it needs to be created manually.