# Use an official Java runtime as a parent image
FROM anapsix/alpine-java

MAINTAINER Alexandre Nuttinck

# Set the working directory to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
ADD . /app

# Run the jar when the container launches
CMD ["java","-jar","tsimulus-ws-1.4.jar"]