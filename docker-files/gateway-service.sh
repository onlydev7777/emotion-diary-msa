docker run -d --name gateway-service --network emotion-service-network \
	-e "spring.cloud.config.uri=http://config-service:8888" \
	-e "spring.rabbitmq.host=rabbitmq" \
	-e "eureka.client.service-url.defaultZone=http://discovery:8761/eureka" \
	-p 9002:9002 gateway-service