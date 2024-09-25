docker run -d --network ediary-service-network \
	--name ediary-auth-service \
	-e "spring.cloud.config.uri=http://config-service:8888" \
	-e "spring.rabbitmq.host=rabbitmq" \
	-e "eureka.client.serviceUrl.defaultZone=http://discovery:8761/eureka" \
	-e "logging.file=/api/logs/auth-ws.log" \
	ediary-auth-service