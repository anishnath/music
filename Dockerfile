FROM anishnath/dockerfile:centos7.mono
LABEL org.label-schema.schema-version="1.0.0-demo" \
    maintainer="anish2good@yahoo.co.in" \
    org.label-schema.vcs-description="Notmusic Lib openjdk8" \
    org.label-schema.docker.cmd="docker exec " \
    image-size="71.6MB" \
    ram-usage="13.2MB to 70MB" \
    cpu-usage="Low"

ENV TOMCAT_MAJOR=8 \
    TOMCAT_VERSION=8.5.3 \
    TOMCAT_HOME=/opt/tomcat \
    CATALINA_HOME=/opt/tomcat \
    CATALINA_OUT=/dev/null

RUN yum -y update && \
   curl -jksSL -o /tmp/apache-tomcat.tar.gz http://archive.apache.org/dist/tomcat/tomcat-${TOMCAT_MAJOR}/v${TOMCAT_VERSION}/bin/apache-tomcat-${TOMCAT_VERSION}.tar.gz && \
   gunzip /tmp/apache-tomcat.tar.gz && \
   tar -C /opt -xf /tmp/apache-tomcat.tar && \
   ln -s /opt/apache-tomcat-${TOMCAT_VERSION} ${TOMCAT_HOME} && \
   rm -rf ${TOMCAT_HOME}/webapps/*

COPY target/ROOT.war ${TOMCAT_HOME}/webapps/
CMD ["/opt/tomcat/bin/catalina.sh", "run"]
EXPOSE 8080