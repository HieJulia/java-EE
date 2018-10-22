#!/usr/bin/env bash

docker build -t hello-javaee8:1.0 .

docker run -it -p 8080:8080 hello-javaee8:1.0