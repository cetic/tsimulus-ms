# Repository contains Dockerfile of Scala and sbt.
FROM hseeberger/scala-sbt

MAINTAINER Alexandre Nuttinck

# Set the working directory to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
ADD . /app

# EXPOSE On port 8001
EXPOSE 8001

# Run the sbt microservice
CMD ["sbt","run"]