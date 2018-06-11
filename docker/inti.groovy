import com.cloudbees.plugins.credentials.CredentialsScope
import com.cloudbees.plugins.credentials.SystemCredentialsProvider
import com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl
import hudson.model.JDK
import hudson.plugins.groovy.Groovy
import javaposse.jobdsl.dsl.DslScriptLoader
import javaposse.jobdsl.dsl.JobManagement
import javaposse.jobdsl.plugin.JenkinsJobManagement
import jenkins.model.Jenkins
import net.sf.json.JSONObject

File jobScript = new File('/usr/share/jenkins/seed-job.groovy')
JobManagement jobManagement = new JenkinsJobManagement(System.out, [:], new File('.'))

println "Creating the seed job"

new DslScriptLoader(jobManagement).with {
    runScript(jobScript.text)
}

println "Adding credentials"
UsernamePasswordCredentialsImpl credentials = new UsernamePasswordCredentialsImpl(CredentialsScope.GLOBAL,
        "my_id", "my description", "user", "pass")

SystemCredentialsProvider
        .getInstance()
        .getCredentials()
        .add(credentials)

SystemCredentialsProvider
        .getInstance()
        .save()

println "Adding jdk"
Jenkins
        .getInstance()
        .getJDKs()
        .add(new JDK("jdk8", "/usr/lib/jvm/java-8-openjdk-amd64"))

println "Marking allow macro token"
Groovy.DescriptorImpl descriptor =
        (Groovy.DescriptorImpl) Jenkins.getInstance().getDescriptorOrDie(Groovy)
descriptor.configure(null, JSONObject.fromObject('''{"allowMacro":"true"}'''))