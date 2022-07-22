#!/bin/bash
echo 'Adidas Subscription Service Build and Containers run'

echo 'maven build'
mvn clean package

echo 'stop containers'
docker-compose stop

echo 'docker run'
docker-compose up

echo 'subscription-service up and running'
