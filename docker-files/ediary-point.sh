docker run -d --name emotion-point --network emotion-service-network \
	-e "spring.cloud.config.uri=http://config-service:8888" \
	-e "spring.rabbitmq.host=rabbitmq" \
	-e "management.zipkin.tracing.endpoint=http://zipkin:9411/api/v2/spans" \
	-e "eureka.client.serviceUrl.defaultZone=http://discovery:8761/eureka" \
	-e "logging.file=/api/logs/point-ws.log" \
	emotion-point