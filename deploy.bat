set app=jy-1
del d:\apache-tomcat-7.0.41\webapps\%app%.war
del -r  d:\apache-tomcat-7.0.41\webapps\%app%
echo "start  copy"
copy target\%app%.war   d:\apache-tomcat-7.0.41\webapps\
d:\apache-tomcat-7.0.41\bin\shutdown.bat
d:\apache-tomcat-7.0.41\bin\startup.bat
