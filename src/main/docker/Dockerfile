FROM jenkins/jenkins:2.150.1

USER root

# Install all necessary software
RUN apt-get update
RUN echo 'debconf debconf/frontend select Noninteractive' | debconf-set-selections
RUN apt-get install -y --no-install-recommends apt-utils

RUN apt-get install -y python-pip
RUN pip install --upgrade pip
RUN pip install jenkins-job-builder


# Let's assume that we don't want to have the setup wizard
# Nor do we want to reject any parameters passed between jobs
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false \
              -Djava.awt.headless=true \
              -Dhudson.model.ParametersAction.keepUndefinedParameters=true

#Set user and password
ENV JENKINS_USER jenkins
ENV JENKINS_PASS jenkins

USER jenkins

# Install plugins
COPY plugins/plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt


COPY jenkins-job-builder/jenkins_jobs.ini /etc/jenkins_jobs/jenkins_jobs.ini
COPY jobs/seed-job.groovy /etc/jenkins_jobs/seed-job.groovy

#COPY ../../../data/jenkins_home/downloads /var/jenkins_home/downloads/
COPY init.groovy.d/* /usr/share/jenkins/ref/init.groovy.d/

