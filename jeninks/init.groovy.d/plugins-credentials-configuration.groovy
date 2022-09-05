import com.cloudbees.plugins.credentials.CredentialsScope
import com.cloudbees.plugins.credentials.domains.Domain
import com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl
import org.jenkinsci.plugins.plaincredentials.impl.StringCredentialsImpl
import jenkins.model.Jenkins
import org.yaml.snakeyaml.Yaml
import hudson.util.Secret

def env = System.getenv()

if(env.PLUGINS_CREDENTIALS_CONFIG_FILE_PATH == null){
    return;
}

def parser = new Yaml()
def domain = Domain.global()

def config = parser.load(("${env.PLUGINS_CREDENTIALS_CONFIG_FILE_PATH}" as File).text)

def store = Jenkins.instance.getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0].getStore()

config.jenkins.plugins.credentials.each
        { credential ->
            def cred = new UsernamePasswordCredentialsImpl(
                    CredentialsScope.GLOBAL, credential.id, credential.description, credential.username, credential.password)
            store.addCredentials(domain, cred)
        };


config.jenkins.plugins.secrets.each
        { secret ->
            def sec = new StringCredentialsImpl(
                    CredentialsScope.GLOBAL, secret.id, secret.description, Secret.fromString(secret.secret)
            )
            store.addCredentials(domain, sec)
        };