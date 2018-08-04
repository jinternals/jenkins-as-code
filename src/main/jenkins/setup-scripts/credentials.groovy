import jenkins.model.*
import com.cloudbees.plugins.credentials.domains.*
import com.cloudbees.plugins.credentials.impl.*

import static com.cloudbees.plugins.credentials.CredentialsScope.GLOBAL

println("Setting credentials")

def domain = Domain.global()

def store = Jenkins.instance.getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0].getStore()

def artifactoryPassword = new File("/run/secrets/artifactoryPassword").text.trim()

def mavenCredentials=['username':'admin', 'password':artifactoryPassword, 'description':'JFrog Artifactory Credentials']
def mavenUser = new UsernamePasswordCredentialsImpl(GLOBAL, 'artifactoryCredentials', mavenCredentials.description, mavenCredentials.username, mavenCredentials.password)
store.addCredentials(domain, mavenUser)


def demoCredentials=['username':'demo', 'password':'demo', 'description':'Demo Credentials']
def demoUser = new UsernamePasswordCredentialsImpl(GLOBAL, 'demoCredentials', demoCredentials.description, demoCredentials.username, demoCredentials.password)
store.addCredentials(domain, demoUser)