import javaposse.jobdsl.dsl.DslFactory

DslFactory factory = this

factory.job('seed-job') {
    jdk('jdk8')
    triggers { githubPush() }
    scm { github("jinternals/jenkins-as-code") }
    wrappers { colorizeOutput() }
    steps {
        shell("./mvnw clean install")
        dsl {
            external('jobs/demo_job.groovy')
            removeAction('DISABLE')
            removeViewAction('DELETE')
            ignoreExisting(false)
        }
    }
}