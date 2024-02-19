# Project Movie DB 

This project is a backend application for movies management via REST API. It allows users to perform CRUD operations on movie data and supports multiple clients simultaneously. All data is persisted in an PostgreSQL database.

## Installation

### Setup with GIT

- Clone the repository with the following git command

```git clone https://github.com/polanc4170/project-movie.git```

- After the project has been cloned, go into ```..\resource\application.properties``` and update the field according to your own database access:

```
spring.datasource.url      = <DB_URL>
spring.datasource.username = <DB_USERNAME>
spring.datasource.password = <DB_PASSWORD>
```

- Run the application, which will then run on

```http://localhost:8080```

## Data

The application consists of two entities ```Movie``` and ```Image``` and their relationship and structure drawn with [DrawSQL](https://drawsql.app/) is as follows:

![](/res/db_diagram.png?raw=true "")

## API Endpoints

The following endpoints are handled by the ```MovieController```:

| Request | URL           | Success  |  Fail   | Success Body                         | Action                   |
|:--------|:--------------|:--------:|:-------:|:-------------------------------------|:-------------------------|
| POST    | /movies       |   201    |   409   | -                                    | Create movie             |
| GET     | /movies       |   200    |   400   | `List<MovieDTO>`                     | Return movies            |
| GET     | /movies?[k=v] |   200    |   400   | `List<MovieDTO>` or `Page<MovieDTO>` | Return movies            |
| GET     | /movies/{id}  |   200    |   400   | `MovieDTO`                           | Return movie with `{id}` |
| PUT     | /movies/{id}  |   200    |   400   | `MovieDTO`                           | Update movie with `{id}` |
| DELETE  | /movies       |   200    |    -    | -                                    | Delete movies            |
| DELETE  | /movies/{id}  |   200    |   400   | -                                    | Delete movie with `{id}` |

Additionally, for the ```GET /movies/?[k=v]``` the following options are available - currently only one request will take precedence:

| GET Request | URL Params                  |
|:------------|:----------------------------|
| Pagination  | page, size                  |
| Search      | pattern, startYear, endYear |
