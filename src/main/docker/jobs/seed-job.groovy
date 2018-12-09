pipelineJob('seed-job') {

    def repo = 'https://github.com/jinternals/jenkins-jobs-configuration.git'

    triggers {
        scm('H/5 * * * *')
    }

    description("Seed Job")

    definition {
        cpsScm {
            scm {
                git {
                    remote { url(repo) }
                    branches('master')
                    scriptPath('Jenkinsfile')
                    extensions { }  // required as otherwise it may try to tag the repo, which you may not want
                }
            }
        }
    }
}