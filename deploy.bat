mvn clean package
del d:\apache-tomcat-7.0.41\webapps\quzilla-wrapper-1.0.0.war
del -r  d:\apache-tomcat-7.0.41\webapps\quzilla-wrapper-1.0.0
copy target\quzilla-wrapper-1.0.0.war   d:\apache-tomcat-7.0.41\webapps\
