import javaposse.jobdsl.dsl.DslFactory

// delegate of the script, if you want code completion just set it to a variable
// of DslFactory type
DslFactory factory = this

// The simplest job is the FreestyleJob
factory.job("spring-cloud-stream-build") {
    // Required by the Delivery Pipeline view
    deliveryPipelineConfiguration("Build")
    // Trigger when a push to github was made
    triggers { githubPush() }
    // SCM configuration
    scm { github("jinternals/spring-cloud-stream") }
    // Additional wrappers
    wrappers { colorizeOutput() }
    // Steps to be executed
    steps { shell("mvn clean install") }
    // What should happen after completion of steps
    publishers {
        // let's check out tests
        archiveJunit("**/target/surefire-reports/TEST-*.xml")
        archiveArtifacts('target/*.jar')
        // automatic step
        // for manual use buildPipelineTrigger
        downstreamParameterized {
            trigger("spring-cloud-stream-deploy") { triggerWithNoParameters() }
        }
    }
}

factory.job("spring-cloud-stream-deploy") {
    // Required by the Delivery Pipeline view
    deliveryPipelineConfiguration("Deploy")
    // SCM configuration
    scm { github("jinternals/spring-cloud-stream") }
    // Steps to be executed
    steps { shell("echo 'Deploying artifact'") }
}


factory.deliveryPipelineView("spring-cloud-stream-pipeline") {
    pipelines {
        component("Deployment", "spring-cloud-stream-build")
    }
    allowPipelineStart()
    showChangeLog()
    allowRebuild()
    showDescription()
    showPromotions()
    showTotalBuildTime()
}