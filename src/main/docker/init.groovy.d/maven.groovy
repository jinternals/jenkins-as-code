import hudson.tasks.Maven
import hudson.tasks.Maven.MavenInstallation;
import hudson.tools.InstallSourceProperty;
import hudson.tools.ToolProperty;
import hudson.tools.ToolPropertyDescriptor
import hudson.tools.ZipExtractionInstaller;
import hudson.util.DescribableList
import jenkins.model.Jenkins;

mavenTool = ['name': 'maven3', 'url': 'file:/var/tools/apache-maven-3.5.4-bin.tar.gz', 'subdir': 'apache-maven-3.5.4']

def extensions = Jenkins.instance.getExtensionList(Maven.DescriptorImpl.class)[0]

List<MavenInstallation> installations = []


println("Setting up tool: ${mavenTool.name} ")

def describableList = new DescribableList<ToolProperty<?>, ToolPropertyDescriptor>()
def installer = new ZipExtractionInstaller(mavenTool.label as String, mavenTool.url as String, mavenTool.subdir as String);

describableList.add(new InstallSourceProperty([installer]))

installations.add(new MavenInstallation(mavenTool.name as String, "", describableList))

extensions.setInstallations(installations.toArray(new MavenInstallation[installations.size()]))

extensions.save()