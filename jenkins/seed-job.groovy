import javaposse.jobdsl.dsl.DslFactory

DslFactory factory = this

factory.job('seed-job') {
    jdk('jdk8')
    triggers { githubPush() }
    scm { github("jinternals/jenkins-as-code") }
    wrappers { colorizeOutput() }

    steps {
        maven{
            mavenInstallation('maven3')
            goals("clean install")
        }

        dsl {
            external('jobs/spring_cloud_stream.groovy')
            removeAction('DISABLE')
            removeViewAction('DELETE')
            ignoreExisting(false)
        }
    }
}