# Mapping Framework Evaluation

## Play with the project

1. Run MongoDB server container

``` shell
docker run -i --rm -p 27017:27017 --name=mongodb-sever mongo:latest
```

2. Compile and run the tests

``` shell
mvn clean install
```

## Dataset

* https://www.kaggle.com/api/v1/datasets/download/victorsoeiro/netflix-tv-shows-and-movies?datasetVersionNumber=2
* https://www.kaggle.com/api/v1/datasets/download/victorsoeiro/amazon-prime-tv-shows-and-movies?datasetVersionNumber=1
* https://www.kaggle.com/api/v1/datasets/download/victorsoeiro/disney-tv-shows-and-movies?datasetVersionNumber=1
* https://www.kaggle.com/api/v1/datasets/download/victorsoeiro/hbo-max-tv-shows-and-movies?datasetVersionNumber=1

That are distributed under [CCO: Public Domain](https://creativecommons.org/publicdomain/zero/1.0/)
A special thanks to https://www.kaggle.com/victorsoeiro for them!
