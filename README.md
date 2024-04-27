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

```shell
[mongodb-ogm-application] mvn spring-boot:run
```

4. See current data report

```shell
http http://localhost:8080/report
```

5. To import a data set (`amazon-prime`, `disney-plus`, `hbo-max`, `netflix`)

```shell
http POST http://localhost:8080/load/amazon-prime
```

## Dataset

* https://www.kaggle.com/api/v1/datasets/download/victorsoeiro/netflix-tv-shows-and-movies?datasetVersionNumber=2
* https://www.kaggle.com/api/v1/datasets/download/victorsoeiro/amazon-prime-tv-shows-and-movies?datasetVersionNumber=1
* https://www.kaggle.com/api/v1/datasets/download/victorsoeiro/disney-tv-shows-and-movies?datasetVersionNumber=1
* https://www.kaggle.com/api/v1/datasets/download/victorsoeiro/hbo-max-tv-shows-and-movies?datasetVersionNumber=1

That are distributed under [CCO: Public Domain](https://creativecommons.org/publicdomain/zero/1.0/)
A special thanks to https://www.kaggle.com/victorsoeiro for them!
