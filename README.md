
# jenkins-as-code

[![Build Status](https://travis-ci.org/jinternals/jenkins-as-code.svg?branch=master)](https://travis-ci.org/jinternals/jenkins-as-code)

This is sample for setup and run jenkins as code.

    .
    ├── jenkins                 # Jenkins automation with seed job setup
    ├── jobs                    # Jenkins jobs as a code, using jobs-dsl
    ├── src/test/java           # Test cases for jobs
    └── README.md



##### build code and docker images :
```
    mvn clean install docker:build 
```

##### start containers :

```
    docker-compose -f docker-compose.yml up -d
```
##### stop containers :

 ```
    docker-compose -f docker-compose.yml stop
```

 ##### remove containers :
  
```
    docker-compose -f docker-compose.yml rm -f   
```
