

## Update plugins.txt 

Run  following script in : http://localhost:8080/script

```$xslt
Jenkins.instance.pluginManager.plugins.each{
  plugin -> 
    println ("${plugin.getDisplayName()} (${plugin.getShortName()}): ${plugin.getVersion()}")
}
```
