import jenkins.model.*

println("Setup number of executors")

def instance = Jenkins.getInstance()
instance.setNumExecutors(5)
