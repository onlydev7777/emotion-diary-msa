docker run -d --name config-service --network emotion-service-network \
  -e "spring.rabbitmq.host=rabbitmq" -e "spring.profiles.active=default" \
  -p 8888:8888 config-service