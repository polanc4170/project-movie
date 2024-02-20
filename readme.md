# Project Movie DB 

This project is a backend application for movies management via REST API, built using the [Spring Boot](https://spring.io/projects/spring-boot) framework. It allows users to perform CRUD operations on movie data and supports multiple clients simultaneously. All data is persisted in an [PostgreSQL](https://www.postgresql.org/) database.

## Installation

- Clone the repository with the following git command

```
git clone https://github.com/polanc4170/project-movie.git
```

### Setup with GIT

- After the project has been cloned, go into ```..\resource\application.properties``` and update the field according to your own database access:

```
spring.datasource.url      = <DB_URL>
spring.datasource.username = <DB_USERNAME>
spring.datasource.password = <DB_PASSWORD>
```

- Run the application, which will then run on

```http://localhost:8080```

### Setup with Docker

- After the project has been cloned, run the following commands in the project root folder:

```
mvn clean
mvn install
docker compose up
```

- With this the application will create a docker container with the database running on port ```5432``` and the server running on port ```8080```. When you are done with the application, by entering the following command, the containers will exit:

```
docker compose down
```

## Data

The application consists of two entities ```Movie``` and ```Image``` and their relationship and structure drawn with [DrawSQL](https://drawsql.app/) is as follows:

![DB Diagram](/res/db_diagram.png?raw=true)

## API Endpoints

The following endpoints are handled by the ```MovieController```:

| Request | URL           | Success | Body                                 | Action                   |
|:--------|:--------------|:-------:|:-------------------------------------|:-------------------------|
| POST    | /movies       |   201   | -                                    | Create movie             |
| GET     | /movies       |   200   | `List<MovieDTO>`                     | Return movies            |
| GET     | /movies?[k=v] |   200   | `List<MovieDTO>` or `Page<MovieDTO>` | Return movies            |
| GET     | /movies/{id}  |   200   | `MovieDTO`                           | Return movie with `{id}` |
| PUT     | /movies/{id}  |   200   | `MovieDTO`                           | Update movie with `{id}` |
| DELETE  | /movies       |   200   | -                                    | Delete movies            |
| DELETE  | /movies/{id}  |   200   | -                                    | Delete movie with `{id}` |

Additionally, for the ```GET /movies/?[k=v]``` the following options are available - currently only one request will take precedence:

| GET Request | URL Params                  |
|:------------|:----------------------------|
| Pagination  | page, size                  |
| Search      | pattern, startYear, endYear |
