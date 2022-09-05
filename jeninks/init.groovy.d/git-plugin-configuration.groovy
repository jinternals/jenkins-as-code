import jenkins.model.*
import org.yaml.snakeyaml.Yaml

def env = System.getenv()

if(env.GIT_CONFIG_FILE_PATH == null){
    return;
}

def parser = new Yaml()
def config = parser.load(("${env.GIT_CONFIG_FILE_PATH}" as File).text)
def inst = Jenkins.getInstance()

def scm = inst.getDescriptor("hudson.plugins.git.GitSCM")

scm.setGlobalConfigName(config.jenkins.git.name)
scm.setGlobalConfigEmail(config.jenkins.git.email)

scm.save()