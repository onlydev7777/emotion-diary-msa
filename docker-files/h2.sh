docker run -d --name=emotion-h2db --network emotion-service-network \
  -p 1521:1521 -p 81:81 \
  -v ./h2:/opt/h2-data \
  -e H2_OPTIONS="-ifNotExists" \
  oscarfonts/h2:2.2.224
