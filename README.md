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
if it is already running on your system, to free the port for the docker container. When this is 
done, do the following steps in the IDE terminal:

1. Navigate to the "docker" folder in the project with `cd ./docker/`.
2. Run the command `docker compose up` to start the MySQL database. If this command has been run earlier, you can use `docker compose start` to start the container.
3. Run the `FlightBookingBackendApplication` class to start the application. 
4. To stop the docker container, run the command `docker compose stop` in the "docker" folder.

### Environment Variables

The application needs the environment variable `MYSQL_PASSWORD` in an `.env` file in the "docker" 
folder. This variable is used to set the password for the MySQL database. The `.env` file will not 
be included in this repository for security reasons, so it needs to be created manually.