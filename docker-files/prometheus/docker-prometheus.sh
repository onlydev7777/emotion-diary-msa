docker run -d --network ediary-service-network --name ediary-prometheus \
  -p 9091:9090 -v ./prometheus.yml:/etc/prometheus/prometheus.yml \
  prom/prometheus