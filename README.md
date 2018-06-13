# jenkins-as-code
This is sample for setup and run jenkins as code.

    .
    ├── jenkins                  # Jenkins automation with seed job setup
    ├── jobs                    # Jenkins jobs as a code, using jobs-dsl
    ├── src/test/java           # Test cases for jobs
    └── README.md

### Build Jenkins image

    docker build ./jenkins -t jenkins_ci
    


### Run Jenkins container

    docker run --name jenkins_ci_container -p 8080:8080 -p 50000:50000 -v /var/jenkins_home jenkins_ci

### Remove Jenkins container and image

    docker rm jenkins_ci_container
    docker rmi jenkins_ci
    
