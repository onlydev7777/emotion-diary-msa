docker run -d --name zipkin --network emotion-service-network \
  -p 9411:9411 openzipkin/zipkin