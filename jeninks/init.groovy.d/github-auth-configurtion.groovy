import jenkins.model.*
import hudson.security.SecurityRealm
import org.jenkinsci.plugins.GithubSecurityRealm
import org.yaml.snakeyaml.Yaml
import org.jenkinsci.plugins.GithubAuthorizationStrategy
import hudson.security.AuthorizationStrategy

def env = System.getenv()

if (env.GITHUB_OAUTH_CONFIG_FILE_PATH == null) {
    return;
}
def instance = Jenkins.getInstance()
def parser = new Yaml()
def config = parser.load(("${env.GITHUB_OAUTH_CONFIG_FILE_PATH}" as File).text)

String githubWebUri = config.jenkins.auth.github.githubWebUri
String githubApiUri = config.jenkins.auth.github.githubApiUri
String clientID = config.jenkins.auth.github.clientID
String clientSecret = config.jenkins.auth.github.clientSecret
String oauthScopes = config.jenkins.auth.github.oauthScopes

SecurityRealm github_realm = new GithubSecurityRealm(githubWebUri, githubApiUri, clientID, clientSecret, oauthScopes)
//check for equality, no need to modify the runtime if no settings changed
if (!github_realm.equals(instance.getSecurityRealm())) {
    instance.setSecurityRealm(github_realm)
    instance.save()
}

if (config.jenkins.auth.github.authorization == true) {
//permissions are ordered similar to web UI
//Admin User Names
    String adminUserNames = config.jenkins.auth.github.adminUserNames
//Participant in Organization
    String organizationNames = config.jenkins.auth.github.organizationNames
//Use Github repository permissions
    boolean useRepositoryPermissions = true
//Grant READ permissions to all Authenticated Users
    boolean authenticatedUserReadPermission = true
//Grant CREATE Job permissions to all Authenticated Users
    boolean authenticatedUserCreateJobPermission = false
//Grant READ permissions for /github-webhook
    boolean allowGithubWebHookPermission = false
//Grant READ permissions for /cc.xml
    boolean allowCcTrayPermission = false
//Grant READ permissions for Anonymous Users
    boolean allowAnonymousReadPermission = false
//Grant ViewStatus permissions for Anonymous Users
    boolean allowAnonymousJobStatusPermission = false

    AuthorizationStrategy github_authorization = new GithubAuthorizationStrategy(adminUserNames,
            authenticatedUserReadPermission,
            useRepositoryPermissions,
            authenticatedUserCreateJobPermission,
            organizationNames,
            allowGithubWebHookPermission,
            allowCcTrayPermission,
            allowAnonymousReadPermission,
            allowAnonymousJobStatusPermission)

//check for equality, no need to modify the runtime if no settings changed
    if (!github_authorization.equals(instance.getAuthorizationStrategy())) {
        instance.setAuthorizationStrategy(github_authorization)
        instance.save()
    }
}
