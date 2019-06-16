FROM jboss/wildfly:9.0.2.Final

# Add all supported JDBC drivers
ADD docker/ojdbc6.jar /opt/jboss/wildfly/standalone/deployments/
ADD docker/ojdbc7.jar /opt/jboss/wildfly/standalone/deployments/
ADD docker/sqljdbc4.jar /opt/jboss/wildfly/standalone/deployments/

ADD wshubear/target/wshub.ear /opt/jboss/wildfly/standalone/deployments/
ADD docker/standalone.xml /opt/jboss/wildfly/standalone/configuration/

EXPOSE 8082/tcp
EXPOSE 9090/tcp