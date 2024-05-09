# Mapping Framework Evaluation

## Play with the project

1. Run MongoDB server container

``` shell
docker run -i --rm -p 27017:27017 --name=mongodb-sever mongo:latest
```

Container data is ephemeral, stop and re-run the container to reset all the data. 

2. Compile and run the tests

``` shell
mvn clean install
```

### MongoDB OGM Application

goto the subproject directory > [mongodb-ogm-application]

3. Run the MongoDb Hibernate OGM application

```shell
mvn spring-boot:run
```

4. See current data report

```shell
http http://localhost:8080/report
```

5. To import a data set (`amazon-prime`, `disney-plus`, `hbo-max`, `netflix`)

```shell
http POST http://localhost:8080/load/amazon-prime
```

6. Given a name of a person, find all credits referred to that person, 
presenting also the all the titles with scores and details associated to those credits.

```shell
http --raw "Oscar Isaac" http://localhost:8080/findCredits
```

7. Given a genre, return the nth page of size m of all titles ordered by Tmdb score in descendant order. 
All title attributes should be presented, including the genres, the countries and the platforms providing it.

```shell
http http://localhost:8080/titlesOrderByScore/genre/scifi/page/1/page-size/10
```

8. Find all movies provided by a given platform having releaseYear included in a range of years.
All title attributes should be presented, including the genres, the countries and the platforms providing it.

```shell
http http://localhost:8080/findMovies/platform/disney-plus/start-year/2018/end-year/2019
```

### MongoDB Native Application

goto the subproject directory > [mongodb-ogm-application]

9. Run the MongoDb native application

```shell
mvn spring-boot:run
```

10. Run the people pipeline

```shell
http POST http://localhost:8081/people
```

11. Run the titles pipeline

```shell
http POST http://localhost:8081/titles
```

12. See current data report

```shell
http http://localhost:8081/report
```

13. Given a name of a person, find all credits referred to that person,
   presenting also the all the titles with scores and details associated to those credits.

```shell
http --raw "Oscar Isaac" http://localhost:8081/findCredits
```

14. Given a genre, return the nth page of size m of all titles ordered by Tmdb score in descendant order.
   All title attributes should be presented, including the genres, the countries and the platforms providing it.

```shell
http http://localhost:8081/titlesOrderByScore/genre/scifi/page/1/page-size/10
```

15. Find all movies provided by a given platform having releaseYear included in a range of years.
   All title attributes should be presented, including the genres, the countries and the platforms providing it.

```shell
http http://localhost:8080/findMovies/platform/disney-plus/start-year/2018/end-year/2019
```

## Dataset

* https://www.kaggle.com/api/v1/datasets/download/victorsoeiro/netflix-tv-shows-and-movies?datasetVersionNumber=2
* https://www.kaggle.com/api/v1/datasets/download/victorsoeiro/amazon-prime-tv-shows-and-movies?datasetVersionNumber=1
* https://www.kaggle.com/api/v1/datasets/download/victorsoeiro/disney-tv-shows-and-movies?datasetVersionNumber=1
* https://www.kaggle.com/api/v1/datasets/download/victorsoeiro/hbo-max-tv-shows-and-movies?datasetVersionNumber=1

That are distributed under [CCO: Public Domain](https://creativecommons.org/publicdomain/zero/1.0/)
A special thanks to https://www.kaggle.com/victorsoeiro for them!
