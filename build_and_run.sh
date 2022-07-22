#!/bin/bash
echo 'Adidas Subscription Service Build and Containers run'

echo 'maven build'
mvn clean package

echo 'remove containers'
docker-compose rm -f -s

docker image rm adidas-subscription-api -f
docker image rm adidas-subscription-mail -f
docker image rm adidas-subscription-service -f

echo 'docker run'
docker-compose up

echo 'subscription-service up and running'
