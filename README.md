[![License](http://img.shields.io/:license-Apache%202-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)

# Tsimulus-ms

A microservice for accessing the TSimulus time series generator.

This project intend to provide a micro-service that exposes the features of [the TSimulus library](https://github.com/cetic/tsimulus) through a simple HTTP connection.

It provides a runnable JAR file that contains an Akka-based microservice.

# How to use it?

* Download [the last released version](https://github.com/cetic/tsimulus-ms/releases) of the service as a runnable JAR archive.
* Run it:

```bash
> java -jar tsimulus-ws.jar -h 0.0.0.0 -p 8001
```

This command will ask the service to listen to incoming connections on port 8001 from any client. Replace *0.0.0.0* by *localhost* for limiting the access to the service to clients running on your machine.

Once running, the service can be accessed by submitting POST request to port 8001:

* POST /generator => Expects a JSON document describing a generator configuration as its POST parameter. The result is a sequence of comma separated lines, each of containing a value of a generated time series. 
* POST /generator/\<date\> => Expects a JSON document describing a generator configuration as its POST parameter. The date must have the yyyy-MM-dd'T'HH:mm:ss.SSS format. The result is a sequence of comma separated lines, each of containing a value of a generated time series. The sequence covers time series values from the beginning of the generation to the given date.
* POST /generator/\<d1\>/\<d2\> => Expects a JSON document describing a generator configuration as its POST parameter. The dates must have the yyyy-MM-dd'T'HH:mm:ss.SSS format. The result is a sequence of comma separated lines, each of containing a value of a generated time series. The sequence covers time series values from \<d1\> to \<d2\>.

The syntax of JSON documents is described in the TSimulus [library documentation](https://tsimulus.readthedocs.io/en/latest/).

# Tsimulus-ms with Docker

You can also retrieve the Docker image which contains the Tsimulus microservice. The Docker image has been created with a Dockerfile.

* Pull the Docker image
```
docker pull ceticasbl/tsimulus-ms
```
* Run the microservice: this command launches the Tsimulus microservice on port 8001.
```
sudo docker run --name tsimulus-ms -ti -p 8001:8001 -d ceticasbl/tsimulus-ms
```

When it's done, as explained in the previous section "How to use it?", the service can be accessed by submitting POST request to port 8001.

## 64-bit ARM architecture

It exists also a docker image for 64-bit ARM architecture. The docker image has been created with the Dockerfile from the arm64 folder.

* Pull the Docker image
```
docker pull ceticasbl/tsimulus-ms-arm64
```
* Run the microservice: this command launches the tsimulus-ws.jar on port 8001.
```
sudo docker run --name tsimulus-ms-arm64 -ti -p 8001:8001 -d ceticasbl/tsimulus-ms-arm64
```

# Tsimulus-ms with OpenShift

You can also run the microservice with OpenShift. For that, we use Kubernetes files which can be found in the kubernetes folder.
In your OpenShift project, first, create the Kubernetes deployment:
```
oc create -f tsimulus-deployment.yml
```
Then, create the Kubernetes service:
```
oc create -f tsimulus-service.yml
```
Finally, create a route by exposing the service tsimulus-ms.
```
oc expose service tsimulus-ms
```

Now the service is accessible at http://tsimulus-ms-tsimulus.apps.10.2.2.2.xip.io/.
When it's done, as explained in the first section "How to use it?", the service can be accessed by submitting POST request to port 8001.

