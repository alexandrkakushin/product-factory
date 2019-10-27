#!/bin/bash
# Развертывание базы данных на localhost + Docker

POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
POSTGRES_DB=pf_development
POSTGRES_OUT_PORT=5432

docker_ps_output=$(docker ps | grep "0.0.0.0:5432->5432/tcp")
if [ -z "$docker_ps_output" ] ; then
  printf "\nЗапуск образа Docker\n"
  docker run -d -e POSTGRES_USER=${POSTGRES_USER} -e POSTGRES_PASSWORD=${POSTGRES_PASSWORD} -e POSTGRES_DB=${POSTGRES_DB} -p ${POSTGRES_OUT_PORT}:5432 postgres:9.4 ;
else
  printf "\nОбраз Docker уже запущен"
fi

exit 0
