docker run -d --name discovery --network emotion-service-network \
  -e "spring.cloud.config.uri=http://config-serice:8888" \
  -p 8761:8761 discovery