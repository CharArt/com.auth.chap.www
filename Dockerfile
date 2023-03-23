FROM charartpav/ubuntu-tom10-java19:0.8
MAINTAINER charartpav@gmail.com
COPY ./target/chap.www-0.0.1-SNAPSHOT.war /home/apache-tomcat-10.0.27/webapps
CMD ["/home/apache-tomcat-10.0.27/bin/catalina.sh", "run"]
EXPOSE 8080


