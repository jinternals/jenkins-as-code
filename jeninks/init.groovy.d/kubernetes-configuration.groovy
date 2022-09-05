import org.csanchez.jenkins.plugins.kubernetes.*
import jenkins.model.*
import org.yaml.snakeyaml.Yaml


def env = System.getenv()

if(env.KUBERNETES_CONFIG_FILE_PATH == null){
    return;
}

def parser = new Yaml()
def config = parser.load(("${env.KUBERNETES_CONFIG_FILE_PATH}" as File).text)
def instance = Jenkins.getInstance()

def kubernetesClouds = new ArrayList<KubernetesCloud>();

config.jenkins.kubernetes.clouds.each{ cloud ->
    def kubernetesCloud = new KubernetesCloud(cloud.name)
    kubernetesCloud.setServerUrl(cloud.url)
    kubernetesCloud.setNamespace(cloud.namespace)
    kubernetesCloud.setJenkinsUrl(cloud.jenkinsUrl)
    kubernetesCloud.setSkipTlsVerify(cloud.skipTlsVerify)
    kubernetesCloud.setJenkinsTunnel(cloud.jenkinsTunnel)
    kubernetesCloud.setConnectTimeout(cloud.connectTimeout)
    kubernetesCloud.setReadTimeout(cloud.readTimeout)
    kubernetesCloud.setMaxRequestsPerHostStr(cloud.maxRequestsPerHostStr)
    kubernetesCloud.setWaitForPodSec(cloud.waitForPodSec)
    kubernetesCloud.setContainerCapStr(cloud.containerCapStr)
    kubernetesCloud.setCredentialsId(cloud.credentialsId)

    kubernetesClouds.add(kubernetesCloud);
}

instance.clouds.clear()
instance.clouds.addAll(kubernetesClouds)
instance.save()

instance.clouds