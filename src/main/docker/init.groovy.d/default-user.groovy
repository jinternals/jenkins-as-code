import jenkins.model.*
import hudson.security.HudsonPrivateSecurityRealm
import hudson.security.GlobalMatrixAuthorizationStrategy
import org.yaml.snakeyaml.Yaml

def env = System.getenv()

Yaml parser = new Yaml()
def config = parser.load(("/etc/jenkins-secret-volume/config.yaml" as File).text)
def jenkins = Jenkins.getInstance()

jenkins.setSecurityRealm(new HudsonPrivateSecurityRealm(false))
jenkins.setAuthorizationStrategy(new GlobalMatrixAuthorizationStrategy())

def user = jenkins.getSecurityRealm().createAccount(config.username, config.password)
user.save()

jenkins.getAuthorizationStrategy().add(Jenkins.ADMINISTER, config.username)
