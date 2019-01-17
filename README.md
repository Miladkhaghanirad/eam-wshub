# EAM WSHub
EAM WSHub is Web application that provides a simplified REST/SOAP facade to Infor EAM. 
EAM WSHub is available as a Docker image here: https://hub.docker.com/r/cerneam/eam-wshub

## Configuration
The docker image needs to be parametrized with environment variables.



| Variable        | Required?  | Default value |
| ------------- | -----:|---------:|
| INFOR_WS_URL           | **Yes** |  |
| INFOR_TENANT         | **Yes** |  |
| INFOR_ORGANIZATION_CODE         | **Yes** |  |
| DB_CONNECTION_URL   | No |  |
| DB_DRIVER_CLASS   | No |  |
| DB_USERNAME   | No |  |
| DB_PASSWORD   | No |  |
| DB_DRIVER   | No |  |
| DB_MIN_POOL_SIZE   | No | 5 |
| DB_INITIAL_POOL_SIZE   | No | 5 |
| DB_MAX_POOL_SIZE   | No | 40 |
| DB_VALID_CONNECTION_CHECKER   | No |  |
| DB_STALE_CONNECTION_CHECKER   | No |  |
| DB_EXCEPTION_SORTER   | No |  |

You can for instance store your environment variables in a dedicated .env file:

```
INFOR_WS_URL=<url>
INFOR_TENANT=<tenant>
INFOR_ORGANIZATION_CODE=<organization-code>
DB_CONNECTION_URL=<db-url>
DB_DRIVER_CLASS=oracle.jdbc.OracleDriver
DB_USERNAME=<db-username>
DB_PASSWORD=<db-password>
DB_DRIVER=ojdbc6.jar
DB_MIN_POOL_SIZE=5
DB_INITIAL_POOL_SIZE=5
DB_MAX_POOL_SIZE=40
DB_VALID_CONNECTION_CHECKER=org.jboss.jca.adapters.jdbc.extensions.oracle.OracleValidConnectionChecker
DB_STALE_CONNECTION_CHECKER=org.jboss.jca.adapters.jdbc.extensions.oracle.OracleStaleConnectionChecker
DB_EXCEPTION_SORTER=org.jboss.jca.adapters.jdbc.extensions.oracle.OracleExceptionSorter
```


## Run

The docker container exposes the following ports:

| Port        | Description  |
| ------------- | -----:|
| 8080          | EAM WSHub | 
| 9090          | JBoss Management Port |

Once you have your own environment variables set up, you can start a new docker container:
```
docker run -p 8080:8080 -p 9090:9090 --env-file .env cerneam/eam-wshub:latest
``` 

Once the docker container is started, our WSDL is accessible on port 8080 at the endpoint `/SOAPWS/SOAP?wsdl`
The REST web services are available at the endpoint `/RESTWS/REST?wsdl`

## License
This software is published under the GNU General Public License v3.0 or later.