FROM tomcat:8.0
EXPOSE 9999

ARG WAR_FILE=target/*.war

COPY ${WAR_FILE} /usr/local/tomcat/webapps/

MAINTAINER rathore_kratika109
