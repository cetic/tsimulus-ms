# Use an official Java runtime as a parent image
FROM anapsix/alpine-java

MAINTAINER Alexandre Nuttinck

# Set the working directory to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
ADD . /app

# Add the ca-certificates to wget
RUN   apk update \                                                                                                                                                                                                                        
&&   apk add ca-certificates wget \                                                                                                                                                                                                      
&&   update-ca-certificates   

# Add tsimulus-ws-1.4.jar
RUN wget https://github.com/cetic/tsimulus-ms/releases/download/1.4/tsimulus-ws-1.4.jar

# Run the jar when the container launches
CMD ["java","-jar","tsimulus-ws-1.4.jar"]