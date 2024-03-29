import hudson.security.Permission
import jenkins.model.Jenkins
import org.yaml.snakeyaml.Yaml
import hudson.security.HudsonPrivateSecurityRealm

authz_strategy_config = [
        strategy: 'GlobalMatrixAuthorizationStrategy',
        role_permissions: [
                admin: ['Overall Administer'],
                read:['Overall Read', 'Job Read', 'View Read'],
                dev: ['Overall Read', 'Job Read', 'View Read','Job Build','Job Cancel']
        ]
]

def env = System.getenv()

if(env.AUTH_CREDENTIALS_CONFIG_FILE_PATH == null){
    return;
}

def parser = new Yaml()
def config = parser.load(("${env.AUTH_CREDENTIALS_CONFIG_FILE_PATH}" as File).text)

Jenkins.instance.setSecurityRealm(new HudsonPrivateSecurityRealm(false))

/**
 * FUNCTIONS AND SETUP CODE
 */
String shortName(Permission p) {
    p.id.tokenize('.')[-2..-1].join(' ')
            .replace('Hudson','Overall')
            .replace('Computer', 'Agent')
            .replace('Item', 'Job')
            .replace('CredentialsProvider', 'Credentials')
}

private Map getCurrentPermissions(Map config = [:]) {
    Map currentPermissions = [:].withDefault { [].toSet() }
    if(!('getGrantedPermissions' in Jenkins.instance.authorizationStrategy.metaClass.methods*.name.sort().unique())) {
        return currentPermissions
    }
    Closure merger = { Map nmap, Map m ->
        m.each { k, v ->
            nmap[k] += v
        }
    }
    Jenkins.instance.authorizationStrategy.grantedPermissions.collect { permission, userList ->
        userList.collect { user ->
            [ (user): shortName(permission) ]
        }
    }.flatten().each merger.curry(currentPermissions)
    currentPermissions
}

private boolean isConfigurationEqual(Map config) {
    Map currentPermissions = getCurrentPermissions(config)
    Jenkins.instance.authorizationStrategy.class.name.endsWith(config['strategy']) &&
            !(false in config['role_permissions'].collect { k, v -> currentPermissions[k] == v.toSet() }) &&
            currentPermissions.keySet() == config['role_permissions'].keySet()
}

private boolean isValidConfig(def config, List<String> validPermissions) {
    Map currentPermissions = getCurrentPermissions()
    config instanceof Map &&
            config.keySet().containsAll(['strategy', 'role_permissions']) &&
            config['strategy'] &&
            config['strategy'] instanceof String &&
            config['strategy'] in ['GlobalMatrixAuthorizationStrategy', 'ProjectMatrixAuthorizationStrategy'] &&
            config['role_permissions'] &&
            !(false in config['role_permissions'].collect { k, v ->
                k instanceof String &&
                        (v instanceof List || v instanceof Set) &&
                        !(false in v.collect {
                            validPermissions.contains(it)
                        })
            })
}

Map<String, Permission> permissionIds = Permission.all.findAll { permission ->
    List<String> nonConfigurablePerms = ['RunScripts', 'UploadPlugins', 'ConfigureUpdateCenter']
    permission.enabled &&
            !permission.id.startsWith('hudson.security.Permission') &&
            !(true in nonConfigurablePerms.collect { permission.id.endsWith(it) })
}.collect { permission ->
    [ (shortName(permission)): permission ]
}.sum()

/**
 * MAIN EXECUTION
 */

if(!binding.hasVariable('authz_strategy_config')) {
    authz_strategy_config = [:]
}

if(!isValidConfig(authz_strategy_config, permissionIds.keySet().toList())) {
    println([
            'Skip configuring matrix authorization strategy because no valid config was provided.',
            'Available Authorization Strategies:\n    GlobalMatrixAuthorizationStrategy\n    ProjectMatrixAuthorizationStrategy',
            "Available Permissions:\n    ${permissionIds.keySet().join('\n    ')}"
    ].join('\n'))
    return
}

if(isConfigurationEqual(authz_strategy_config)) {
    println "Nothing changed.  ${authz_strategy_config['strategy']} authorization strategy already configured."
    return
}

println "Configuring authorization strategy ${authz_strategy_config['strategy']}"

def authz_strategy = Class.forName("hudson.security.${authz_strategy_config['strategy']}").newInstance()

config.jenkins.auth.credentials.each
        { credential ->
            print "Setting up ${credential.username}"

            def user =  Jenkins.instance.getSecurityRealm().createAccount(credential.username, credential.password)
            user.save()

            def permissions = authz_strategy_config['role_permissions'].get(credential.type)

            permissions.each { p ->
                authz_strategy.add(permissionIds[p], credential.username)
            }
        }

// configure global authorization
Jenkins.instance.authorizationStrategy = authz_strategy

// save settings to persist across restarts
Jenkins.instance.save()