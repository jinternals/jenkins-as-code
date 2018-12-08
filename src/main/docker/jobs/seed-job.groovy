job("seed-job") {

    scm {
        git {
            remote {
                github("jinternals/jenkins-jobs-configuration")
            }
        }
    }

    triggers {
        scm('*/15 * * * *')
    }

    steps {
        shell('jenkins-jobs --conf /etc/jenkins_jobs/jenkins_jobs.ini update --delete-old -r ./configuration')
    }
}
