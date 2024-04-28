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

3. Run the MongoDb Hibernate OGM application

goto [mongodb-ogm-application]

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

7. Given a genre, return the nth page of size m of all titles ordered by Imdb score in descendant order. 
Each title should be presented tougher with its credits and the related people. 
Present also a result object, containing the total hit count and the current request page.

```shell
http http://localhost:8080/titlesOrderByScore/genre/scifi/page/1/page-size/10
```

## Dataset

* https://www.kaggle.com/api/v1/datasets/download/victorsoeiro/netflix-tv-shows-and-movies?datasetVersionNumber=2
* https://www.kaggle.com/api/v1/datasets/download/victorsoeiro/amazon-prime-tv-shows-and-movies?datasetVersionNumber=1
* https://www.kaggle.com/api/v1/datasets/download/victorsoeiro/disney-tv-shows-and-movies?datasetVersionNumber=1
* https://www.kaggle.com/api/v1/datasets/download/victorsoeiro/hbo-max-tv-shows-and-movies?datasetVersionNumber=1

That are distributed under [CCO: Public Domain](https://creativecommons.org/publicdomain/zero/1.0/)
A special thanks to https://www.kaggle.com/victorsoeiro for them!
