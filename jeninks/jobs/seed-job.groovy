  pipelineJob('seed-job') {
    
    triggers {
        githubPush()
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
