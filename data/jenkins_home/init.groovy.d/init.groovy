import javaposse.jobdsl.dsl.DslScriptLoader
import javaposse.jobdsl.dsl.JobManagement
import javaposse.jobdsl.plugin.JenkinsJobManagement
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.impl.*
import jenkins.model.*
import hudson.plugins.groovy.*
import net.sf.json.JSONObject


println("Setup number of executors")

def instance = Jenkins.getInstance()
instance.setNumExecutors(5)


println("Creating the seed job")

File jobScript = new File('/usr/share/jenkins/seed-job.groovy')
JobManagement jobManagement = new JenkinsJobManagement(System.out, [:], new File('.'))

new DslScriptLoader(jobManagement).with { runScript(jobScript.text) }

println("Adding credentials")
SystemCredentialsProvider.getInstance().getCredentials().add(
        new UsernamePasswordCredentialsImpl(CredentialsScope.GLOBAL,
                "my_id", "my description", "user", "pass"))
SystemCredentialsProvider.getInstance().save()


println("Marking allow macro token")
Groovy.DescriptorImpl descriptor =
        (Groovy.DescriptorImpl) Jenkins.getInstance().getDescriptorOrDie(Groovy)
descriptor.configure(null, JSONObject.fromObject('''{"allowMacro":"true"}'''))