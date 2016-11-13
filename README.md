[![Build Status](https://travis-ci.org/cetic/rts-gen-ms.svg?branch=master)](https://travis-ci.org/cetic/rts-gen-ms)

# rts-gen-ms

A microservice for accessing the realistic time series generator.

This project intend to provide a micro-service that exposes the features of [the RTS-Gen library](https://github.com/cetic/rts-gen) through a simple HTTP connection.

It provides a runnable JAR file that contains an Akka-based microservice.

* POST /generator => Expects a JSON document describing a generator configuration as its POST parameter. The result is a sequence of comma separated lines, each of containing a value of a generated time series. 
* POST /generator/\<date\> => Expects a JSON document describing a generator configuration as its POST parameter. The date must have the yyyy-MM-dd'T'HH:mm:ss.SSS format. The result is a sequence of comma separated lines, each of containing a value of a generated time series. The sequence covers time series values from the beginning of the generation to the given date.
* POST /generator/\<d1\>/\<d2\> => Expects a JSON document describing a generator configuration as its POST parameter. The dates must have the yyyy-MM-dd'T'HH:mm:ss.SSS format. The result is a sequence of comma separated lines, each of containing a value of a generated time series. The sequence covers time series values from \<d1\> to \<d2\>.


