## Update plugins.txt 


##### build code and docker images :
```
    mvn dockerfile:build 
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

#### Update Jenkins Plugins : 
Run following script in : http://localhost:8080/script

```$xslt
Jenkins.instance.pluginManager.plugins.each{
  plugin -> 
    println ("${plugin.getShortName()}:${plugin.getVersion()}")
}
```

#### Download and put following sdk in ./jenkins-as-code/data/tools/ dir:
```
    apache-maven-3.5.4-bin.tar.gz
    jdk-8u181-linux-x64.tar.gz
```