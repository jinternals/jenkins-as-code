import hudson.tasks.Maven
import hudson.tasks.Maven.MavenInstallation;
import hudson.tools.InstallSourceProperty;
import hudson.tools.ToolProperty;
import hudson.tools.ToolPropertyDescriptor
import hudson.tools.ZipExtractionInstaller;
import hudson.util.DescribableList
import jenkins.model.Jenkins;

def mavenExtension = Jenkins.instance.getExtensionList(Maven.DescriptorImpl.class)[0]

List<MavenInstallation> mavenInstallations = []

mavenTool = ['name': 'maven3', 'url': 'file:/var/jenkins_home/downloads/apache-maven-3.5.4-bin.tar.gz', 'subdir': 'apache-maven-3.5.4', 'label': 'apache-maven-3.5.4']

def describableList = new DescribableList<ToolProperty<?>, ToolPropertyDescriptor>()
def mavenInstaller = new ZipExtractionInstaller(mavenTool.label as String, mavenTool.url as String, mavenTool.subdir as String);
describableList.add(new InstallSourceProperty([mavenInstaller]))

mavenInstallations.add(new MavenInstallation(mavenTool.name as String, "", describableList))

mavenExtension.setInstallations(mavenInstallations.toArray(new MavenInstallation[mavenInstallations.size()]))

mavenExtension.save()