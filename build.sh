#!/bin/bash
echo 'Adidas Subscription Service Build and Containers run'

echo 'maven build'
mvn clean package

echo 'docker build api'
#cd adidas-subscription-api
docker image build -t adidas-subscription-api adidas-subscription-api/

echo 'docker run api'
docker container run --name adidas-subscription-api -p 8094:8094 -d adidas-subscription-api

echo 'subscription-api up and running'

echo 'docker build subscription service'
#cd ../adidas-subscription-service
docker image build -t adidas-subscription-service adidas-subscription-service/

echo 'docker run service'
docker container run --name adidas-subscription-service -p 8095:8095 -d adidas-subscription-service

echo 'subscription-service up and running'
