#!/bin/bash

IMAGE="laxsrbija/wmth:latest"

docker build -t $IMAGE .
docker push $IMAGE
docker rmi $IMAGE
