import jenkins.model.JenkinsLocationConfiguration
import org.yaml.snakeyaml.Yaml

def env = System.getenv()

if(env.JENKINS_CONFIG_FILE_PATH == null){
    return;
}

def parser = new Yaml()
def config = parser.load(("${env.JENKINS_CONFIG_FILE_PATH}" as File).text)


def jenkinsLocationConfiguration = JenkinsLocationConfiguration.get()

jenkinsLocationConfiguration.setUrl(config.jenkins.buildUrl)
jenkinsLocationConfiguration.setAdminAddress(config.jenkins.email)

jenkinsLocationConfiguration.save()