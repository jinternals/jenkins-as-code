import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.impl.*
import hudson.plugins.groovy.*
import javaposse.jobdsl.dsl.DslScriptLoader
import javaposse.jobdsl.dsl.JobManagement
import javaposse.jobdsl.plugin.JenkinsJobManagement
import jenkins.model.*

def env = System.getenv()

if(env.SEED_JOB_ENABLED == null || env.SEED_JOB_ENABLED == false){
    return;
}

println("Creating the seed job.")

File jobScript = new File('/etc/jenkins_jobs/seedJob.groovy')
JobManagement jobManagement = new JenkinsJobManagement(System.out, [:], new File('.'))

new DslScriptLoader(jobManagement).with { runScript(jobScript.text) }