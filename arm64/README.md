## 64-bit ARM architecture

It exists also a docker image for 64-bit ARM architecture. The docker image has been created with the Dockerfile from the arm64 folder.

* Pull the Docker image
```
docker pull ceticasbl/tsimulus-ms-arm64
```
* Run the microservice: this command launches the tsimulus-ws.jar on port 8001.
```
docker run --name tsimulus-ms-arm64 -ti -p 8001:8001 -d ceticasbl/tsimulus-ms-arm64
```