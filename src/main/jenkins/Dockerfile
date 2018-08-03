FROM jenkins/jenkins:2.135

USER root

# Install all necessary software
RUN apt-get update && apt-get install -y ruby curl

# Let's assume that we don't want to have the setup wizard
# Nor do we want to reject any parameters passed between jobs
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false \
              -Djava.awt.headless=true \
              -Dhudson.model.ParametersAction.keepUndefinedParameters=true

USER jenkins

# Install plugins
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt


#COPY ../../../data/jenkins_home/downloads /var/jenkins_home/downloads/
COPY setup-scripts/* /usr/share/jenkins/ref/init.groovy.d/

COPY seed-job.groovy /usr/share/jenkins/seed-job.groovy