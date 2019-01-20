FROM maven:3.6.0-jdk-8-slim
COPY cmd.sh /
RUN mkdir /app; \
    groupadd -r weather && useradd -r -g weather weather;\
    chown -R weather:weather /app; \
    mkdir /home/weather; \
    chown -R weather:weather /home/weather;
WORKDIR /app
COPY target/weather-api.jar /app
EXPOSE 8080
USER weather
CMD ["/cmd.sh"]
