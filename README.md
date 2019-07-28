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

#### Run on kubernetes: 
```
minikube start --vm-driver hyperkit --cpus 4 --memory 8192

kubectl apply -f https://raw.githubusercontent.com/jinternals/jenkins-as-code/master/src/main/kubernetes/namespace.yml
kubectl apply -f https://raw.githubusercontent.com/jinternals/jenkins-as-code/master/src/main/kubernetes/deployment.yml
kubectl apply -f https://raw.githubusercontent.com/jinternals/jenkins-as-code/master/src/main/kubernetes/service.yml
kubectl apply -f https://raw.githubusercontent.com/jinternals/jenkins-as-code/master/src/main/kubernetes/ingress.yml

kubectl create clusterrolebinding permissive-binding --clusterrole=cluster-admin --user=admin --user=kubelet --group=system:serviceaccounts:jenkins
    
```    
