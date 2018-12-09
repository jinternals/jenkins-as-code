import jenkins.model.*

def numExecutors = 5;

println("Setting up ${numExecutors} executors.")
def instance = Jenkins.getInstance()
instance.setNumExecutors(numExecutors)
