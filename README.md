

# article-management-service

The Service is used to manage articles such as create, delete , update as well as perform other required tasks. 


## Project Structure

`/src/*` structure follows default Java structure.

## Development

To build your application in the dev profile, run:

```
./mvnw install
```

## Testing

To launch your application's tests, run:

```
./mvnw verify
```


## Run
To run your application , run the ArticleManagementServiceApplication class


Below are the endpoints that are running on the default port 8080

POST localhost:8080/article

```
[{
    "header":"Test Header3",
    "text":"Test Text",
    "shortDesc":"Test ShortDesc",
    "author":"Test Author",
    "keyword":"test Keyword"
}]
```

PUT localhost:8080/article
```
{
    "id" : "62137a3a4a2e4b256c96af34",
    "header":"Test Header5",
    "text":"Test Text1",
    "shortDesc":"Test ShortDesc",
    "author":"Test Author",
    "keyword":"test Keyword"
}
```

DEL localhost:8080/article/62137a3a4a2e4b256c96af34

GET localhost:8080/article/62137a3a4a2e4b256c96af34

GET localhost:8080/article?pageNumber=0&pageSize=10&authorIds=6213908a74b1ff0f0aa7bbb5&keyword=test

Note: I have not used docker and sonar testing for quick delivery along with some dependent functionalities such as spring securing using oauth/jwt , circuit breaker,api-gateway,eureka server,config server,user-service or configmaps and other services required in microservice architecture.


