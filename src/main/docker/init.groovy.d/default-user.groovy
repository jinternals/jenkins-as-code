import jenkins.model.*
import hudson.security.HudsonPrivateSecurityRealm
import hudson.security.GlobalMatrixAuthorizationStrategy

def env = System.getenv()


config = readYaml (file: '/etc/jenkins-secret-volume/config.yml')

def jenkins = Jenkins.getInstance()
jenkins.setSecurityRealm(new HudsonPrivateSecurityRealm(false))
jenkins.setAuthorizationStrategy(new GlobalMatrixAuthorizationStrategy())

def user = jenkins.getSecurityRealm().createAccount(config.username, config.password)
user.save()

jenkins.getAuthorizationStrategy().add(Jenkins.ADMINISTER, config.username)
