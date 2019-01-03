#!/bin/bash
# mvn clean package -Dmaven.repo.local=.m2
java -jar -Dspring.profiles.active=docker target/weather-api-0.0.1-SNAPSHOT.jar
