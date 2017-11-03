# Use an official Java runtime as a parent image
FROM arm64v8/openjdk

MAINTAINER Alexandre Nuttinck

# Set the working directory to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
ADD . /app

# Add tsimulus-ws-1.4.jar
RUN wget https://github.com/cetic/tsimulus-ms/releases/download/1.4/tsimulus-ws-1.4.jar

# Run the jar when the container launches
CMD ["java","-jar","tsimulus-ws-1.4.jar"]