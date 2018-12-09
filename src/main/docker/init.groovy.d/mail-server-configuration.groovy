import jenkins.model.*

def inst = Jenkins.getInstance()

def desc = inst.getDescriptor("hudson.tasks.Mailer")

//desc.setSmtpAuth("[SMTP user]", "[SMTP password]")
//desc.setReplyToAddress("[reply to email address]")
//desc.setSmtpHost("[SMTP host]")
//desc.setUseSsl(false)
//desc.setSmtpPort("[SMTP port]")
//desc.setCharset("[character set]")
//
//desc.save()