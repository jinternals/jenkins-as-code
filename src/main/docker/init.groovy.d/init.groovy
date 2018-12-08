import javaposse.jobdsl.dsl.DslScriptLoader
import javaposse.jobdsl.dsl.JobManagement
import javaposse.jobdsl.plugin.JenkinsJobManagement
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.impl.*
import jenkins.model.*
import hudson.plugins.groovy.*
import net.sf.json.JSONObject



println("Creating the seed job")

File jobScript = new File('/etc/jenkins_jobs/seed-job.groovy')
JobManagement jobManagement = new JenkinsJobManagement(System.out, [:], new File('.'))

new DslScriptLoader(jobManagement).with { runScript(jobScript.text) }


println("Marking allow macro token")
Groovy.DescriptorImpl descriptor =
        (Groovy.DescriptorImpl) Jenkins.getInstance().getDescriptorOrDie(Groovy)
descriptor.configure(null, JSONObject.fromObject('''{"allowMacro":"true"}'''))