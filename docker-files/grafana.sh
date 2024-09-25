docker run -d --name emotion-grafana --network emotion-service-network \
 -p 3000:3000 grafana/grafana