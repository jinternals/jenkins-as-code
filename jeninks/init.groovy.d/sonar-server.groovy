import hudson.model.*
import jenkins.model.*
import hudson.plugins.sonar.*
import hudson.tools.*
import org.yaml.snakeyaml.Yaml


def env = System.getenv()

if(env.SONAR_QUBE_SERVER_CONFIG_FILE_PATH == null){
    return;
}

def parser = new Yaml()
def config = parser.load(("${env.SONAR_QUBE_SERVER_CONFIG_FILE_PATH}" as File).text)

def instance = Jenkins.getInstance()

println("Configuring SonarQube...")

def sonar_conf = instance.getDescriptor(SonarGlobalConfiguration.class)

def sonar_installations = new ArrayList<SonarInstallation>();

config.jenkins.sonarQube.servers.each {
    server ->

        def sonar_inst = new SonarInstallation(
                server.name,
                server.url,
                server.credentialsId, null, '', '', '','', null)

        sonar_installations += sonar_inst

}

sonar_conf.setInstallations((SonarInstallation[]) sonar_installations)
sonar_conf.save()

println("Finished SonarQube Configuring")
