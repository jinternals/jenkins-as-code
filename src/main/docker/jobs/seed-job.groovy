  pipelineJob('seed-job') {
    
    triggers {
        scm('H/5 * * * *')
    }

    description("Seed Job")

    definition {
        cps {
            def jobDslScript = new File('/etc/jenkins_jobs/seedDsl.groovy')
            script(jobDslScript.text)
            sandbox()
        }
    }
}
