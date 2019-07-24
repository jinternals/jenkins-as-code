FROM jenkins/jenkins:2.176.1

# Let's assume that we don't want to have the setup wizard
# Nor do we want to reject any parameters passed between jobs
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false \
              -Djava.awt.headless=true \
              -Dhudson.model.ParametersAction.keepUndefinedParameters=true

# Install plugins
COPY plugins/plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt


COPY jenkins-job-builder/jenkins_jobs.ini /etc/jenkins_jobs/jenkins_jobs.ini
COPY jobs/* /etc/jenkins_jobs/

#COPY ../../../data/jenkins_home/downloads /var/jenkins_home/downloads/
COPY init.groovy.d/* /usr/share/jenkins/ref/init.groovy.d/

