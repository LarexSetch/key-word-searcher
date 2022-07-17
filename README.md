# How to run

Run docker compose 
```shell
docker-compose up -d
```
It started up the wiremock witch uses for development
after that you can start application by

```shell
./gradlew run
```

#### OR

Configure the application by set environment variables 
working with yandex api (it doesn't tested because have no limits)
and run 
```shell
./gradlew run
```

Application runs and listen 8090 port.

# API:

### Request:
```shell
GET /search?query={query}
GET /search?query={query}&query={query1}
GET /search?query={query}&query={query1}&query={query2}
```

### Response:
```shell
{
  "items": [{
    "groupBy": ["domain.com", "query"],
    "count": 0
  }]
}

```

# Alternative idea of app implementation

Create 2 endpoints:
1. Submit list of queries and return the identifier of submittion
2. Another endpoint returns information of prepared data

Pros of another implementation:
* Do not keep connection request data some times later
* Easy implement the queue of searching

Cons
* 2 endpoints it's more complicated than one implementation
* Addition load for get data
