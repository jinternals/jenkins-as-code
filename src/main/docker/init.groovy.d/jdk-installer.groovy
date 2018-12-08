import hudson.model.JDK
import hudson.tools.InstallSourceProperty
import hudson.tools.ZipExtractionInstaller

javaTools = [
        [
                'name'  : 'jdk8',
                //'label' : 'jdk8',
                'url'   : 'file:/var/tools/jdk-8u181-linux-x64.tar.gz',
                'subdir': 'jdk1.8.0_181'
        ]
]

def descriptor = new JDK.DescriptorImpl();

List<JDK> installations = []

javaTools.each { javaTool ->

    println("Setting up : ${javaTool.name}")

    def installer = new ZipExtractionInstaller(
            javaTool.label as String,
            javaTool.url as String,
            javaTool.subdir as String);

    def jdk = new JDK(javaTool.name as String,
            null,
            [
                    new InstallSourceProperty([installer])
            ])

    installations.add(jdk)

}

descriptor.setInstallations(installations.toArray(new JDK[installations.size()]))

descriptor.save()