docker run -d --network emotion-service-network \
	--name ediary-diary \
	-e "spring.cloud.config.uri=http://config-service:8888" \
	-e "spring.rabbitmq.host=rabbitmq" \
	-e "management.zipkin.tracing.endpoint=http://zipkin:9411/api/v2/spans" \
	-e "eureka.client.serviceUrl.defaultZone=http://discovery:8761/eureka" \
	-e "logging.file=/api/logs/diary-ws.log" \
	ediary-diary