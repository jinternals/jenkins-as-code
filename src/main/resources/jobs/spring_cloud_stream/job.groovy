package jobs.spring_cloud_stream

import javaposse.jobdsl.dsl.DslFactory

// delegate of the script, if you want code completion just set it to a variable
// of DslFactory type
DslFactory factory = this

// The simplest job is the FreestyleJob
factory.job("spring-cloud-job-build") {
    // Required by the Delivery Pipeline view
    deliveryPipelineConfiguration("Build")
    // Trigger when a push to github was made
    triggers { githubPush() }
    // SCM configuration
    scm { github("jinternals/spring-cloud-job") }
    // Additional wrappers
    wrappers { colorizeOutput() }
    // Steps to be executed
    steps {
        maven{
            mavenInstallation('maven3')
            goals("clean install")
        }
    }
    // What should happen after completion of steps
    publishers {
        // let's check out tests
        archiveJunit("**/target/surefire-reports/TEST-*.xml")
        archiveArtifacts('target/*.jar')
        // automatic step
        // for manual use buildPipelineTrigger
        downstreamParameterized {
            trigger("spring-cloud-job-deploy") { triggerWithNoParameters() }
        }
    }
}

factory.job("spring-cloud-job-deploy") {
    // Required by the Delivery Pipeline view
    deliveryPipelineConfiguration("Deploy")
    // SCM configuration
    scm { github("jinternals/spring-cloud-job") }
    // Steps to be executed
    steps { shell("echo 'Deploying artifact'") }
}


factory.deliveryPipelineView("spring-cloud-job-pipeline") {
    pipelines {
        component("Deployment", "spring-cloud-job-build")
    }
    allowPipelineStart()
    showChangeLog()
    allowRebuild()
    showDescription()
    showPromotions()
    showTotalBuildTime()
}