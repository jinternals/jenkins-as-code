@Library("jenkins-demo-shared-libraries") _

seedPipeline(
        gitBranch: "master",
        gitCredentialId: "github",
        gitRepository: "https://github.com/jinternals/jenkins-jobs-configuration.git"
)
