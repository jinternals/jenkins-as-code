import hudson.model.*
import jenkins.*
import jenkins.model.*
import org.kohsuke.stapler.*
import org.quality.gates.jenkins.plugin.*
import java.lang.reflect.Field
import org.yaml.snakeyaml.Yaml

def env = System.getenv()

if(env.SONARE_CONFIG_FILE_PATH == null){
    return;
}

def parser = new Yaml()
def config = parser.load(("${env.SONARE_CONFIG_FILE_PATH}" as File).text)

def descriptor = Jenkins.instance.getDescriptorByType(
        org.quality.gates.jenkins.plugin.GlobalConfig.class)


Field sonarQubeAuth = descriptor.class.getDeclaredField("listOfGlobalConfigData")
sonarQubeAuth.setAccessible(true)

sonarQubeAuthArray = new ArrayList<GlobalConfigDataForSonarInstance>()


config.jenkins.sonarQube.qulaityGate.each
        { gate ->
            sonarInstance = new GlobalConfigDataForSonarInstance()
            sonarInstance.setName(gate.name)
            sonarInstance.setUsername(gate.username)
            sonarInstance.setPass(gate.password)
            sonarInstance.setSonarUrl(gate.url)
            sonarInstance.setTimeToWait(gate.timeToWait)
            sonarInstance.setMaxWaitTime(gate.maxWaitTime)
            sonarInstance.setToken(gate.token)

            sonarQubeAuthArray.add(sonarInstance)
        }


sonarQubeAuth.set(descriptor, sonarQubeAuthArray)

descriptor.save()