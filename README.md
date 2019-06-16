# EAM WSHub
EAM WSHub is Web application that provides a simplified REST/SOAP facade to Infor EAM. 
EAM WSHub is available as a Docker image here: https://hub.docker.com/r/cerneam/eam-wshub

## Configuration
The docker image needs to be parametrized with environment variables.



| Variable        | Required?  | Default value |
| ------------- | -----:|---------:|
| INFOR_WS_URL           | **Yes** |  |
| INFOR_TENANT         | **Yes** |  |

Please not that for the moment the database connection is not supported and the non-mandatory parameters above won't be considered.

You can for instance store your environment variables in a dedicated .env file:

```
INFOR_WS_URL=<url>
INFOR_TENANT=<tenant>
```


## Run

The docker container exposes the following ports:

| Port        | Description  |
| ------------- | -----:|
| 8082          | EAM WSHub | 
| 9090          | JBoss Management Port |

Once you have your own environment variables set up, you can start a new docker container:
```
docker run -p 8082:8082 -p 9090:9090 --env-file .env cerneam/eam-wshub:latest
``` 

Once the docker container is started, our WSDL is accessible on port 8082 at the endpoint `/SOAPWS/SOAP?wsdl`
The REST web services are available at the endpoint `/RESTWS/REST/...` (consult WSHub_RESTAPIs postman project for further details).

## License
This software is published under the GNU General Public License v3.0 or later.