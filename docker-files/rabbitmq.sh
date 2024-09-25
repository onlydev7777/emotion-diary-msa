docker run -d --name ediary-rabbitmq --network ediary-service-network \
  -p 15673:15672 -p 5673:5672 -p 15672:15671 -p 4370:4369 \
  -e RABBITMQ_DEFAULT_USER=guest -e RABBITMQ_DEFAULT_PASS=guest \
  ediary-rabbitmq