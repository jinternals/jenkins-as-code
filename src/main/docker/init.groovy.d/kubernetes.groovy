import org.csanchez.jenkins.plugins.kubernetes.*
import jenkins.model.*

def instance = Jenkins.getInstance()

def kubernetesCloud = new KubernetesCloud('jenkins')

kubernetesCloud.setServerUrl('https://192.168.64.7:8443')
kubernetesCloud.setNamespace('jenkins')
kubernetesCloud.setJenkinsUrl('http://jenkins-ui.jenkins:8080')
kubernetesCloud.setSkipTlsVerify(false)
kubernetesCloud.setJenkinsTunnel('jenkins-discovery.jenkins:50000')
kubernetesCloud.setConnectTimeout(5)
kubernetesCloud.setReadTimeout(15)
kubernetesCloud.setMaxRequestsPerHostStr(32)
kubernetesCloud.setWaitForPodSec(600)
kubernetesCloud.setContainerCapStr(10)

instance.clouds.replace(kubernetesCloud)

instance.save()
