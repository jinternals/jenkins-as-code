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
        shell('jenkins-jobs update --delete-old -r ./configuration')
    }

}
