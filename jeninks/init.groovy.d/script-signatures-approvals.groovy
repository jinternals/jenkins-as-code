def signatures = ['staticMethod java.lang.System getenv java.lang.String']

def approver = org.jenkinsci.plugins.scriptsecurity.scripts.ScriptApproval.get()
signatures.each{ signature ->
    approver.approveSignature(signature)
}
