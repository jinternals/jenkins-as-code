import com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl
import org.jenkinsci.plugins.plaincredentials.impl.StringCredentialsImpl
import org.jenkinsci.plugins.plaincredentials.impl.FileCredentialsImpl
import com.cloudbees.plugins.credentials.domains.Domain
import com.cloudbees.plugins.credentials.CredentialsScope
import jenkins.model.Jenkins
import hudson.util.Secret
import com.cloudbees.jenkins.plugins.sshcredentials.impl.BasicSSHUserPrivateKey
import com.cloudbees.plugins.credentials.impl.CertificateCredentialsImpl
import com.cloudbees.plugins.credentials.SecretBytes
import org.yaml.snakeyaml.Yaml

def domain = Domain.global()
def store = Jenkins.instance.getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0].getStore()

Yaml parser = new Yaml()
def config = parser.load(("/etc/jenkins-secret-volume/config.yaml" as File).text)

config.credentials.each 
{ credential ->
      def githubAccount = new UsernamePasswordCredentialsImpl(
      CredentialsScope.GLOBAL, credential.id, credential.description, credential.username, credential.password)
      store.addCredentials(domain, githubAccount)
};
