

## Update plugins.txt 

Run  following script in : http://<jenkins-url>/script

```$xslt

Jenkins.instance.pluginManager.plugins.each{
  plugin -> 
    println ("${plugin.getDisplayName()} (${plugin.getShortName()}): ${plugin.getVersion()}")
}
```
