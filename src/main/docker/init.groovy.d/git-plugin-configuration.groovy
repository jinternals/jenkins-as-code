import jenkins.model.*

def inst = Jenkins.getInstance()

def desc = inst.getDescriptor("hudson.plugins.git.GitSCM")

desc.setGlobalConfigName("Mradul Pandey")
desc.setGlobalConfigEmail("pandeymradul@gmail.com")

desc.save()