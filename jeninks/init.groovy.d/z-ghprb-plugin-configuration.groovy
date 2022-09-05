import hudson.util.Secret
import jenkins.model.*
import org.jenkinsci.plugins.ghprb.*

import java.lang.reflect.Field

import org.yaml.snakeyaml.Yaml

def env = System.getenv()

if(env.GHPRB_CONFIG_FILE_PATH == null){
    return;
}

def parser = new Yaml()
def config = parser.load(("${env.GHPRB_CONFIG_FILE_PATH}" as File).text)


def descriptor = Jenkins.instance.getDescriptorByType(org.jenkinsci.plugins.ghprb.GhprbTrigger.DescriptorImpl.class)
Field githubAuth = descriptor.class.getDeclaredField("githubAuth")
githubAuth.setAccessible(true)

githubAuths = new ArrayList<GhprbGitHubAuth>()

config.jenkins.ghprbs.each{ ghprb ->

      def secret = ghprb.secret == null? null : Secret.fromString(ghprb.secret)
      def ghprbGitHubAuth = new GhprbGitHubAuth(ghprb.apiUrl,
              ghprb.jenkinsUrl, ghprb.credentialsId,
              ghprb.description, ghprb.id, secret)

    githubAuths.add(ghprbGitHubAuth)
}

githubAuth.set(descriptor, githubAuths)

descriptor.save()
